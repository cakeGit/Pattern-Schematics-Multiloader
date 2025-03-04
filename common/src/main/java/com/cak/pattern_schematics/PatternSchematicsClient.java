package com.cak.pattern_schematics;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import com.cak.pattern_schematics.registry.PlatformPackets;
import com.simibubi.create.foundation.ponder.CreatePonderPlugin;
import net.createmod.ponder.foundation.PonderIndex;

public class PatternSchematicsClient {
  
  /**Treat as final, the variable is initialised by platform*/
  public static PatternSchematicHandler PATTERN_SCHEMATIC_HANDLER;

  public static void init() {
    PlatformPackets.getChannel().initClientListener();
    PonderIndex.addPlugin(new CreatePonderPlugin());
  }
  
}
