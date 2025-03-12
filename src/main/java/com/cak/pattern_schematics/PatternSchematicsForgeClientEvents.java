package com.cak.pattern_schematics;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.InputEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

@EventBusSubscriber(Dist.CLIENT)
public class PatternSchematicsForgeClientEvents {
    
    @SubscribeEvent
    public static void onTick(ClientTickEvent.Pre event) {
        PatternSchematicsClientEvents.onTick();
    }
    
    @SubscribeEvent
    public static void onRenderWorld(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_PARTICLES)
            return;
        PatternSchematicsClientEvents.renderPatternSchematic(event.getPoseStack());
    }
    
    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        boolean pressed = !(event.getAction() == 0);
        PatternSchematicsClientEvents.onKeyInput(event.getKey(), pressed);
    }
    
    @SubscribeEvent
    public static void onMouseScrolled(InputEvent.MouseScrollingEvent event) {
        if (PatternSchematicsClientEvents.onMouseScrolled(event.getScrollDeltaY()))
            event.setCanceled(true);
    }
    
    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Pre event) {
        int button = event.getButton();
        boolean pressed = !(event.getAction() == 0);
        if (PatternSchematicsClientEvents.onMouseInput(button, pressed))
            event.setCanceled(true);
    }
    
    @EventBusSubscriber(value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
            event.registerAbove(VanillaGuiLayers.HOTBAR, PatternSchematics.asResource("pattern_schematic"), PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER);
        }

        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent event) {
        }
        
    }
    
}

