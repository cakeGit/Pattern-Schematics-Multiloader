package com.cak.pattern_schematics.mixin;

import com.simibubi.create.content.trains.track.TrackTargetingBehaviour;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.utility.BlockHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockHelper.class)
public class BlockHelperMixin {
    
    /**Fix to issue, thanks to @zetttabyte on Discord*/
    @Inject(method = "prepareBlockEntityData", at = @At(value = "RETURN"), remap = false, cancellable = true)
    private static void trainTargeting_prepareBlockEntityData(BlockState blockState, BlockEntity blockEntity, CallbackInfoReturnable<CompoundTag> cir) {
        if (blockEntity instanceof SmartBlockEntity smartBlockEntity) {
            if (smartBlockEntity.getBehaviour(TrackTargetingBehaviour.TYPE) != null) {
                CompoundTag tag = cir.getReturnValue();
                tag.remove("Id");
                cir.setReturnValue(tag);
            }
        }
    }
    
}
