package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.registry.PatternSchematicsTabInsertions;
import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = CreateCreativeModeTab.class, remap = false)
public class CreateCreativeModeTabMixin {
    
    @Redirect(method = "addItems", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/Item;fillItemCategory(Lnet/minecraft/world/item/CreativeModeTab;Lnet/minecraft/core/NonNullList;)V", remap = true))
    private void addAdditionalItemInject(Item instance, CreativeModeTab category, NonNullList<ItemStack> items) {
        instance.fillItemCategory(category, items);
        
        if (
            PatternSchematicsTabInsertions.getAllInsertsAfterItem()
                .containsKey(instance)
        ) {
            PatternSchematicsTabInsertions.getAllInsertsAfterItem()
                .get(instance).fillItemCategory(category, items);
        }
    }
    
}
