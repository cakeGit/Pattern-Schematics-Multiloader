package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.content.PatternSchematicItem;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicWorld;
import com.cak.pattern_schematics.foundation.util.Vec3iUtils;
import com.simibubi.create.content.schematics.SchematicPrinter;
import com.simibubi.create.content.schematics.SchematicWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SchematicPrinter.class, remap = false)
public class SchematicPrinterMixin {
  
  private static ItemStack lastThreadStack = null;
  private static StructureTemplate lastThreadStructureTemplate = null;
  
  @Shadow
  private SchematicWorld blockReader;
  
  @Inject(method = "loadSchematic", at = @At(value = "HEAD"))
  private void loadSchematic_head(ItemStack blueprint, Level originalWorld, boolean processNBT, CallbackInfo ci) {
    lastThreadStack = blueprint;
  }
  
  @ModifyVariable(method = "loadSchematic", ordinal = 0, at = @At(value = "STORE"))
  private StructureTemplate store_activeTemplate(StructureTemplate template) {
    lastThreadStructureTemplate = template;
    return template;
  }
  
  @Inject(method = "loadSchematic", at = @At(value = "FIELD", shift = At.Shift.AFTER, opcode = Opcodes.PUTFIELD, target = "Lcom/simibubi/create/content/schematics/SchematicPrinter;blockReader:Lcom/simibubi/create/content/schematics/SchematicWorld;"))
  private void loadSchematic(ItemStack blueprint, Level originalWorld, boolean processNBT, CallbackInfo ci) {
    if (lastThreadStack.getItem() instanceof PatternSchematicItem) {
      PatternSchematicWorld patternSchematicWorld = new PatternSchematicWorld(blockReader.anchor, blockReader.getLevel());
      patternSchematicWorld.putExtraData(blueprint, lastThreadStructureTemplate);
      blockReader = patternSchematicWorld;
    }
  }
  
//  @Redirect(method = "loadSchematic", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;getSize()Lnet/minecraft/core/Vec3i;"))
//  private Vec3i getBounds(StructureTemplate instance) {
//    //For pattern schematics template.getbounds != world.getbounds
//    Vec3i size = lastThreadStructureTemplate.getSize();
////
////    if (blockReader instanceof  PatternSchematicWorld patternSchematicWorld)
////      return Vec3iUtils.multiplyVec3i(
////          size, new Vec3i(1, 1, 1)
////              .subtract(patternSchematicWorld.cloneScaleMin)
////              .offset(patternSchematicWorld.cloneScaleMax)
////      );
////    else
//      return size;
//  }
  
  ServerLevelAccessor lastWorld;
  StructurePlaceSettings lastPlaceSettings;
  
  @Redirect(method = "loadSchematic", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;placeInWorld(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;Lnet/minecraft/util/RandomSource;I)Z", remap = true))
  private boolean loadSchem(StructureTemplate instance, ServerLevelAccessor world,
                            BlockPos blockPos1, BlockPos blockPos2, StructurePlaceSettings placeSettings,
                            RandomSource randomSource, int i) {
    lastWorld = world;
    lastPlaceSettings = placeSettings;
    if (world instanceof PatternSchematicWorld patternSchematicWorld) {
      Vec3i minScale = patternSchematicWorld.cloneScaleMin;
      Vec3i maxScale = patternSchematicWorld.cloneScaleMax;
  
      Vec3i scale1 = new BlockPos(minScale).rotate(placeSettings.getRotation());
      Vec3i scale2 = new BlockPos(maxScale).rotate(placeSettings.getRotation());
      
      minScale = Vec3iUtils.min(scale1, scale2);
      maxScale = Vec3iUtils.max(scale1, scale2);
      
      int k = 0;
      for (int x = minScale.getX(); x <= maxScale.getX(); x++) {
        for (int y = minScale.getY(); y <= maxScale.getY(); y++) {
          for (int z = minScale.getZ(); z <= maxScale.getZ(); z++) {
            BlockPos anchor = blockPos1.offset(
                Vec3iUtils.multiplyVec3i(new Vec3i(x, y, z), patternSchematicWorld.sourceBounds.getLength().offset(1, 1, 1))
            );
            k++;
//            System.out.println("placed" + k + "" + Vec3iUtils.multiplyVec3i(new Vec3i(x, y, z), patternSchematicWorld.sourceBounds.getLength().offset(1, 1, 1)));
            instance.placeInWorld(world, anchor, anchor, placeSettings, randomSource, i);
          }
        }
      }
      return true;
    }
    return instance.placeInWorld(world, blockPos1, blockPos2, placeSettings, randomSource, i);
  }
  
  @Redirect(method = "loadSchematic", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/BBHelper;encapsulate(Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/levelgen/structure/BoundingBox;", remap = true))
  private BoundingBox loadSchem(BoundingBox bb, BlockPos pos) {
    if (lastWorld instanceof PatternSchematicWorld patternSchematicWorld) {
      return patternSchematicWorld.genBounds(bb, lastPlaceSettings);
    }
    
    return bb;
  }
  
}
