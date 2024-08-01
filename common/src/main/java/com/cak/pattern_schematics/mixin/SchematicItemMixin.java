package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.content.PatternSchematicItem;
import com.simibubi.create.content.schematics.SchematicItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = SchematicItem.class, remap = false)
public class SchematicItemMixin {
  
  @Inject(method = "onItemUse", at = @At(value = "HEAD"), cancellable = true)
  public void onItemUse(Player player, InteractionHand hand, CallbackInfoReturnable<Boolean> cir) {
    if (player.getItemInHand(hand).getItem() instanceof PatternSchematicItem)
      cir.setReturnValue(false);
  }
  
}
