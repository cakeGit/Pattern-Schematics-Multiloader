package com.cak.pattern_schematics.forge.mixin;

import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Platform specific due to a schema difference
 */
@Mixin(value = SchematicannonInventory.class, remap = false)
public class SchematicannonInventoryMixin {
    
    @Inject(method = "isItemValid", at = @At("RETURN"), cancellable = true)
    public void isItemValid(int slot, ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (slot == 0) {
            cir.setReturnValue(
                PatternSchematicsRegistry.PATTERN_SCHEMATIC.get() == stack.getItem()
                    || AllItems.SCHEMATIC.get() == stack.getItem()
            );
        }
    }
    
}
