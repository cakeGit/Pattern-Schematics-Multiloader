package com.cak.pattern_schematics.registry;

import com.simibubi.create.content.contraptions.AssemblyException;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.nullness.NonNullUnaryOperator;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.Item;

public class PlatformRegistryTransforms {
    
    @ExpectPlatform
    public static <T extends Item, P> NonNullUnaryOperator<ItemBuilder<T, P>> transformEmptyPatternSchematic() {
        throw new AssertionError();
    }
    
}
