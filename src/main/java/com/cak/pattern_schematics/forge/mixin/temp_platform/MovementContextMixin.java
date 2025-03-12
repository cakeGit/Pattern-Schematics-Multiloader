package com.cak.pattern_schematics.forge.mixin.temp_platform;

import com.cak.pattern_schematics.foundation.mixin_accessors.MovementContextAccessor;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MovementContext.class, remap = false)
public class MovementContextMixin implements MovementContextAccessor {
    
    @Shadow private FilterItemStack filter;

    @Shadow public Level world;

    @Shadow public CompoundTag blockEntityData;

    @Override
    public void pattern_schematics$setFilter(ItemStack stack) {
        filter = FilterItemStack.of(world.registryAccess(), blockEntityData.getCompound("Filter"));
    }
    
}
