package com.cak.pattern_schematics.fabric;

import com.cak.pattern_schematics.PatternSchematicsClientEvents;
import io.github.fabricators_of_create.porting_lib.event.client.KeyInputCallback;
import io.github.fabricators_of_create.porting_lib.event.client.MouseInputEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.minecraft.client.Minecraft;

public class PatternSchematicsFabricClientEvents {
    
    public static void registerListeners() {
        ClientTickEvents.START_CLIENT_TICK.register((mc) ->
            PatternSchematicsClientEvents.onTick());
        WorldRenderEvents.AFTER_TRANSLUCENT.register((context) ->
            PatternSchematicsClientEvents.renderPatternSchematic(context.matrixStack()));
        
        KeyInputCallback.EVENT.register((key, scancode, action, mods) -> {
            if (Minecraft.getInstance().screen != null) return;
            boolean pressed = !(action == 0);
            PatternSchematicsClientEvents.onKeyInput(key, pressed);
        });
        MouseInputEvents.BEFORE_SCROLL.register((double deltaX, double delta) -> {
            if (Minecraft.getInstance().screen != null)
                return false;
            
            return PatternSchematicsClientEvents.onMouseScrolled(delta);
        });
        MouseInputEvents.BEFORE_BUTTON.register((button, modifiers, action) -> {
            if (Minecraft.getInstance().screen != null)
                return false;
            
            boolean pressed = action == MouseInputEvents.Action.PRESS;
            
            return PatternSchematicsClientEvents.onMouseInput(button, pressed);
        });
    }
    
}
