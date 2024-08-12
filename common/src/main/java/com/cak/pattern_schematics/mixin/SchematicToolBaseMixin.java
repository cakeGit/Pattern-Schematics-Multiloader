package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.PatternSchematicsClient;
import com.cak.pattern_schematics.foundation.mirror.CloneSchematicOutlineRenderer;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicsToolType;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.schematics.client.SchematicHandler;
import com.simibubi.create.content.schematics.client.tools.SchematicToolBase;
import com.simibubi.create.foundation.utility.RaycastHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SchematicToolBase.class, remap = false)
public abstract class SchematicToolBaseMixin {
    
    @Shadow
    protected SchematicHandler schematicHandler;
    
    @Shadow
    public abstract void init();
    
    /**
     * Praise be to simi that none of the calls to this are specific enough that a slapped on extends in
     * {@link PatternSchematicHandler} works
     */
    @Inject(method = "init", at = @At("TAIL"))
    public void init(CallbackInfo ci) {
        schematicHandler =
            (PatternSchematicsToolType.isPatternSchematicTool((SchematicToolBase) (Object) this) ?
                PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER : CreateClient.SCHEMATIC_HANDLER);
    }
    
    @Inject(method = "renderOnSchematic", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lcom/simibubi/create/foundation/outliner/AABBOutline;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/simibubi/create/foundation/render/SuperRenderTypeBuffer;Lnet/minecraft/world/phys/Vec3;F)V", remap = true))
    public void renderOnSchematic(CallbackInfo ci) {
        CloneSchematicOutlineRenderer.applyOutlineModification(schematicHandler);
    }
    
    @Unique
    private static boolean pattern_schematics$currentThreadIsPatternSchematic = false;
    @Unique
    private static AABB pattern_schematics$currentThreadPatternBounds;
    
    @Redirect(method = "updateTargetPos", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/client/SchematicHandler;getBounds()Lnet/minecraft/world/phys/AABB;", remap = true))
    public AABB getExtendedBounds(SchematicHandler instance) {
        if (instance instanceof PatternSchematicHandler patternSchematics) {
            pattern_schematics$currentThreadIsPatternSchematic = true;
            pattern_schematics$currentThreadPatternBounds = patternSchematics.getExtendedBounds();
            return patternSchematics.getExtendedBounds();
        }
        pattern_schematics$currentThreadIsPatternSchematic = false;
        return instance.getBounds();
    }
    
    @Redirect(method = "updateTargetPos", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/RaycastHelper$PredicateTraceResult;getFacing()Lnet/minecraft/core/Direction;", remap = true))
    public Direction getFacingResult(RaycastHelper.PredicateTraceResult instance) {
        if (
            pattern_schematics$currentThreadIsPatternSchematic &&
            pattern_schematics$currentThreadPatternBounds.contains(
                schematicHandler.getTransformation().toLocalSpace(Minecraft.getInstance().player.getEyePosition())
            )
        ) {
            return instance.getFacing().getOpposite();
        }
        return instance.getFacing();
    }
    
}
