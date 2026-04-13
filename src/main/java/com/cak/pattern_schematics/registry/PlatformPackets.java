package com.cak.pattern_schematics.registry;

import com.cak.pattern_schematics.foundation.GenericNetworker;
import com.cak.pattern_schematics.foundation.FabricNetworker;
import com.cak.pattern_schematics.packet.PatternSchematicSyncPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class PlatformPackets {
    
    public static GenericNetworker getChannel() {
        return FabricNetworker.of(PatternSchematicPackets.CHANNEL_NAME);
    }
    
    public static void registerPackets() {
        PatternSchematicPackets.registerPackets();
    }
    
    public static void sendPatternSchematicSyncPacket(
        int slot, StructurePlaceSettings settings,
        BlockPos anchor, boolean deployed,
        Vec3i cloneScaleMin, Vec3i cloneScaleMax, Vec3i cloneOffset
    ) {
        getChannel().sendToServer(new PatternSchematicSyncPacket(
            slot, settings, anchor, deployed, cloneScaleMin, cloneScaleMax, cloneOffset
        ));
    }
}
