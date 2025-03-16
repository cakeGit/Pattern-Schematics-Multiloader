package com.cak.pattern_schematics;

import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderPlugin;
import com.cak.pattern_schematics.foundation.mirror.forge.PatternSchematicHandlerForge;
import net.createmod.ponder.foundation.PonderIndex;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = PatternSchematics.MOD_ID, dist = Dist.CLIENT)
public class PatternSchematicsForgeClient {
    
    public static final PatternSchematicHandlerForge PATTERN_SCHEMATICS_HANDLER_FORGE = new PatternSchematicHandlerForge();

    public PatternSchematicsForgeClient() {
        onInitializeClient();
    }

    public static void onInitializeClient() {
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER = PATTERN_SCHEMATICS_HANDLER_FORGE;
        PatternSchematicsClient.init();
    }
    
}
