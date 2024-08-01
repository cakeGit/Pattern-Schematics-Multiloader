package com.cak.pattern_schematics;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import com.cak.pattern_schematics.registry.PatternSchematicPackets;

public class PatternSchematicsClient {
  
  /**Treat as final, the variable is initialised by platform*/
  public static PatternSchematicHandler PATTERN_SCHEMATIC_HANDLER;

  public static void init() {
    PatternSchematicPackets.getChannel().initClientListener();
  }
  
}
