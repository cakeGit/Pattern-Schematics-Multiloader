package com.cak.pattern_schematics;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.createmod.catnip.render.DefaultSuperRenderTypeBuffer;
import net.createmod.catnip.render.SuperRenderTypeBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;

public class PatternSchematicsClientEvents {
    
    public static void onTick() {
        if (Minecraft.getInstance().level == null || Minecraft.getInstance().player == null)
            return;
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER.tick();
    }
    
    public static void renderPatternSchematic(PoseStack ms) {
        ms.pushPose();

        ms.pushPose();
        SuperRenderTypeBuffer buffer = DefaultSuperRenderTypeBuffer.getInstance();

        Vec3 camera = Minecraft.getInstance().gameRenderer.getMainCamera()
            .getPosition();
        
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER.render(ms, buffer, camera);
        
        buffer.draw();
        RenderSystem.enableCull();
        ms.popPose();
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

}
