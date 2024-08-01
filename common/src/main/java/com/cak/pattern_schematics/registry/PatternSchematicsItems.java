package com.cak.pattern_schematics.registry;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.content.PatternSchematicItem;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

public class PatternSchematicsItems {
  
  public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(PatternSchematics.MODID);
  
  public static final ItemEntry<Item> EMPTY_PATTERN_SCHEMATIC = REGISTRATE
      .item("empty_pattern_schematic", Item::new)
      .defaultModel()
      .properties(p -> p.stacksTo(1))
      .tab(AllCreativeModeTabs.BASE_CREATIVE_TAB.key())
      .register();
  
  public static final ItemEntry<PatternSchematicItem> PATTERN_SCHEMATIC = REGISTRATE
      .item("pattern_schematic", PatternSchematicItem::new)
      .defaultModel()
      .properties(p -> p.stacksTo(1))
      //.tab(AllCreativeModeTabs.BASE_CREATIVE_TAB.getKey())
      .register();
  
  public static void register() {}
  
}
