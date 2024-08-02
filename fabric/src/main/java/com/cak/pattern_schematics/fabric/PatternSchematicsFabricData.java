package com.cak.pattern_schematics.fabric;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderIndex;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderTags;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.cak.pattern_schematics.registry.PatternSchematicsLang;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.ponder.PonderLocalization;
import com.simibubi.create.infrastructure.ponder.AllPonderTags;
import com.simibubi.create.infrastructure.ponder.GeneralText;
import com.simibubi.create.infrastructure.ponder.PonderIndex;
import com.simibubi.create.infrastructure.ponder.SharedText;
import io.github.fabricators_of_create.porting_lib.data.ExistingFileHelper;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

public class PatternSchematicsFabricData implements DataGeneratorEntrypoint {
    
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        ExistingFileHelper helper = ExistingFileHelper.withResourcesFromArg();
        PatternSchematicsRegistry.REGISTRATE.setupDatagen(fabricDataGenerator.createPack(), helper);
        PatternSchematicsLang.register();
        
        PatternSchematicsPonderTags.register();
        PatternSchematicsPonderIndex.register();
        
        SharedText.gatherText();
        PonderLocalization.generateSceneLang();
        
        PonderLocalization.provideLang(PatternSchematics.MODID, PatternSchematicsLang.ENTRY_CONSUMER);
    }
    
}
