package com.cak.pattern_schematics.fabric;

import net.fabricmc.loader.api.FabricLoader;

public class PlatformGetterImpl {
    
    public static String platformName() {
        return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
    }
    
}
