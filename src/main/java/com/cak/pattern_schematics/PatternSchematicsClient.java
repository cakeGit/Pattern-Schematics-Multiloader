package com.cak.pattern_schematics;

import com.cak.pattern_schematics.content.ponder.PatternSchematicsPonderPlugin;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import net.createmod.ponder.foundation.PonderIndex;

public class PatternSchematicsClient {
  
  /**Treat as final, the variable is initialised by platform*/
  public static PatternSchematicHandler PATTERN_SCHEMATIC_HANDLER;

  public static void init() {
    PonderIndex.addPlugin(new PatternSchematicsPonderPlugin());
  }
  
}
