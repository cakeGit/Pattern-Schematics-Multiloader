package com.cak.pattern_schematics.fabric;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderIndex;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderTags;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.cak.pattern_schematics.registry.PatternSchematicsLang;
import com.simibubi.create.foundation.ponder.PonderLocalization;
import com.simibubi.create.infrastructure.ponder.SharedText;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

public class PatternSchematicsFabricData implements DataGeneratorEntrypoint {
    
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();
        PatternSchematicsRegistry.REGISTRATE.setupDatagen(fabricDataGenerator, helper);
        PatternSchematicsLang.register();
        
        PatternSchematicsPonderTags.register();
        PatternSchematicsPonderIndex.register();
        
        SharedText.gatherText();
        PonderLocalization.generateSceneLang();
        
        PonderLocalization.provideLang(PatternSchematics.MODID, PatternSchematicsLang.ENTRY_CONSUMER);
    }
    
}
