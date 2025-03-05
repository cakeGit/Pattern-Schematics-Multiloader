package com.cak.pattern_schematics.forge;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsDataComponents;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;

import static com.cak.pattern_schematics.PatternSchematics.REGISTRATE;

@Mod(PatternSchematics.MOD_ID)
public class PatternSchematicsForge {
    
    public PatternSchematicsForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = ModLoadingContext.get().getActiveContainer().getEventBus();
        REGISTRATE.registerEventListeners(eventBus);
        PatternSchematicsDataComponents.register(eventBus);
        PatternSchematics.init();
    }
    
}
