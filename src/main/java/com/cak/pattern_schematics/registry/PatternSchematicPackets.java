package com.cak.pattern_schematics.registry;

import com.cak.pattern_schematics.PatternSchematics;
import net.minecraft.resources.ResourceLocation;

public class PatternSchematicPackets {
    
    public static final ResourceLocation CHANNEL_NAME = PatternSchematics.asResource("main");
    
    public static void registerPackets() {
        // Packet registration is handled by FabricNetworker.initServerListener()
    }
    
}
