package com.cak.pattern_schematics.platform.foundation.mirror.forge;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.schematics.SchematicInstances;
import com.simibubi.create.content.schematics.client.SchematicRenderer;
import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import net.createmod.catnip.outliner.AABBOutline;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class PatternSchematicHandlerForge extends PatternSchematicHandler {

  @Override
  public void render(ForgeGui gui, GuiGraphics graphics, float partialTicks, int width, int height) {
    if (Minecraft.getInstance().options.hideGui || !active)
      return;
    if (activeSchematicItem != null)
      this.overlay.renderOn(graphics, activeHotbarSlot);
    currentTool.getTool()
        .renderOverlay(gui, graphics, partialTicks, width, height);
    selectionScreen.renderPassive(graphics, partialTicks);
  }
  
  public void printInstantly() {
    AllPackets.getChannel().sendToServer(new SchematicPlacePacket(activeSchematicItem.copy()));
    CompoundTag nbt = this.activeSchematicItem.getTag();
    nbt.putBoolean("Deployed", false);
    this.deployed = false;
    this.active = false;
    this.activeSchematicItem.setTag(nbt);
    SchematicInstances.clearHash(this.activeSchematicItem);
    this.markDirty();
  }

  public AABBOutline getOutline() {
    return outline;
  }
  
}
