package com.cak.pattern_schematics;

import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderIndex;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderTags;
import com.cak.pattern_schematics.registry.PlatformPackets;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
public class PatternSchematics {
    // Define mod id in a common place for everything to reference
    public static final String MODID = "create_pattern_schematics";
    // Directly reference a slf4j logger
    private static final Logger LOGGER = LogUtils.getLogger();
    
    public static void init() {
        PatternSchematicsRegistry.register();
        PlatformPackets.registerPackets();
        PlatformPackets.getChannel().initServerListener();
        PatternSchematicsPonderTags.register();
        PatternSchematicsPonderIndex.register();
    }
    
    public static ResourceLocation asResource(String loc) {
        return new ResourceLocation(MODID, loc);
    }
    
}
