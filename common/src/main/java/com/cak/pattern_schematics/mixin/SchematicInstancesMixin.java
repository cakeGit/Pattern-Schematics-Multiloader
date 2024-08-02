package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.content.item.PatternSchematicItem;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicWorld;
import com.simibubi.create.content.schematics.SchematicInstances;
import com.simibubi.create.content.schematics.SchematicWorld;
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
  private static void loadWorld(Level wrapped, ItemStack schematic, CallbackInfoReturnable<SchematicWorld> cir) {
    lastThreadStack = schematic;
  }
  
  @ModifyVariable(method = "loadWorld", at = @At(value = "STORE"), ordinal = 0)
  private static StructureTemplate store_activeTemplate(StructureTemplate template) {
    lastThreadStructureTemplate = template;
    return template;
  }
  
  @ModifyVariable(method = "loadWorld", at = @At("STORE"), ordinal = 0)
  private static SchematicWorld loadWorld(SchematicWorld value) {
    if (lastThreadStack.getItem() instanceof PatternSchematicItem) {
      PatternSchematicWorld patternSchematicWorld = new PatternSchematicWorld(value.anchor, value.getLevel());
      patternSchematicWorld.putExtraData(lastThreadStack, lastThreadStructureTemplate);
      return patternSchematicWorld;
    }
    return value;
  }
  
}
