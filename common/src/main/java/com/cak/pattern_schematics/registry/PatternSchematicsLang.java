package com.cak.pattern_schematics.registry;

import java.util.function.BiConsumer;

/**Currently not functional since there's no datagen, but also there isn't much of a need for datagen now the mdo is done so eeh*/
public class PatternSchematicsLang {
  
  public static BiConsumer<String, String> ENTRY_CONSUMER = PatternSchematicsRegistry.REGISTRATE::addRawLang;
  
  public static void register() {
    putLang(
        "create_pattern_schematics.schematic.tool.clone", "Clone",
        "create_pattern_schematics.schematic.tool.clone.description.0", "Repeats the selected schematic",
        "create_pattern_schematics.schematic.tool.clone.description.1", "Point at the Schematic and [CTRL]-Scroll to repeat it.",
        "create_pattern_schematics.schematic.tool.clone.description.2", "",
        "create_pattern_schematics.schematic.tool.clone.description.3", "",
        "create_pattern_schematics.ponder.schematic_printing.header", "Printing with pattern schematics",
        "create_pattern_schematics.contraption_application.applied_to", "Applied pattern schematic to ",
        "create_pattern_schematics.contraption_application.deployers", " deployer(s)"
    );
  }
  
  public static void putLang(String... entryKeyPairs) {
    for (int i = 0; i < entryKeyPairs.length; i+=2) {
      PatternSchematicsRegistry.REGISTRATE
          .addRawLang(entryKeyPairs[i], entryKeyPairs[i+1]);
    }
  }
  
}
