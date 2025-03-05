package com.cak.pattern_schematics.registry.forge;

import com.cak.pattern_schematics.packet.forge.PatternSchematicSyncPacket;
import net.createmod.catnip.platform.CatnipServices;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class PlatformPacketsImpl {

    public static void registerPackets() {
        PatternSchematicPackets.register();
    }

    public static void sendPatternSchematicSyncPacket(
        int slot, StructurePlaceSettings settings,
        BlockPos anchor, boolean deployed,
        Vec3i cloneScaleMin, Vec3i cloneScaleMax, Vec3i cloneOffset
    ) {
        CatnipServices.NETWORK.sendToServer(new PatternSchematicSyncPacket(
            slot, settings, anchor, deployed, cloneScaleMin, cloneScaleMax, cloneOffset
        ));
    }

}
