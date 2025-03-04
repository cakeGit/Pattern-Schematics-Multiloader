package com.cak.pattern_schematics.forge.mixin.temp_platform;

import com.cak.pattern_schematics.content.item.PatternSchematicItem;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicLevel;
import com.cak.pattern_schematics.foundation.util.Vec3iUtils;
import com.simibubi.create.content.schematics.SchematicPrinter;
import net.createmod.catnip.levelWrappers.SchematicLevel;
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
  private SchematicLevel blockReader;
  
  @Inject(method = "loadSchematic", at = @At(value = "HEAD"))
  private void loadSchematic_head(ItemStack blueprint, Level originalWorld, boolean processNBT, CallbackInfo ci) {
    lastThreadStack = blueprint;
  }
  
  @ModifyVariable(method = "loadSchematic", ordinal = 0, at = @At(value = "STORE"))
  private StructureTemplate store_activeTemplate(StructureTemplate template) {
    lastThreadStructureTemplate = template;
    return template;
  }
  
  @Inject(method = "loadSchematic", at = @At(value = "FIELD", shift = At.Shift.AFTER, opcode = Opcodes.PUTFIELD, target = "Lcom/simibubi/create/content/schematics/SchematicPrinter;blockReader:Lnet/createmod/catnip/levelWrappers/SchematicLevel;"))
  private void loadSchematic(ItemStack blueprint, Level originalWorld, boolean processNBT, CallbackInfo ci) {
    if (lastThreadStack.getItem() instanceof PatternSchematicItem) {
      PatternSchematicLevel patternSchematicLevel = new PatternSchematicLevel(blockReader.anchor, blockReader.getLevel());
      patternSchematicLevel.putExtraData(blueprint, lastThreadStructureTemplate);
      blockReader = patternSchematicLevel;
    }
  }
  
  ServerLevelAccessor lastWorld;
  StructurePlaceSettings lastPlaceSettings;

  @Redirect(method = "loadSchematic", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;placeInWorld(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructurePlaceSettings;Lnet/minecraft/util/RandomSource;I)Z", remap = true))
  private boolean loadSchematic_placeInWorld(StructureTemplate instance, ServerLevelAccessor world, BlockPos offset, BlockPos pos, StructurePlaceSettings placeSettings, RandomSource random, int flags) {
    return placeInWorldDebuggable(instance, world, offset, pos, placeSettings, random, flags);
  }

  private boolean placeInWorldDebuggable(StructureTemplate instance, ServerLevelAccessor world, BlockPos offset, BlockPos pos, StructurePlaceSettings placeSettings, RandomSource random, int flags) {
    lastWorld = world;
    lastPlaceSettings = placeSettings;
    if (world instanceof PatternSchematicLevel patternSchematicLevel) {
      Vec3i minScale = patternSchematicLevel.cloneScaleMin;
      Vec3i maxScale = patternSchematicLevel.cloneScaleMax;

      Vec3i scale1 = new BlockPos(minScale).rotate(placeSettings.getRotation());
      Vec3i scale2 = new BlockPos(maxScale).rotate(placeSettings.getRotation());

      minScale = Vec3iUtils.min(scale1, scale2);
      maxScale = Vec3iUtils.max(scale1, scale2);

      for (int x = minScale.getX(); x <= maxScale.getX(); x++) {
        for (int y = minScale.getY(); y <= maxScale.getY(); y++) {
          for (int z = minScale.getZ(); z <= maxScale.getZ(); z++) {
            BlockPos anchor = offset.offset(
                Vec3iUtils.multiplyVec3i(new Vec3i(x, y, z), patternSchematicLevel.sourceBounds.getLength().offset(1, 1, 1))
            );
            instance.placeInWorld(world, anchor, anchor, placeSettings, random, flags);
          }
        }
      }
      return true;
    }
    return instance.placeInWorld(world, offset, pos, placeSettings, random, flags);
  }

  @Redirect(method = "loadSchematic", at = @At(value = "INVOKE", target = "Lnet/createmod/catnip/math/BBHelper;encapsulate(Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/levelgen/structure/BoundingBox;", remap = true))
  private BoundingBox loadSchematic_encapsulate(BoundingBox bb, BlockPos pos) {
    if (lastWorld instanceof PatternSchematicLevel patternSchematicLevel) {
      return patternSchematicLevel.genBounds(bb, lastPlaceSettings);
    }
    
    return bb;
  }
  
}
