package com.cak.pattern_schematics.registry;

import com.cak.pattern_schematics.PatternSchematics;

/**Currently not functional since there's no datagen, but also there isn't much of a need for datagen now the mdo is done so eeh*/
public class PatternSchematicsLang {
  
  public static void register() {
    putLang(
        "create_pattern_schematics.schematic.tool.clone", "Clone",
        "create_pattern_schematics.schematic.tool.clone.description.0", "Repeats the selected schematic",
        "create_pattern_schematics.schematic.tool.clone.description.1", "Point at the Schematic and [CTRL]-Scroll to repeat it.",
        "create_pattern_schematics.schematic.tool.clone.description.2", "",
        "create_pattern_schematics.schematic.tool.clone.description.3", ""
    );
  }
  
  public static void putLang(String... entryKeyPairs) {
    for (int i = 0; i < entryKeyPairs.length; i+=2) {
      PatternSchematicsItems.REGISTRATE
          .addRawLang(entryKeyPairs[i], entryKeyPairs[i+1]);
    }
  }
  
}