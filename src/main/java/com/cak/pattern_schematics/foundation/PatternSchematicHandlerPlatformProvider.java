package com.cak.pattern_schematics.foundation;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandlerFabric;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;

public class PatternSchematicHandlerPlatformProvider {
    public static PatternSchematicHandler getPlatformPatternSchematicHandler() {
        return new PatternSchematicHandlerFabric();
    }
}
