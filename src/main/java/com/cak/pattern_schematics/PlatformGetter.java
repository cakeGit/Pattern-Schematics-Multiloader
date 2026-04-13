package com.cak.pattern_schematics;

import net.fabricmc.loader.api.FabricLoader;

public class PlatformGetter {
    public static String platformName() {
        return FabricLoader.getInstance().isModLoaded("quilt_loader") ? "Quilt" : "Fabric";
    }
}
