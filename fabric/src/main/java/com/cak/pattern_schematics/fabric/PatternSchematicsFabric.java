package com.cak.pattern_schematics.fabric;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import net.fabricmc.api.ModInitializer;

import static com.cak.pattern_schematics.PatternSchematics.REGISTRATE;

public class PatternSchematicsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        PatternSchematics.init();
        REGISTRATE.register();
    }
    
}
