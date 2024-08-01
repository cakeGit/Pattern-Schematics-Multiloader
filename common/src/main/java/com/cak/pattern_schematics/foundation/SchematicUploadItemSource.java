package com.cak.pattern_schematics.foundation;

import com.cak.pattern_schematics.content.PatternSchematicItem;
import com.cak.pattern_schematics.registry.PatternSchematicsItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.SchematicItem;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.core.HolderGetter;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.List;

public enum SchematicUploadItemSource {
  
  DEFAULT(SchematicItem::create, 0, List.of(
      AllItems.EMPTY_SCHEMATIC,
      AllItems.SCHEMATIC_AND_QUILL,
      AllItems.SCHEMATIC
  ) ),
  PATTERN(PatternSchematicItem::create, 1, List.of(
      PatternSchematicsItems.EMPTY_PATTERN_SCHEMATIC,
      PatternSchematicsItems.PATTERN_SCHEMATIC
  ))
  ;
  
  final SchematicItemFactory factory;
  final int nbtValue;
  final List<ItemEntry<? extends Item>> schematicSourceItem;
  
  SchematicUploadItemSource(SchematicItemFactory factory, int nbtValue, List<ItemEntry<? extends Item>> schematicSourceItem) {
    this.factory = factory;
    this.nbtValue = nbtValue;
    this.schematicSourceItem = schematicSourceItem;
  }
  
  public SchematicItemFactory getFactory() {
    return factory;
  }
  
  public int getNbtValue() {
    return nbtValue;
  }
  
  public List<ItemEntry<? extends Item>> getSchematicSourceItems() {
    return schematicSourceItem;
  }
  
  public static SchematicUploadItemSource tryFromItemStack(ItemStack stack) {
    return Arrays.stream(values())
        .filter(source -> source.getSchematicSourceItems()
            .stream().anyMatch(sourceItem -> sourceItem.isIn(stack)))
        .findAny().orElse(null);
  }
  
  public static SchematicUploadItemSource tryFromInt(int nbtValue) {
    return Arrays.stream(values())
        .filter(source -> source.getNbtValue() == nbtValue)
        .findAny().orElse(null);
  }
  
  public static interface SchematicItemFactory {
    ItemStack create(HolderGetter<Block> lookup, String schematic, String owner);
  }
  
}
