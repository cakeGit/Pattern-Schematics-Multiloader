package com.cak.pattern_schematics.content.item;

import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.content.schematics.SchematicItem;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public class PatternSchematicItem extends SchematicItem {
  
  public PatternSchematicItem(Properties properties) {
    super(properties);
  }
  
  public static ItemStack create(String schematic, String owner) {
    ItemStack blueprint = PatternSchematicsRegistry.PATTERN_SCHEMATIC.asStack();
    
    CompoundTag tag = new CompoundTag();
    tag.putBoolean("Deployed", false);
    tag.putString("Owner", owner);
    tag.putString("File", schematic);
    tag.put("Anchor", NbtUtils.writeBlockPos(BlockPos.ZERO));
    tag.putString("Rotation", Rotation.NONE.name());
    tag.putString("Mirror", Mirror.NONE.name());
    blueprint.setTag(tag);
    
    writeSize(blueprint);
    return blueprint;
  }
  
}
