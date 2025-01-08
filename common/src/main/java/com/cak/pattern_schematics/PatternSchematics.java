package com.cak.pattern_schematics;

import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.cak.pattern_schematics.registry.PlatformPackets;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.item.ItemDescription;
import com.simibubi.create.foundation.item.KineticStats;
import com.simibubi.create.foundation.item.TooltipHelper;
import com.simibubi.create.foundation.item.TooltipModifier;
import net.minecraft.resources.ResourceLocation;

// The value here should match an entry in the META-INF/mods.toml file
public class PatternSchematics {
    
    public static final String MOD_ID = "create_pattern_schematics";
    
    public static final CreateRegistrate REGISTRATE = CreateRegistrate.create(PatternSchematics.MOD_ID);
    
    static {
        REGISTRATE.setTooltipModifierFactory(item ->
            new ItemDescription.Modifier(item, TooltipHelper.Palette.STANDARD_CREATE)
                .andThen(TooltipModifier.mapNull(KineticStats.create(item)))
        );
    }
    
    public static void init() {
        PatternSchematicsRegistry.register();
        PlatformPackets.registerPackets();
        PlatformPackets.getChannel().initServerListener();
    }
    
    public static ResourceLocation asResource(String loc) {
        return new ResourceLocation(MOD_ID, loc);
    }
    
}
