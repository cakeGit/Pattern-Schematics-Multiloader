package com.cak.pattern_schematics;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;

public class PatternSchematicsFabricClientEvents {
    
    public static void registerListeners() {
        ClientTickEvents.START_CLIENT_TICK.register((mc) ->
            PatternSchematicsClientEvents.onTick());
        WorldRenderEvents.AFTER_TRANSLUCENT.register((context) ->
            PatternSchematicsClientEvents.renderPatternSchematic(context.matrixStack()));
    }
    
    // Called from KeyboardHandlerMixin
    public static void onKeyInput(int key, int scancode, int action, int mods) {
        if (Minecraft.getInstance().screen != null) return;
        boolean pressed = !(action == 0);
        PatternSchematicsClientEvents.onKeyInput(key, pressed);
    }
    
    // Called from MouseHandlerMixin
    public static boolean onMouseScroll(double deltaX, double delta) {
        if (Minecraft.getInstance().screen != null)
            return false;
        return PatternSchematicsClientEvents.onMouseScrolled(delta);
    }
    
    // Called from MouseHandlerMixin
    public static boolean onMouseButton(int button, int action) {
        if (Minecraft.getInstance().screen != null)
            return false;
        boolean pressed = action == 1; // GLFW_PRESS
        return PatternSchematicsClientEvents.onMouseInput(button, pressed);
    }
    
}
