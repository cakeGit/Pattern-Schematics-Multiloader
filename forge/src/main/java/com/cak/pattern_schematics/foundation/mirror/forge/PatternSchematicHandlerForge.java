package com.cak.pattern_schematics.foundation.mirror.forge;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import com.simibubi.create.AllDataComponents;
import com.simibubi.create.content.schematics.SchematicInstances;
import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import net.createmod.catnip.outliner.AABBOutline;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

public class PatternSchematicHandlerForge extends PatternSchematicHandler {

  public void render(GuiGraphics guiGraphics, DeltaTracker deltaTracker) {
    Minecraft mc = Minecraft.getInstance();
    if (mc.options.hideGui || !active)
      return;
    if (activeSchematicItem != null)
      this.overlay.renderOn(guiGraphics, activeHotbarSlot);
    this.currentTool.getTool().renderOverlay(mc.gui, guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(false), guiGraphics.guiWidth(), guiGraphics.guiHeight());
    this.selectionScreen.renderPassive(guiGraphics, deltaTracker.getGameTimeDeltaPartialTick(false));
  }

  public void printInstantly() {
    CatnipServices.NETWORK.sendToServer(new SchematicPlacePacket(this.activeSchematicItem.copy()));
    this.activeSchematicItem.set(AllDataComponents.SCHEMATIC_DEPLOYED, false);
    SchematicInstances.clearHash(this.activeSchematicItem);
    this.renderers.forEach((r) -> r.setActive(false));
    this.active = false;
    this.markDirty();
  }

  public AABBOutline getOutline() {
    return outline;
  }
  
}
