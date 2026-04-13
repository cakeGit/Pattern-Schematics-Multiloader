package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.PatternSchematicsClient;
import com.simibubi.create.CreateClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CreateClient.class, remap = false)
public class CreateClientMixin {
    
    @Inject(method = "invalidateRenderers", at = @At("HEAD"))
    private static void invalidateRenderers(CallbackInfo ci) {
        if (PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER != null)
            PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER.updateRenderers();
    }
    
}
