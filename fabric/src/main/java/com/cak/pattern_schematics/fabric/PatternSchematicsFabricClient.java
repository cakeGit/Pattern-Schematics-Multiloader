package com.cak.pattern_schematics.fabric;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.PatternSchematicsClient;
import com.cak.pattern_schematics.PatternSchematicsClientEvents;
import com.cak.pattern_schematics.foundation.mirror.fabric.PatternSchematicHandlerFabric;
import com.mojang.blaze3d.platform.Window;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public class PatternSchematicsFabricClient implements ClientModInitializer {
    
    public static final PatternSchematicHandlerFabric PATTERN_SCHEMATICS_HANDLER_FABRIC = new PatternSchematicHandlerFabric();
    
    @Override
    public void onInitializeClient() {
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER = PATTERN_SCHEMATICS_HANDLER_FABRIC;
        PatternSchematicsClient.init();
        registerOverlays();
        PatternSchematicsFabricClientEvents.registerListeners();
    }
    
    private static void registerOverlays() {
        HudRenderCallback.EVENT.register((graphics, partialTicks) -> {
            Window window = Minecraft.getInstance().getWindow();
            
            PATTERN_SCHEMATICS_HANDLER_FABRIC.renderOverlay(graphics, partialTicks, window);
        });
    }
    
}
