package com.cak.pattern_schematics.content.ponder;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;

public class PatternSchematicsPonderIndex {
    
    static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(PatternSchematics.MODID);
    
    public static void register() {
        
        HELPER.forComponents(PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC, PatternSchematicsRegistry.PATTERN_SCHEMATIC)
            .addStoryBoard("pattern_schematic/schematic_printing",
                PatternSchematicPonderScenes::schematicPrinting, PatternSchematicsPonderTags.PATTERN_SCHEMATIC)
            .addStoryBoard("pattern_schematic/train_schematic_printing",
                PatternSchematicPonderScenes::trainSchematicPrinting, PatternSchematicsPonderTags.PATTERN_SCHEMATIC);
        
    }
    
}
