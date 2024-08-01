package com.cak.pattern_schematics.forge;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsItems;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PatternSchematics.MODID)
public class PatternSchematicsForge {
    //TODO: Fix the hot steaming mess of the lack of forge working
    public PatternSchematicsForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        PatternSchematicsItems.REGISTRATE.registerEventListeners(eventBus);
        PatternSchematics.init();
    }
    
}
