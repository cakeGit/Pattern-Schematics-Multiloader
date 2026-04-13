package com.cak.pattern_schematics.content.ponder;

import net.createmod.ponder.foundation.PonderIndex;

public class PatternSchematicsPonderIndex {
    public static void register() {
        PonderIndex.addPlugin(new PatternSchematicsPonderPlugin());
    }
}
