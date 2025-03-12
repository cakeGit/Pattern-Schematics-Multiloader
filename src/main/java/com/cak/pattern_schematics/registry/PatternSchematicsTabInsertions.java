package com.cak.pattern_schematics.registry;

import com.google.common.collect.ImmutableMap;
import com.simibubi.create.AllItems;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class PatternSchematicsTabInsertions {
    
    
    private static Map<Item, Item> INSERTS_AFTER = null;
    
    public static final Map<ItemEntry<Item>, ItemEntry<Item>> REGISTRY_INSERTS_AFTER = Map.of(
        AllItems.EMPTY_SCHEMATIC, PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC
    );
    
    public static Map<Item, Item> getAllInsertsAfter() {
        if (INSERTS_AFTER != null) {
            return INSERTS_AFTER;
        }
        
        INSERTS_AFTER = new HashMap<>();
        for (Map.Entry<ItemEntry<Item>, ItemEntry<Item>> entry : REGISTRY_INSERTS_AFTER.entrySet()) {
            INSERTS_AFTER.put(entry.getKey().get(), entry.getValue().get());
        }
        return INSERTS_AFTER;
    }
    
}
