package com.cak.pattern_schematics.fabric;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsItems;
import io.github.fabricators_of_create.porting_lib.util.EnvExecutor;
import net.fabricmc.api.ModInitializer;

public class PatternSchematicsFabric implements ModInitializer {
    
    @Override
    public void onInitialize() {
        PatternSchematics.init();
        PatternSchematicsItems.REGISTRATE.register();
    }
    
}
