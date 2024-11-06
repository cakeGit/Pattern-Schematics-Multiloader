package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicWorld;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.content.kinetics.deployer.DeployerMovementBehaviour;
import com.simibubi.create.content.schematics.SchematicItem;
import com.simibubi.create.content.schematics.SchematicWorld;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DeployerMovementBehaviour.class, remap = false)
public class DeployerMovementBehaviorMixin {
    
    private ItemStack currentBlueprint;
    
    @Redirect(method = "activate", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z", remap = true))
    public boolean isIn(ItemEntry<SchematicItem> instance, ItemStack stack) {
        currentBlueprint = stack;
        return instance.isIn(stack) || PatternSchematicsRegistry.PATTERN_SCHEMATIC.isIn(stack);
    }
    
    @Inject(method = "activate", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/kinetics/deployer/DeployerMovementBehaviour;activateAsSchematicPrinter(Lcom/simibubi/create/content/contraptions/behaviour/MovementContext;Lnet/minecraft/core/BlockPos;Lcom/simibubi/create/content/kinetics/deployer/DeployerFakePlayer;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)V", shift = At.Shift.AFTER, remap = true), cancellable = true)
    public void after_activateAsSchematicPrinter(CallbackInfo ci) {
        ci.cancel();
    }
    
    @Redirect(method = "activateAsSchematicPrinter", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/BoundingBox;isInside(Lnet/minecraft/core/Vec3i;)Z", remap = true))
    public boolean isInside(BoundingBox instance, Vec3i vec3i) {
        if (PatternSchematicsRegistry.PATTERN_SCHEMATIC.isIn(currentBlueprint)) {
            return true;
        }
        return instance.isInside(vec3i);
    }
    
    @Redirect(method = "activateAsSchematicPrinter", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/SchematicWorld;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", remap = true))
    public BlockState getBlockState(SchematicWorld instance, BlockPos globalPos) {
        if (instance instanceof PatternSchematicWorld patternSchematicWorld) {
            return instance.getBlockState(pattern_Schematics$getSourceOfLocal(globalPos, patternSchematicWorld));
        } else return instance.getBlockState(globalPos);
    }
    
    @Unique
    public BlockPos pattern_Schematics$getSourceOfLocal(BlockPos position, PatternSchematicWorld patternSchematicWorld) {
        position = position.subtract(patternSchematicWorld.anchor);
        BoundingBox box = patternSchematicWorld.getBounds();
        position = position.subtract(new Vec3i(box.minX(), box.minY(), box.minZ()));
        return new BlockPos(
            pattern_Schematics$repeatingBounds(position.getX(), box.minX(), box.maxX()),
            pattern_Schematics$repeatingBounds(position.getY(), box.minY(), box.maxY()),
            pattern_Schematics$repeatingBounds(position.getZ(), box.minZ(), box.maxZ())
        ).offset(patternSchematicWorld.anchor);
    }
    
    @Unique
    private int pattern_Schematics$repeatingBounds(int source, int min, int max) {
        return (Math.floorMod(source, (max - min) + 1) + min);
    }
    
    @Redirect(method = "activateAsSchematicPrinter", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/SchematicWorld;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true))
    public BlockEntity getBlockEntity(SchematicWorld instance, BlockPos globalPos) {
        if (instance instanceof PatternSchematicWorld patternSchematicWorld)
            return instance.getBlockEntity(pattern_Schematics$getSourceOfLocal(globalPos, patternSchematicWorld));
        return instance.getBlockEntity(globalPos);
    }
    
}
