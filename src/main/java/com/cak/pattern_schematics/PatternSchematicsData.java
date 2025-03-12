package com.cak.pattern_schematics;

import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderPlugin;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.providers.RegistrateDataProvider;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

import java.util.function.BiConsumer;

@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
public class PatternSchematicsData {

    @SubscribeEvent
    public static void onGenerateData(GatherDataEvent event) {
        PatternSchematics.REGISTRATE.addDataGenerator(ProviderType.LANG, provider -> {
            BiConsumer<String, String> langConsumer = provider::add;

            // Register this since FMLClientSetupEvent does not run during datagen
            PonderIndex.addPlugin(new PatternSchematicsPonderPlugin());

            PonderIndex.getLangAccess().provideLang(PatternSchematics.MOD_ID, langConsumer);
        });

        event.getGenerator().addProvider(
            true,
            PatternSchematics.REGISTRATE.setDataProvider(
                new RegistrateDataProvider(PatternSchematics.REGISTRATE, PatternSchematics.MOD_ID, event)
            )
        );
    }

}
