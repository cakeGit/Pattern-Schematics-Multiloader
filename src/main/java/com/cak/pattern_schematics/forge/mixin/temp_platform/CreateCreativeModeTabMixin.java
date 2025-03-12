package com.cak.pattern_schematics.forge.mixin.temp_platform;

import com.cak.pattern_schematics.registry.PatternSchematicsTabInsertions;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(remap = false, targets = "com.simibubi.create.AllCreativeModeTabs$RegistrateDisplayItemsGenerator")
public class CreateCreativeModeTabMixin {

    @Redirect(method = "outputAll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/CreativeModeTab$Output;accept(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/item/CreativeModeTab$TabVisibility;)V"))
    private static void addAdditionalItemInject(CreativeModeTab.Output instance, ItemStack itemStack, CreativeModeTab.TabVisibility tabVisibility) {
        Item itemToAdd = itemStack.getItem();
        //Ensure execution order, add the instance then add otherg
        if (
            PatternSchematicsTabInsertions.getAllInsertsAfter()
                .containsKey(itemToAdd)
        ) {
            instance.accept(PatternSchematicsTabInsertions.getAllInsertsAfter()
                .get(itemToAdd).getDefaultInstance(), CreativeModeTab.TabVisibility.PARENT_TAB_ONLY);
        }

        instance.accept(itemStack, tabVisibility);
    }

}
