package com.cak.pattern_schematics.forge;

import com.cak.pattern_schematics.PatternSchematicsClient;
import com.cak.pattern_schematics.foundation.mirror.forge.PatternSchematicHandlerForge;

public class PatternSchematicsForgeClient {
    
    public static final PatternSchematicHandlerForge PATTERN_SCHEMATICS_HANDLER_FORGE = new PatternSchematicHandlerForge();
    
    public static void onInitializeClient() {
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER = PATTERN_SCHEMATICS_HANDLER_FORGE;
        PatternSchematicsClient.init();
    }
    
}
