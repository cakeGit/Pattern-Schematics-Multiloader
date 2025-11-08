package com.cak.pattern_schematics.registry;

import com.cak.pattern_schematics.foundation.GenericNetworker;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class PlatformPackets {
    
    @ExpectPlatform
    public static GenericNetworker getChannel() {
        throw new AssertionError();
    }
    
    @ExpectPlatform
    public static void registerPackets() {
        throw new AssertionError();
    }
    
    @ExpectPlatform
    public static void sendPatternSchematicSyncPacket(
        int slot, StructurePlaceSettings settings,
        BlockPos anchor, boolean deployed,
        Vec3i cloneScaleMin, Vec3i cloneScaleMax, Vec3i cloneOffset
    ) {
        throw new AssertionError();
    }
}
