package com.cak.pattern_schematics.content.item;

import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.AllDataComponents;
import com.simibubi.create.content.schematics.SchematicItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public class PatternSchematicItem extends SchematicItem {
  
  public PatternSchematicItem(Properties properties) {
    super(properties);
  }
  
  public static ItemStack create(Level lookup, String schematic, String owner) {
    ItemStack blueprint = PatternSchematicsRegistry.PATTERN_SCHEMATIC.asStack();

    blueprint.set(AllDataComponents.SCHEMATIC_DEPLOYED, false);
    blueprint.set(AllDataComponents.SCHEMATIC_OWNER, owner);
    blueprint.set(AllDataComponents.SCHEMATIC_FILE, schematic);
    blueprint.set(AllDataComponents.SCHEMATIC_ANCHOR, BlockPos.ZERO);
    blueprint.set(AllDataComponents.SCHEMATIC_ROTATION, Rotation.NONE);
    blueprint.set(AllDataComponents.SCHEMATIC_MIRROR, Mirror.NONE);
    writeSize(lookup, blueprint);

    return blueprint;
  }
  
}
