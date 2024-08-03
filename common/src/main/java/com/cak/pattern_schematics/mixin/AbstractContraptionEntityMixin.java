package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.foundation.mixin_accessors.MovementContextAccessor;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.Contraption;
import com.simibubi.create.content.contraptions.behaviour.MovementContext;
import com.simibubi.create.content.kinetics.deployer.DeployerBlock;
import com.simibubi.create.content.logistics.filter.FilterItemStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Adding interaction for applying schematics onto deployers that are in assembled schematics
 */
@Mixin(value = AbstractContraptionEntity.class, remap = false)
public class AbstractContraptionEntityMixin {
    
    @Shadow
    protected Contraption contraption;
    
    @Inject(method = "handlePlayerInteraction", at = @At("HEAD"), cancellable = true)
    public void handlePlayerInteraction(
        Player player, BlockPos localPos, Direction side,
        InteractionHand interactionHand, CallbackInfoReturnable<Boolean> cir
    ) {
        if (player.level().isClientSide || !player.isShiftKeyDown() || !player.getItemInHand(interactionHand).is(PatternSchematicsRegistry.PATTERN_SCHEMATIC.get()))
            return;
        
        Pair<StructureTemplate.StructureBlockInfo, MovementContext> actor = contraption.getActorAt(localPos);
        
        if (actor == null || !actor.getLeft().state().is(AllBlocks.DEPLOYER.get()))
            return;
        
        int appliedCount = pattern_schematics$performBulkSchematicApply(actor.getLeft().state().getValue(DeployerBlock.FACING), actor.getRight().localPos, player.getItemInHand(interactionHand));
        
        player.displayClientMessage(
            Component.translatable("create_pattern_schematics.contraption_application.applied_to")
                .append(Component.literal(String.valueOf(appliedCount)))
                .append(Component.translatable("create_pattern_schematics.contraption_application.deployers")),
            true
        );
        
        cir.setReturnValue(true);
    }
    
    @Unique
    private int pattern_schematics$performBulkSchematicApply(Direction facingDirection, BlockPos localPos, ItemStack item) {
        Set<MovementContext> deployerActors = pattern_schematics$collectDeployerSurface(facingDirection, localPos);
        
        FilterItemStack newFilter = FilterItemStack.of(item);
        CompoundTag newFilterTag = newFilter.serializeNBT();
        
        for (MovementContext context : deployerActors) {
            context.blockEntityData.put("Filter", newFilterTag.copy());
            ((MovementContextAccessor) context).pattern_schematics$setFilter(newFilter);
        }
        return deployerActors.size();
    }
    
    @Unique
    private Set<MovementContext> pattern_schematics$collectDeployerSurface(Direction facingDirection, BlockPos localPos) {
        Set<MovementContext> allDeployers = contraption.getActors().stream()
            .filter(actor ->
                actor.getLeft().state().is(AllBlocks.DEPLOYER.get())
                    && actor.getLeft().state().getValue(DeployerBlock.FACING) == facingDirection
            )
            .map(MutablePair::getRight).collect(Collectors.toSet());
        
        return pattern_schematics$getConnectedDeployers(facingDirection, localPos, allDeployers);
    }
    
    @Unique
    private Set<MovementContext> pattern_schematics$getConnectedDeployers(
        Direction facingDirection, BlockPos localPos, Set<MovementContext> allDeployers
    ) {
        HashMap<BlockPos, MovementContext> deployerPositions = new HashMap<>();
        allDeployers.forEach(context -> deployerPositions.put(context.localPos, context));
        
        Set<BlockPos> frontier = new HashSet<>();
        pattern_schematics$searchForConnected(pattern_schematics$getSearchDirections(facingDirection), localPos, deployerPositions, frontier);
        
//        Create.LOGGER.info("Found {} connected deployers", frontier.size());
        
        Set<MovementContext> validDeployers = new HashSet<>();
        for (BlockPos pos : frontier) {
            validDeployers.add(deployerPositions.get(pos));
        }
        return validDeployers;
    }
    
    @Unique
    private void pattern_schematics$searchForConnected(Set<Direction> directions, BlockPos currentPos, HashMap<BlockPos, MovementContext> deployerPositions, Set<BlockPos> frontier) {
        if (frontier.contains(currentPos) || !deployerPositions.containsKey(currentPos) || frontier.size() > 512) return;
        
        //This block is a deployer
        frontier.add(currentPos);
        for (Direction direction : directions) {
            pattern_schematics$searchForConnected(directions, currentPos.offset(direction.getNormal()), deployerPositions, frontier);
        }
    }
    
    @Unique
    private Set<Direction> pattern_schematics$getSearchDirections(Direction facingDirection) {
        return Arrays.stream(Direction.values()).filter(direction -> direction.getAxis() != facingDirection.getAxis()).collect(Collectors.toSet());
    }
    
}
