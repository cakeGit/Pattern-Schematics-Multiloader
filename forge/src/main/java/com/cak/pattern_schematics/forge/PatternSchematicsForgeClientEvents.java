package com.cak.pattern_schematics.forge;

import com.cak.pattern_schematics.PatternSchematicsClient;
import com.cak.pattern_schematics.PatternSchematicsClientEvents;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderIndex;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderTags;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.render.SuperRenderTypeBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PatternSchematicsForgeClientEvents {
    
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
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
        if (PatternSchematicsClientEvents.onMouseScrolled(event.getScrollDelta()))
            event.setCanceled(true);
    }
    
    @SubscribeEvent
    public static void onMouseInput(InputEvent.MouseButton.Pre event) {
        int button = event.getButton();
        boolean pressed = !(event.getAction() == 0);
        if (PatternSchematicsClientEvents.onMouseInput(button, pressed))
            event.setCanceled(true);
    }
    
    @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        
        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
            event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "pattern_schematic", PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER);
        }

        @SubscribeEvent
        public static void setupClient(FMLClientSetupEvent event) {
            PatternSchematicsPonderTags.register();
            PatternSchematicsPonderIndex.register();
        }
        
    }
    
}

