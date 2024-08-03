package com.cak.pattern_schematics.registry.fabric;

import com.simibubi.create.AllCreativeModeTabs;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import net.minecraft.world.item.Item;

public class PlatformRegistryTransformsImpl {
    
    public static <T extends Item, P>NonNullUnaryOperator<ItemBuilder<T, P>> transformEmptyPatternSchematic() {
       return (i) -> i.tab(() -> AllCreativeModeTabs.BASE_CREATIVE_TAB);
    }
    
    public static void registerPackets() {
        PatternSchematicPackets.registerPackets();
    }
    
}
