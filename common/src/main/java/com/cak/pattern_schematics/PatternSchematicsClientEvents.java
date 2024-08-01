package com.cak.pattern_schematics;

import com.jozufozu.flywheel.event.RenderLayerEvent;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.render.SuperRenderTypeBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class PatternSchematicsClientEvents {
    
    public static void onTick() {
        if (Minecraft.getInstance().level == null || Minecraft.getInstance().player == null)
            return;
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER.tick();
    }
    
    public static void renderPatternSchematic(PoseStack stack) {
        stack.pushPose();
        
        SuperRenderTypeBuffer buffer = SuperRenderTypeBuffer.getInstance();
        
        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera()
            .getPosition();
        
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER.render(stack, buffer, camera);
        
        buffer.draw();
        RenderSystem.enableCull();
        stack.popPose();
    }
    
    public static void onKeyInput(int key, boolean pressed) {
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER.onKeyInput(key, pressed);
    }
    
    public static boolean onMouseScrolled(double delta) {
        if (Minecraft.getInstance().screen != null)
            return false;
        
        return PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER.mouseScrolled(delta);
    }
    
    public static boolean onMouseInput(int button, boolean pressed) {
        if (Minecraft.getInstance().screen != null)
            return false;
        
        return PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER.onMouseInput(button, pressed);
    }
    
//Todo: For foge
//  @Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
//  public static class ModBusEvents {
//
//    @SubscribeEvent
//    public static void registerGuiOverlays(RegisterGuiOverlaysEvent event) {
//      event.registerAbove(VanillaGuiOverlay.HOTBAR.id(), "pattern_schematic", PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER);
//    }
//
//  }

}
