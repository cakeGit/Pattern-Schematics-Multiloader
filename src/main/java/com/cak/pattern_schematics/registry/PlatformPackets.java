package com.cak.pattern_schematics.registry;

import com.cak.pattern_schematics.foundation.GenericNetworker;
import com.cak.pattern_schematics.platform.registry.forge.PlatformPacketsImpl;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class PlatformPackets {
    
    public static GenericNetworker getChannel() {
        return PlatformPacketsImpl.getChannel();
    }
    
    public static void registerPackets() {
        PlatformPacketsImpl.registerPackets();
    }
    
    public static void sendPatternSchematicSyncPacket(
        int slot, StructurePlaceSettings settings,
        BlockPos anchor, boolean deployed,
        Vec3i cloneScaleMin, Vec3i cloneScaleMax, Vec3i cloneOffset
    ) {
        PlatformPacketsImpl.sendPatternSchematicSyncPacket(
            slot, settings, anchor, deployed, cloneScaleMin, cloneScaleMax, cloneOffset
        );
    }
}
