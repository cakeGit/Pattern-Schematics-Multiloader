package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.foundation.ExpandedContraptionRotationState;
import com.cak.pattern_schematics.foundation.RolledContraptionRotationState;
import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.OrientedContraptionEntity;
import com.simibubi.create.foundation.utility.VecHelper;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = OrientedContraptionEntity.class, remap = false)
public abstract class OrientedContraptionEntityMixin extends Entity{
    
    public OrientedContraptionEntityMixin(EntityType<?> entityType, Level level) {
        super(entityType, level);
    }
    
    @Shadow public abstract float getInitialYaw();
    
    @Shadow public abstract float getViewXRot(float partialTicks);
    
    @Shadow public abstract float getViewYRot(float partialTicks);
    
    @Shadow public abstract float getYawOffset();
    
    @Shadow public float pitch;
    
    @Shadow public float yaw;
    
    @Shadow protected abstract void repositionOnCart(PoseStack matrixStack, float partialTicks, Entity ridingEntity);
    
    @Shadow protected abstract void repositionOnContraption(PoseStack matrixStack, float partialTicks, Entity ridingEntity);
    
    /**
     * @author CAKE
     * @reason DONT CARE
     */
    @Overwrite
    public AbstractContraptionEntity.ContraptionRotationState getRotationState() {
        ExpandedContraptionRotationState crs = (ExpandedContraptionRotationState) new AbstractContraptionEntity.ContraptionRotationState();
        
        float yawOffset = getYawOffset();
        crs.pattern_schematics$setZRotation(pitch);
        crs.pattern_schematics$setYRotation(-yaw + yawOffset);
        
        if (pitch != 0 && yaw != 0) {
            crs.pattern_schematics$setSecondYRotation(-yaw);
            crs.pattern_schematics$setYRotation(yawOffset);
        }
        
        RolledContraptionRotationState rcrs = new RolledContraptionRotationState((AbstractContraptionEntity.ContraptionRotationState) crs);
        rcrs.setRoll(30);
        return rcrs;
    }
    
    /**
     * @author CAKE
     * @reason DONT CARE
     */
    @Overwrite
    public Vec3 applyRotation(Vec3 localPos, float partialTicks) {
        localPos = VecHelper.rotate(localPos, getInitialYaw(), Direction.Axis.Y);
        localPos = VecHelper.rotate(localPos, getViewXRot(partialTicks), Direction.Axis.Z);
        localPos = VecHelper.rotate(localPos, 30, Direction.Axis.X);
        localPos = VecHelper.rotate(localPos, getViewYRot(partialTicks), Direction.Axis.Y);
        return localPos;
    }
    
    /**
     * @author CAKE
     * @reason DONT CARE
     */
    @Overwrite
    public Vec3 reverseRotation(Vec3 localPos, float partialTicks) {
        localPos = VecHelper.rotate(localPos, -getViewYRot(partialTicks), Direction.Axis.Y);
        localPos = VecHelper.rotate(localPos, -30, Direction.Axis.X);
        localPos = VecHelper.rotate(localPos, -getViewXRot(partialTicks), Direction.Axis.Z);
        localPos = VecHelper.rotate(localPos, -getInitialYaw(), Direction.Axis.Y);
        return localPos;
    }
    
    /**
     * @author CAKE
     * @reason DONT CARE
     */
    @Overwrite
    @Environment(EnvType.CLIENT)
    public void applyLocalTransforms(PoseStack matrixStack, float partialTicks) {
        float angleInitialYaw = getInitialYaw();
        float angleYaw = getViewYRot(partialTicks);
        float anglePitch = getViewXRot(partialTicks);
        
        matrixStack.translate(-.5f, 0, -.5f);
        
        Entity ridingEntity = getVehicle();
        if (ridingEntity instanceof AbstractMinecart)
            repositionOnCart(matrixStack, partialTicks, ridingEntity);
        else if (ridingEntity instanceof AbstractContraptionEntity) {
            if (ridingEntity.getVehicle() instanceof AbstractMinecart)
                repositionOnCart(matrixStack, partialTicks, ridingEntity.getVehicle());
            else
                repositionOnContraption(matrixStack, partialTicks, ridingEntity);
        }
        
        TransformStack.cast(matrixStack)
            .nudge(getId())
            .centre()
            .rotateY(angleYaw)
            .rotateX(getRoll(getContraption))//Create contraption expander
            .rotateZ(anglePitch)
            .rotateY(angleInitialYaw)
            .unCentre();
    }
    
    
}
