package com.cak.pattern_schematics.foundation.mirror;

import com.cak.pattern_schematics.PatternSchematics;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

public class PatternSchematicHotbarSlotOverlay {
    
    public static final ResourceLocation PATTERN_SCHEMATIC_SLOT = PatternSchematics.asResource("textures/gui/slot_overlay.png");
    
    public void renderOn(GuiGraphics graphics, int slot) {
        Window mainWindow = Minecraft.getInstance().getWindow();
        int x = mainWindow.getGuiScaledWidth() / 2 - 88;
        int y = mainWindow.getGuiScaledHeight() - 19;
        RenderSystem.enableDepthTest();
        graphics.blit(PATTERN_SCHEMATIC_SLOT, x + 20 * slot, y, 0, 0, 16, 16);
    }
    
}
