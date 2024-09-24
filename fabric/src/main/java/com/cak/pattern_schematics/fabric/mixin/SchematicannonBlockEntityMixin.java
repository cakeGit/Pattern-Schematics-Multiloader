package com.cak.pattern_schematics.fabric.mixin;

import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.AllItems;
import com.simibubi.create.content.schematics.cannon.SchematicannonBlockEntity;
import com.simibubi.create.content.schematics.cannon.SchematicannonInventory;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Change the second inventory set item call to be the pattern schematic if there was a pattern schematic previously
 * present Note that due to class resolution issues, the mixin has to be duplicated across both platforms
 */
@Mixin(value = SchematicannonBlockEntity.class, remap = false)
public class SchematicannonBlockEntityMixin {
    
    @Shadow
    public SchematicannonInventory inventory;
    @Unique
    private boolean pattern_schematics$currentThreadIsOfPatternSchematic = false;
    
    @Inject(method = "finishedPrinting", at = @At("HEAD"))
    private void finishedPrinting(CallbackInfo ci) {
        pattern_schematics$currentThreadIsOfPatternSchematic = !inventory.getStackInSlot(0).isEmpty() &&
            inventory.getStackInSlot(0).is(PatternSchematicsRegistry.PATTERN_SCHEMATIC.get());
    }
    
    @Redirect(method = "finishedPrinting", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/cannon/SchematicannonInventory;setStackInSlot(ILnet/minecraft/world/item/ItemStack;)V", remap = true))
    private void setStackInSlot(SchematicannonInventory instance, int slot, ItemStack stack) {
        if (slot != 1) {
            instance.setStackInSlot(slot, stack);
            return;
        }
        
        Item resultItem = pattern_schematics$currentThreadIsOfPatternSchematic ?
            PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC.get() :
            AllItems.EMPTY_SCHEMATIC.get();
        
        //Check if the result slot is either empty or matching
        ItemStack current = inventory.getStackInSlot(1);
        boolean resultSlotIsAvailable = current.isEmpty() ||
            current.is(resultItem);
        
        int putSlot = resultSlotIsAvailable ? 1 : 0;
        inventory.setStackInSlot(putSlot, resultItem.getDefaultInstance());
    }
    
}
