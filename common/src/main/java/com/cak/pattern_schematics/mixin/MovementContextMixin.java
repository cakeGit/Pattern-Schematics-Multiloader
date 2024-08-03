package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.foundation.mixin_accessors.MovementContextAccessor;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MovementContext.class)
public class MovementContextMixin implements MovementContextAccessor {
    
    @Shadow private FilterItemStack filter;
    
    @Override
    public void pattern_schematics$setFilter(FilterItemStack stack) {
        filter = stack;
    }
    
}
