package com.cak.pattern_schematics.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(StructureTemplate.class)
public class StructureTemplateMixin {
    
    
    @Redirect(method = "placeInWorld", at = @At(value = "INVOKE", ordinal = 1, target = "Lnet/minecraft/world/level/ServerLevelAccessor;setBlock(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;I)Z", remap = true))
    private boolean setBlock(ServerLevelAccessor instance, BlockPos pos, BlockState state, int i) {
//    if (instance instanceof PatternSchematicWorld patternSchematicWorld)
//      return patternSchematicWorld.setCloneBlock(pos, state, i);
        return instance.setBlock(pos, state, i);
    }
    
    
}
