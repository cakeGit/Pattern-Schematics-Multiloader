package com.cak.pattern_schematics.foundation.mirror.forge;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllPackets;
import com.simibubi.create.content.schematics.SchematicInstances;
import com.simibubi.create.content.schematics.packet.SchematicPlacePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.client.gui.overlay.ForgeGui;

public class PatternSchematicHandlerForge extends PatternSchematicHandler {

  @Override
  public void render(ForgeGui gui, PoseStack graphics, float partialTicks, int width, int height) {
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
    CompoundTag nbt = activeSchematicItem.getTag();
    nbt.putBoolean("Deployed", false);
    activeSchematicItem.setTag(nbt);
    SchematicInstances.clearHash(activeSchematicItem);
    renderers.forEach(r -> r.setActive(false));
    active = false;
    markDirty();
  }
  
}
