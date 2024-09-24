package com.cak.pattern_schematics.registry;

import com.cak.pattern_schematics.content.item.PatternSchematicItem;
import com.simibubi.create.AllCreativeModeTabs;
import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import static com.cak.pattern_schematics.PatternSchematics.REGISTRATE;

public class PatternSchematicsRegistry {
    
    public static final ItemEntry<Item> EMPTY_PATTERN_SCHEMATIC = REGISTRATE
        .item("empty_pattern_schematic", Item::new)
        .defaultModel()
        .properties(p -> p.stacksTo(1))
        .tab(() -> AllCreativeModeTabs.BASE_CREATIVE_TAB)
        .register();
    
    public static final ItemEntry<PatternSchematicItem> PATTERN_SCHEMATIC = REGISTRATE
        .item("pattern_schematic", PatternSchematicItem::new)
        .defaultModel()
        .properties(p -> p.stacksTo(1))
        .register();
    
    public static void register() {}
    
}
