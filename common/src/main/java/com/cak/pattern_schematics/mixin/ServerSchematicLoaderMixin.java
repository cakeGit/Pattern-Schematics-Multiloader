package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.foundation.mixin_accessors.SchematicTableBlockEntityMixinAccessor;
import com.simibubi.create.content.schematics.ServerSchematicLoader;
import com.simibubi.create.content.schematics.table.SchematicTableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ServerSchematicLoader.class, remap = false)
public class ServerSchematicLoaderMixin {
  
  private static SchematicTableBlockEntity uploadTargetTable;
  
  @Redirect(method = "handleFinishedUpload", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/ServerSchematicLoader;getTable(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;)Lcom/simibubi/create/content/schematics/table/SchematicTableBlockEntity;", remap = true))
  private SchematicTableBlockEntity injected(ServerSchematicLoader instance, Level world, BlockPos pos) {
    uploadTargetTable = instance.getTable(world, pos);
    return instance.getTable(world, pos);
  }
  
  @Redirect(method = "handleFinishedUpload", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/SchematicItem;create(Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/world/item/ItemStack;", remap = true))
  private ItemStack injected(String schematic, String owner) {
    return ((SchematicTableBlockEntityMixinAccessor) uploadTargetTable).getSchematicSource()
        .getFactory().create(schematic, owner);
  }

}
