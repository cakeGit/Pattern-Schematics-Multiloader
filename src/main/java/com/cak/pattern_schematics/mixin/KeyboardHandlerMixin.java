package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.PatternSchematicsFabricClientEvents;
import net.minecraft.client.KeyboardHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
    
    @Inject(method = "keyPress", at = @At("TAIL"))
    private void onKeyPress(long window, int key, int scancode, int action, int mods, CallbackInfo ci) {
        PatternSchematicsFabricClientEvents.onKeyInput(key, scancode, action, mods);
    }
    
}
