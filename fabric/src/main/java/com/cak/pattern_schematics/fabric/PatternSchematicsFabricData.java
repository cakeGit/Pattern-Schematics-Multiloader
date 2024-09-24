package com.cak.pattern_schematics.fabric;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderIndex;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderTags;
import com.cak.pattern_schematics.registry.PatternSchematicsLang;
import com.simibubi.create.foundation.ponder.PonderLocalization;
import com.simibubi.create.infrastructure.ponder.SharedText;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;

import static com.cak.pattern_schematics.PatternSchematics.REGISTRATE;

public class PatternSchematicsFabricData implements DataGeneratorEntrypoint {
    
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();
        REGISTRATE.setupDatagen(fabricDataGenerator.createPack(), helper);
        PatternSchematicsLang.register();
        
        PatternSchematicsPonderTags.register();
        PatternSchematicsPonderIndex.register();
        
        SharedText.gatherText();
        PonderLocalization.generateSceneLang();
        
        PonderLocalization.provideLang(PatternSchematics.MOD_ID, PatternSchematicsLang.ENTRY_CONSUMER);
    }
    
}
