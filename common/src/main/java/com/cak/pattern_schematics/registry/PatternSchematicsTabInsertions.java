package com.cak.pattern_schematics.registry;

import com.simibubi.create.AllItems;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.Item;

import java.util.HashMap;
import java.util.Map;

public class PatternSchematicsTabInsertions {
    
    private static Map<Item, Item> INSERTS_AFTER_ITEM = null;
    
    public static final Map<ItemEntry<Item>, ItemEntry<Item>> REGISTRY_INSERTS_AFTER_ITEM = Map.of(
        AllItems.EMPTY_SCHEMATIC, PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC
    );
    
    public static Map<Item, Item> getAllInsertsAfterItem() {
        if (INSERTS_AFTER_ITEM != null) {
            return INSERTS_AFTER_ITEM;
        }
        
        INSERTS_AFTER_ITEM = new HashMap<>();
        for (Map.Entry<ItemEntry<Item>, ItemEntry<Item>> entry : REGISTRY_INSERTS_AFTER_ITEM.entrySet()) {
            INSERTS_AFTER_ITEM.put(entry.getKey().get(), entry.getValue().get());
        }
        return INSERTS_AFTER_ITEM;
    }
    
}
