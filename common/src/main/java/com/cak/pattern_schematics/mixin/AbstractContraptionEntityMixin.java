package com.cak.pattern_schematics.mixin;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Adding interaction for applying schematics onto deployers that are in assembled schematics
 * */
@Mixin(value = AbstractContraptionEntity.class, remap = false)
public class AbstractContraptionEntityMixin {
    
//    @Shadow protected Contraption contraption;
//
//    @Inject(method = "handlePlayerInteraction", at = @At("HEAD"), cancellable = true)
//    public void handlePlayerInteraction(
//        Player player, BlockPos localPos, Direction side,
//        InteractionHand interactionHand, CallbackInfoReturnable<Boolean> cir
//    ) {
//        Pair<StructureTemplate.StructureBlockInfo, MovementContext> actor = contraption.getActorAt(localPos);
//        if (actor != null && actor.getLeft().state().is(AllBlocks.DEPLOYER.get()))  {
//            Create.LOGGER.info("Found deployer");
//            cir.setReturnValue(true);
//        }
//    }
    
}
