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
            "create_pattern_schematics.contraption_application.applied_to", "Applied pattern schematic to ",
            "create_pattern_schematics.contraption_application.deployers", " deployer(s)",
            "create_pattern_schematics.contraption_application.not_positioned", "Couldn't apply schematic: Schematic not positioned!",

            "item.create_pattern_schematics.empty_pattern_schematic.tooltip.summary",
                "Can be written to make an _extended version of Create's schematic_ which allows contraptions and schematicannons to _repeat a design_ multiple times.",
            "item.create_pattern_schematics.pattern_schematic.tooltip.summary",
                "An _extended version of Create's schematic_ which allows contraptions and schematicannons to _repeat a design_ multiple times.",

            "item.create_pattern_schematics.pattern_schematic_and_quill.tooltip.behaviour1",
                "Select two _corner points_ using _R-Click_. Hold _Ctrl_ and Scroll to select locations mid-air.",
            "item.create_pattern_schematics.pattern_schematic_and_quill.tooltip.behaviour2",
                "_Ctrl-Scroll_ on the faces to adjust the size, then R-Click again to Save.",
            "item.create_pattern_schematics.pattern_schematic_and_quill.tooltip.condition1",
                "Creating a selection",
            "item.create_pattern_schematics.pattern_schematic_and_quill.tooltip.condition2",
                "Adjusting and Saving",
            "item.create_pattern_schematics.pattern_schematic_and_quill.tooltip.summary",
                "Used for _saving a Structure_ in your world to a _.nbt file_ or be printed immediately into a _pattern schematic_.",

            "tag.item.create_pattern_schematics.pattern_schematic_paper_substitutes", "Pattern Schematic Paper Substitutes",
            "tag.item.create_pattern_schematics.schematic_paper_substitutes", "Schematic Paper Substitutes"
        );
    }
    
    public static void putLang(String... entryKeyPairs) {
        for (int i = 0; i < entryKeyPairs.length; i += 2) {
            REGISTRATE.addRawLang(entryKeyPairs[i], entryKeyPairs[i + 1]);
        }
    }
    
}
