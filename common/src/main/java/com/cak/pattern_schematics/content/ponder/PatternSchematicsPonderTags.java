package com.cak.pattern_schematics.content.ponder;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;

public class PatternSchematicsPonderTags {
    
    public static final PonderTag
        PATTERN_SCHEMATIC = create("pattern_schematics").item(PatternSchematicsRegistry.PATTERN_SCHEMATIC.get())
        .defaultLang("Pattern Schematics", "Printing with pattern schematics")
        .addToIndex();
    
    private static PonderTag create(String id) {
        return new PonderTag(PatternSchematics.asResource(id));
    }
    
    public static void register() {
        // Add items to tags here
        
        PonderRegistry.TAGS.forTag(PATTERN_SCHEMATIC)
            .add(PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC)
            .add(PatternSchematicsRegistry.PATTERN_SCHEMATIC);
        
    }
    
}
