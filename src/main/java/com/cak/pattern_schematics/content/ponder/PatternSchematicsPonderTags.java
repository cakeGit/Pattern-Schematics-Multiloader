package com.cak.pattern_schematics.content.ponder;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class PatternSchematicsPonderTags {

    public static final ResourceLocation PATTERN_SCHEMATICS = loc("pattern_schematics");

    private static ResourceLocation loc(String id) {
        return PatternSchematics.asResource(id);
    }

    public static void register(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PonderTagRegistrationHelper<RegistryEntry<?, ?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);

        helper.registerTag(PATTERN_SCHEMATICS)
            .item(PatternSchematicsRegistry.PATTERN_SCHEMATIC.get())
            .title("Pattern Schematics")
            .description("Printing with pattern schematics!")
            .register();

        HELPER.addToTag(PATTERN_SCHEMATICS)
            .add(PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC)
            .add(PatternSchematicsRegistry.PATTERN_SCHEMATIC);
    }
    
}
