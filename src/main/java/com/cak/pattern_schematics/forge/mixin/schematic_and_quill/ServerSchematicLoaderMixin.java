package com.cak.pattern_schematics.forge.mixin.schematic_and_quill;

import com.cak.pattern_schematics.content.item.PatternSchematicItem;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.content.schematics.SchematicItem;
import com.simibubi.create.content.schematics.ServerSchematicLoader;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ServerSchematicLoader.class)
public class ServerSchematicLoaderMixin {

    @Unique
    private static boolean pattern_Schematics$isHandlingInstantPatternSchematic = false;

    @Redirect(method = "handleInstantSchematic", at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/ItemEntry;isIn(Lnet/minecraft/world/item/ItemStack;)Z"))
    public boolean handleInstantSchematic_isIn(ItemEntry<?> instance, ItemStack itemStack) {
        pattern_Schematics$isHandlingInstantPatternSchematic = PatternSchematicsRegistry.PATTERN_SCHEMATIC_AND_QUILL.isIn(itemStack);
        return instance.isIn(itemStack) || PatternSchematicsRegistry.PATTERN_SCHEMATIC_AND_QUILL.isIn(itemStack);
    }

    @Redirect(method = "handleInstantSchematic", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/schematics/SchematicItem;create(Lnet/minecraft/world/level/Level;Ljava/lang/String;Ljava/lang/String;)Lnet/minecraft/world/item/ItemStack;"))
    public ItemStack handleInstantSchematic_create(Level level, String schematic, String owner) {
        if (pattern_Schematics$isHandlingInstantPatternSchematic) {
            return PatternSchematicItem.create(level, schematic, owner);
        }
        return SchematicItem.create(level, schematic, owner);
    }



}
