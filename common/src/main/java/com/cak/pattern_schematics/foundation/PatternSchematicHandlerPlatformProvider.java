package com.cak.pattern_schematics.foundation;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import dev.architectury.injectables.annotations.ExpectPlatform;

public class PatternSchematicHandlerPlatformProvider {
    
    @ExpectPlatform
    public static PatternSchematicHandler getPlatformPatternSchematicHandler() {
        throw new AssertionError();
    }
    
}
