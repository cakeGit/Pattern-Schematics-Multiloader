package com.cak.pattern_schematics.platform.forge.mixin.temp_platform;

import com.cak.pattern_schematics.content.item.PatternSchematicItem;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicLevel;
import com.simibubi.create.content.schematics.SchematicInstances;
import net.createmod.catnip.levelWrappers.SchematicLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SchematicInstances.class, remap = false)
public class SchematicInstancesMixin {
  
  private static ItemStack lastThreadStack = null;
  private static StructureTemplate lastThreadStructureTemplate = null;
  
  @Inject(method = "loadWorld", at = @At(value = "HEAD"))
  private static void loadWorld(Level wrapped, ItemStack schematic, CallbackInfoReturnable<SchematicLevel> cir) {
    lastThreadStack = schematic;
  }
  
  @ModifyVariable(method = "loadWorld", at = @At(value = "STORE"), ordinal = 0)
  private static StructureTemplate store_activeTemplate(StructureTemplate template) {
    lastThreadStructureTemplate = template;
    return template;
  }
  
  @ModifyVariable(method = "loadWorld", at = @At("STORE"), ordinal = 0)
  private static SchematicLevel loadWorld(SchematicLevel value) {
    return getSchematicLevelDebuggable(value);
  }

  private static SchematicLevel getSchematicLevelDebuggable(SchematicLevel value) {
    if (lastThreadStack.getItem() instanceof PatternSchematicItem) {
      PatternSchematicLevel patternSchematicLevel = new PatternSchematicLevel(value.anchor, value.getLevel());
      patternSchematicLevel.putExtraData(lastThreadStack, lastThreadStructureTemplate);
      return patternSchematicLevel;
    }
    return value;
  }

}
