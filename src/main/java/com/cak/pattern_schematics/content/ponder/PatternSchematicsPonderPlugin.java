package com.cak.pattern_schematics.content.ponder;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class PatternSchematicsPonderPlugin implements PonderPlugin {

    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        PonderSceneRegistrationHelper<ItemProviderEntry<?>> HELPER = helper.withKeyFunction(RegistryEntry::getId);
        HELPER.forComponents(PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC, PatternSchematicsRegistry.PATTERN_SCHEMATIC)
            .addStoryBoard("pattern_schematic/schematic_printing",
                PatternSchematicPonderScenes::schematicPrinting, PatternSchematicsPonderTags.PATTERN_SCHEMATICS)
            .addStoryBoard("pattern_schematic/train_schematic_printing",
                PatternSchematicPonderScenes::trainSchematicPrinting, PatternSchematicsPonderTags.PATTERN_SCHEMATICS);
    }

    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        PatternSchematicsPonderTags.register(helper);
    }

    @Override
    public String getModId() {
        return PatternSchematics.MOD_ID;
    }

}
