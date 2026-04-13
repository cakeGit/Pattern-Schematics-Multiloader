package com.cak.pattern_schematics.content.ponder;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import net.createmod.ponder.api.registration.PonderPlugin;
import net.createmod.ponder.api.registration.PonderSceneRegistrationHelper;
import net.createmod.ponder.api.registration.PonderTagRegistrationHelper;
import net.minecraft.resources.ResourceLocation;

public class PatternSchematicsPonderPlugin implements PonderPlugin {
    
    public static final ResourceLocation PATTERN_SCHEMATIC_TAG = PatternSchematics.asResource("pattern_schematics");
    
    @Override
    public String getModId() {
        return PatternSchematics.MOD_ID;
    }
    
    @Override
    public void registerScenes(PonderSceneRegistrationHelper<ResourceLocation> helper) {
        ResourceLocation emptyId = PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC.getId();
        ResourceLocation patternId = PatternSchematicsRegistry.PATTERN_SCHEMATIC.getId();
        
        helper.forComponents(emptyId, patternId)
            .addStoryBoard("pattern_schematic/schematic_printing",
                PatternSchematicPonderScenes::schematicPrinting, PATTERN_SCHEMATIC_TAG)
            .addStoryBoard("pattern_schematic/train_schematic_printing",
                PatternSchematicPonderScenes::trainSchematicPrinting, PATTERN_SCHEMATIC_TAG);
    }
    
    @Override
    public void registerTags(PonderTagRegistrationHelper<ResourceLocation> helper) {
        helper.registerTag(PATTERN_SCHEMATIC_TAG)
            .title("Pattern Schematics")
            .description("Printing with pattern schematics")
            .item(PatternSchematicsRegistry.PATTERN_SCHEMATIC.get())
            .addToIndex()
            .register();
        
        ResourceLocation emptyId = PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC.getId();
        ResourceLocation patternId = PatternSchematicsRegistry.PATTERN_SCHEMATIC.getId();
        
        helper.addToTag(PATTERN_SCHEMATIC_TAG)
            .add(emptyId)
            .add(patternId);
    }
}
