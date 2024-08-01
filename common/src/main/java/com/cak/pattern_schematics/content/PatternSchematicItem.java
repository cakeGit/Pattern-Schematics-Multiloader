package com.cak.pattern_schematics.content;

import com.cak.pattern_schematics.registry.PatternSchematicsItems;
import com.simibubi.create.content.schematics.SchematicItem;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderGetter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;

public class PatternSchematicItem extends SchematicItem {
  
  public PatternSchematicItem(Properties properties) {
    super(properties);
  }
  
  public static ItemStack create(HolderGetter<Block> lookup, String schematic, String owner) {
    ItemStack blueprint = PatternSchematicsItems.PATTERN_SCHEMATIC.asStack();
    
    CompoundTag tag = new CompoundTag();
    tag.putBoolean("Deployed", false);
    tag.putString("Owner", owner);
    tag.putString("File", schematic);
    tag.put("Anchor", NbtUtils.writeBlockPos(BlockPos.ZERO));
    tag.putString("Rotation", Rotation.NONE.name());
    tag.putString("Mirror", Mirror.NONE.name());
    blueprint.setTag(tag);
    
    writeSize(lookup, blueprint);
    return blueprint;
  }
  
}
