package com.cak.pattern_schematics.fabric.mixin;

import io.github.fabricators_of_create.porting_lib.transfer.item.ItemStackHandlerSlot;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ItemStackHandlerSlot.class, remap = false)
public class AntiCreativeCrateCrashMixin {
    
    @Shadow private ItemStack stack;
    
    @Inject(method = "save", at = @At("HEAD"), cancellable = true)
    public void save(CallbackInfoReturnable<CompoundTag> cir) {
        if (stack == null) cir.setReturnValue(null);
    }
    
}
