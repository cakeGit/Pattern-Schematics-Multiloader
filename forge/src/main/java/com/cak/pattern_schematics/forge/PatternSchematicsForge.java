package com.cak.pattern_schematics.forge;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.PatternSchematicsClient;
import com.cak.pattern_schematics.registry.PatternSchematicsItems;
import com.simibubi.create.CreateClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(PatternSchematics.MODID)
public class PatternSchematicsForge {
    
    public PatternSchematicsForge() {
        // registrate must be given the mod event bus on forge before registration
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        PatternSchematicsItems.REGISTRATE.registerEventListeners(eventBus);
        PatternSchematics.init();
        
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> PatternSchematicsForgeClient::onInitializeClient);
        
    }
    
}
