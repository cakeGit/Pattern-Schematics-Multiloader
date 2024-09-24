package com.cak.pattern_schematics.registry;

import java.util.function.BiConsumer;

import static com.cak.pattern_schematics.PatternSchematics.REGISTRATE;

public class PatternSchematicsLang {
  
  public static BiConsumer<String, String> ENTRY_CONSUMER = REGISTRATE::addRawLang;
  
  public static void register() {
    putLang(
        "create_pattern_schematics.schematic.tool.clone", "Clone",
        "create_pattern_schematics.schematic.tool.clone.description.0", "Repeats the selected schematic",
        "create_pattern_schematics.schematic.tool.clone.description.1", "Point at the Schematic and [CTRL]-Scroll to repeat it.",
        "create_pattern_schematics.schematic.tool.clone.description.2", "Alternatively, [CTRL+SHIFT]-Scroll to clone in the direction you are facing",
        "create_pattern_schematics.schematic.tool.clone.description.3", "",
        "create_pattern_schematics.ponder.schematic_printing.header", "Printing with pattern schematics",
        "create_pattern_schematics.contraption_application.applied_to", "Applied pattern schematic to ",
        "create_pattern_schematics.contraption_application.deployers", " deployer(s)",
        "create_pattern_schematics.contraption_application.not_positioned", "Couldn't apply schematic: Schematic not positioned!"
    );
  }
  
  public static void putLang(String... entryKeyPairs) {
    for (int i = 0; i < entryKeyPairs.length; i+=2) {
      REGISTRATE.addRawLang(entryKeyPairs[i], entryKeyPairs[i+1]);
    }
  }
  
}
