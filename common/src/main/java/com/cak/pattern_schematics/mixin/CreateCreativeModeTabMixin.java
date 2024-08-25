package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.registry.PatternSchematicsTabInsertions;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(remap = false, targets = "com.simibubi.create.AllCreativeModeTabs$RegistrateDisplayItemsGenerator")
public class CreateCreativeModeTabMixin {
    
    @Redirect(method = "collectItems", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
    private boolean addAdditionalItemInject(List<Item> instance, Object element) {
        Item itemToAdd = (Item) element;
        //Ensure execution order, add the instance then add other
        boolean result = instance.add(itemToAdd);
        
        if (
            PatternSchematicsTabInsertions.getAllInsertsAfter()
                .containsKey(itemToAdd)
        ) {
            instance.add(
                PatternSchematicsTabInsertions.getAllInsertsAfter()
                    .get(itemToAdd)
            );
        }
        
        return result;
    }
    
}
