package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicWorld;
import com.cak.pattern_schematics.registry.PatternSchematicsItems;
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
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = DeployerMovementBehaviour.class, remap = false)
public class DeployerMovementBehaviorMixin {

  private ItemStack currentBlueprint;
//  private Contraption currentContraption;
//  private Level currentLevel;
//  private BlockPos currentBlockPos;
//
//
//  @Inject(method = "activateAsSchematicPrinter", at = @At("HEAD"))
//  public void head_activateAsSchematicPrinter(MovementContext context, BlockPos blockPos, DeployerFakePlayer player, Level world, ItemStack filter, CallbackInfo ci) {
//    currentContraption = context.contraption;
//    currentBlockPos = blockPos;
//    currentLevel = world;
//  }
//
  @Redirect(method = "activate", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"))
  public boolean isIn(ItemEntry<SchematicItem> instance, ItemStack stack) {
    currentBlueprint = stack;
    return instance.isIn(stack) || PatternSchematicsItems.PATTERN_SCHEMATIC.isIn(stack);
  }
  
  @Redirect(method = "activateAsSchematicPrinter", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/BoundingBox;isInside(Lnet/minecraft/core/Vec3i;)Z", remap = true))
  public boolean isInside(BoundingBox instance, Vec3i vec3i) {
    if (PatternSchematicsItems.PATTERN_SCHEMATIC.isIn(currentBlueprint)) {
      return true;
    }
    return instance.isInside(vec3i);
  }
  
  @Redirect(method = "activateAsSchematicPrinter", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/SchematicWorld;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;", remap = true))
  public BlockState getBlockState(SchematicWorld instance, BlockPos globalPos) {
    if (instance instanceof PatternSchematicWorld patternSchematicWorld) {
//      System.out.println(globalPos);
//      System.out.println(pattern_Schematics$getSourceOfLocal(globalPos, patternSchematicWorld));
//      System.out.println(instance.getBlockState(pattern_Schematics$getSourceOfLocal(globalPos, patternSchematicWorld)));
      return instance.getBlockState(pattern_Schematics$getSourceOfLocal(globalPos, patternSchematicWorld));
    }
    else return instance.getBlockState(globalPos);
  }
  
  @Unique
  public BlockPos pattern_Schematics$getSourceOfLocal(BlockPos position, PatternSchematicWorld patternSchematicWorld) {
    position = position.subtract(patternSchematicWorld.anchor);
    BoundingBox box = patternSchematicWorld.getBounds();
    return new BlockPos(
        pattern_Schematics$repeatingBounds(position.getX(), box.minX(), box.maxX()),
        pattern_Schematics$repeatingBounds(position.getY(), box.minY(), box.maxY()),
        pattern_Schematics$repeatingBounds(position.getZ(), box.minZ(), box.maxZ())
    ).offset(patternSchematicWorld.anchor);
  }
  
  @Unique
  private int pattern_Schematics$repeatingBounds(int source, int min, int max) {
    return (Math.floorMod(source, (max-min)+1) + min);
  }
  
  @Redirect(method = "activateAsSchematicPrinter", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/SchematicWorld;getBlockEntity(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/entity/BlockEntity;", remap = true))
  public BlockEntity getBlockEntity(SchematicWorld instance, BlockPos globalPos) {
    if (instance instanceof PatternSchematicWorld patternSchematicWorld)
      return instance.getBlockEntity(pattern_Schematics$getSourceOfLocal(globalPos, patternSchematicWorld));
    return instance.getBlockEntity(globalPos);
  }
//
//  private BlockState transformBlock(BlockState blockState, PatternSchematicWorld patternSchematicWorld) {
//    //System.out.println(blockState.getBlock());
//    if (blockState == null)
//      return null;
//    return blockState;
//  }
//
//  private BlockPos modifyPos(BlockPos globalPos, SchematicWorld instance) {
//    if (instance instanceof PatternSchematicWorld patternSchematicWorld) {
//
//      currentContraptionSchematicTransform = ContraptionSchematicTransform.Handlers.get(currentContraption);
//
//      globalPos = currentContraptionSchematicTransform.castModifyPos(
//          currentContraption, patternSchematicWorld,
//          globalPos.subtract(currentContraption.anchor)
//      ).offset(currentContraption.anchor).offset(-1, -1, -1);
//
//      System.out.println(globalPos);
//
//      return currentContraptionSchematicTransform.castApplyRealToSourcePosition(
//          currentContraption, patternSchematicWorld, globalPos
//      );
//
//    }
//    return globalPos;
//  }

}
