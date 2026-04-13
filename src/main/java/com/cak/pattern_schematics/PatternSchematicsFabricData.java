package com.cak.pattern_schematics;

import com.cak.pattern_schematics.registry.PatternSchematicsLang;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import static com.cak.pattern_schematics.PatternSchematics.REGISTRATE;

public class PatternSchematicsFabricData implements DataGeneratorEntrypoint {
    
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        REGISTRATE.setupDatagen(fabricDataGenerator.createPack(), null);
        PatternSchematicsLang.register();
    }
    
}
