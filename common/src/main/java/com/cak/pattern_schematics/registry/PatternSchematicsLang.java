package com.cak.pattern_schematics.registry;

import java.util.function.BiConsumer;

import static com.cak.pattern_schematics.PatternSchematics.REGISTRATE;

public class PatternSchematicsLang {
    
    public static BiConsumer<String, String> ENTRY_CONSUMER = REGISTRATE::addRawLang;
    
    public static void register() {
        putLang(
            "create_pattern_schematics.schematic.tool.clone", "Clone",
            "create_pattern_schematics.schematic.tool.clone.description.0", "Repeats the selected schematic",
            "create_pattern_schematics.schematic.tool.clone.description.1", "Select and use [CTRL]-Scroll to clone in the current facing direction.",
            "create_pattern_schematics.schematic.tool.clone.description.2", "Alternatively, [CTRL+SHIFT]-Scroll to clone on the selected surface.",
            "create_pattern_schematics.schematic.tool.clone.description.3", "",
            "create_pattern_schematics.ponder.schematic_printing.header", "Printing with pattern schematics",
            "create_pattern_schematics.contraption_application.applied_to", "Applied pattern schematic to ",
            "create_pattern_schematics.contraption_application.deployers", " deployer(s)",
            "create_pattern_schematics.contraption_application.not_positioned", "Couldn't apply schematic: Schematic not positioned!",
            "item.create_pattern_schematics.empty_pattern_schematic.tooltip.summary"
            , "Can be written to make an _extended version of Create's schematic_ which allows contraptions and schematicannons to _repeat a design_ multiple times.",
            "item.create_pattern_schematics.pattern_schematic.tooltip.summary"
            , "An _extended version of Create's schematic_ which allows contraptions and schematicannons to _repeat a design_ multiple times."
        );
    }
    
    public static void putLang(String... entryKeyPairs) {
        for (int i = 0; i < entryKeyPairs.length; i += 2) {
            REGISTRATE.addRawLang(entryKeyPairs[i], entryKeyPairs[i + 1]);
        }
    }
    
}
