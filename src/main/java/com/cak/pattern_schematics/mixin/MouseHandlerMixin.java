package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.PatternSchematicsFabricClientEvents;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {
    
    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void onScroll(long window, double deltaX, double deltaY, CallbackInfo ci) {
        if (PatternSchematicsFabricClientEvents.onMouseScroll(deltaX, deltaY)) {
            ci.cancel();
        }
    }
    
    @Inject(method = "onPress", at = @At("HEAD"), cancellable = true)
    private void onPress(long window, int button, int action, int mods, CallbackInfo ci) {
        if (PatternSchematicsFabricClientEvents.onMouseButton(button, action)) {
            ci.cancel();
        }
    }
    
}
