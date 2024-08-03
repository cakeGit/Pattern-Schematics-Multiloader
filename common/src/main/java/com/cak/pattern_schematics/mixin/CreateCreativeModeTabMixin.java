package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.infrastructure.item.CreateCreativeModeTab;
import com.tterrag.registrate.util.entry.RegistryEntry;
import net.minecraft.world.item.Item;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(value = CreateCreativeModeTab.class, remap = false)
public class CreateCreativeModeTabMixin {
    
    @Inject(method = "registeredItems", at = @At("RETURN"), cancellable = true)
    private void additional_registeredItems(CallbackInfoReturnable<Collection<RegistryEntry<Item>>> cir) {
        Collection<RegistryEntry<Item>> items = cir.getReturnValue();
        items.add(PatternSchematicsRegistry.EMPTY_PATTERN_SCHEMATIC);
        cir.setReturnValue(items);
    }
    
}
