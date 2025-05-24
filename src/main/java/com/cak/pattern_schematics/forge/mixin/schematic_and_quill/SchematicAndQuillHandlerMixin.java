package com.cak.pattern_schematics.forge.mixin.schematic_and_quill;

import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.client.SchematicAndQuillHandler;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SchematicAndQuillHandler.class)
public abstract class SchematicAndQuillHandlerMixin {

    @Shadow protected abstract boolean isPresent();

    @Inject(method = "isActive", at = @At("RETURN"), cancellable = true)
    private void isActive(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(cir.getReturnValue() || isPresent() && PatternSchematicsRegistry.PATTERN_SCHEMATIC_AND_QUILL.isIn(Minecraft.getInstance().player.getMainHandItem()));
    }

}
