package com.cak.pattern_schematics.foundation.mirror;

import com.cak.pattern_schematics.PatternSchematics;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllKeys;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.utility.Components;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;
import java.util.function.Consumer;

/**Same fate as {@link PatternSchematicsToolSelectionScreen}, mixins defeated me, thus, a "mirror" class (copy paste lol)*/
public class PatternSchematicsToolSelectionScreen extends Screen {
  
  public final String scrollToCycle = Lang.translateDirect("gui.toolmenu.cycle")
      .getString();
  public final String holdToFocus = "gui.toolmenu.focusKey";
  
  protected List<PatternSchematicsToolType> tools;
  protected Consumer<PatternSchematicsToolType> callback;
  public boolean focused;
  private float yOffset;
  protected int selection;
  private boolean initialized;
  
  protected int w;
  protected int h;
  
  public PatternSchematicsToolSelectionScreen(List<PatternSchematicsToolType> tools, Consumer<PatternSchematicsToolType> callback) {
    super(Components.literal("Tool Selection"));
    this.minecraft = Minecraft.getInstance();
    this.tools = tools;
    this.callback = callback;
    focused = false;
    yOffset = 0;
    selection = 0;
    initialized = false;
    
    callback.accept(tools.get(selection));
    
    w = Math.max(tools.size() * 50 + 30, 220);
    h = 30;
  }
  
  public void setSelectedElement(PatternSchematicsToolType tool) {
    if (!tools.contains(tool))
      return;
    selection = tools.indexOf(tool);
  }
  
  public void cycle(int direction) {
    selection += (direction < 0) ? 1 : -1;
    selection = (selection + tools.size()) % tools.size();
  }
  
  private void draw(GuiGraphics graphics, float partialTicks) {
    PoseStack matrixStack = graphics.pose();
    Window mainWindow = minecraft.getWindow();
    if (!initialized)
      init(minecraft, mainWindow.getGuiScaledWidth(), mainWindow.getGuiScaledHeight());
    
    int x = (mainWindow.getGuiScaledWidth() - w) / 2 + 15;
    int y = mainWindow.getGuiScaledHeight() - h - 75;
    
    matrixStack.pushPose();
    matrixStack.translate(0, -yOffset, focused ? 100 : 0);
    
    AllGuiTextures gray = AllGuiTextures.HUD_BACKGROUND;
    ResourceLocation patternSchematicsGray = PatternSchematics.asResource(gray.location.getPath());
    RenderSystem.enableBlend();
    RenderSystem.setShaderColor(1, 1, 1, focused ? 7 / 8f : 1 / 2f);
    
    graphics.blit(patternSchematicsGray, x - 15, y, gray.startX, gray.startY, w, h, gray.width, gray.height);
    
    float toolTipAlpha = yOffset / 10;
    List<Component> toolTip = tools.get(selection)
        .getDescription();
    int stringAlphaComponent = ((int) (toolTipAlpha * 0xFF)) << 24;
    
    if (toolTipAlpha > 0.25f) {
      RenderSystem.setShaderColor(.8f, .7f, .8f, toolTipAlpha);
      graphics.blit(patternSchematicsGray, x - 15, y + 33, gray.startX, gray.startY, w, h + 22, gray.width, gray.height);
      RenderSystem.setShaderColor(1, 1, 1, 1);
      
      if (toolTip.size() > 0)
        graphics.drawString(font, toolTip.get(0), x - 10, y + 38, 0xEEEEEE + stringAlphaComponent, false);
      if (toolTip.size() > 1)
        graphics.drawString(font, toolTip.get(1), x - 10, y + 50, 0xEECDEE + stringAlphaComponent, false);
      if (toolTip.size() > 2)
        graphics.drawString(font, toolTip.get(2), x - 10, y + 60, 0xEECDEE + stringAlphaComponent, false);
      if (toolTip.size() > 3)
        graphics.drawString(font, toolTip.get(3), x - 10, y + 72, 0xDDCCDD + stringAlphaComponent, false);
    }
    
    RenderSystem.setShaderColor(1, 1, 1, 1);
    if (tools.size() > 1) {
      String keyName = AllKeys.TOOL_MENU.getBoundKey();
      int width = minecraft.getWindow()
          .getGuiScaledWidth();
      if (!focused)
        graphics.drawCenteredString(minecraft.font, Lang.translateDirect(holdToFocus, keyName), width / 2,
            y - 10, 0xEECDEE);
      else
        graphics.drawCenteredString(minecraft.font, scrollToCycle, width / 2, y - 10, 0xEECDEE);
    } else {
      x += 65;
    }
    
    
    for (int i = 0; i < tools.size(); i++) {
      RenderSystem.enableBlend();
      matrixStack.pushPose();
      
      float alpha = focused ? 1 : .2f;
      if (i == selection) {
        matrixStack.translate(0, -10, 0);
        RenderSystem.setShaderColor(1, 1, 1, 1);
        graphics.drawCenteredString(minecraft.font, tools.get(i)
            .getDisplayName()
            .getString(), x + i * 50 + 24, y + 28, 0xEECDEE);
        alpha = 1;
      }
      RenderSystem.setShaderColor(0, 0, 0, alpha);
      tools.get(i)
          .getIcon()
          .render(graphics, x + i * 50 + 16, y + 12);
      RenderSystem.setShaderColor(1, 1, 1, alpha);
      tools.get(i)
          .getIcon()
          .render(graphics, x + i * 50 + 16, y + 11);
      
      matrixStack.popPose();
    }
    
    RenderSystem.disableBlend();
    matrixStack.popPose();
  }
  
  public void update() {
    if (focused)
      yOffset += (10 - yOffset) * .1f;
    else
      yOffset *= .9f;
  }
  
  public void renderPassive(GuiGraphics graphics, float partialTicks) {
    draw(graphics, partialTicks);
  }
  
  @Override
  public void onClose() {
    callback.accept(tools.get(selection));
  }
  
  @Override
  protected void init() {
    super.init();
    initialized = true;
  }
  
}