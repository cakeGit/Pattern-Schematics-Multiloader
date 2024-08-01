package com.cak.pattern_schematics.foundation.mirror;


import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.foundation.CloneTool;
import com.cak.pattern_schematics.foundation.SingleIcon;
import com.simibubi.create.content.schematics.client.tools.*;
import com.simibubi.create.foundation.gui.AllIcons;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public enum PatternSchematicsToolType {
  
  DEPLOY(new DeployTool(), AllIcons.I_TOOL_DEPLOY),
  MOVE(new MoveTool(), AllIcons.I_TOOL_MOVE_XZ),
  MOVE_Y(new MoveVerticalTool(), AllIcons.I_TOOL_MOVE_Y),
  ROTATE(new RotateTool(), AllIcons.I_TOOL_ROTATE),
  FLIP(new FlipTool(), AllIcons.I_TOOL_MIRROR),
  PRINT(new PlaceTool(), AllIcons.I_CONFIRM),
  CLONE(new CloneTool(), new SingleIcon(0, 0, PatternSchematics.asResource("textures/gui/clone_tool.png")), true);
  
  final ISchematicTool tool;
  final AllIcons icon;
  final boolean patternModId;
  
  PatternSchematicsToolType(ISchematicTool tool, AllIcons icon) {
    this(tool, icon, false);
  }
  
  PatternSchematicsToolType(ISchematicTool tool, AllIcons icon, boolean patternModId) {
    this.tool = tool;
    this.icon = icon;
    this.patternModId = patternModId;
  }
  
  public ISchematicTool getTool() {
    return tool;
  }
  
  public MutableComponent getDisplayName() {
    return (patternModId ? Component.translatable(PatternSchematics.MODID + ".schematic.tool." + Lang.asId(name()))
    : Lang.translateDirect("schematic.tool." + Lang.asId(name())));
  }
  
  public AllIcons getIcon() {
    return icon;
  }
  
  public static List<PatternSchematicsToolType> getTools(boolean creative) {
    List<PatternSchematicsToolType> tools = new ArrayList<>();
    Collections.addAll(tools, MOVE, MOVE_Y, DEPLOY, ROTATE, FLIP, CLONE);
    if (creative)
      tools.add(PRINT);
    return tools;
  }
  
  public static boolean isPatternSchematicTool(SchematicToolBase tool) {
    return Arrays.stream(values()).anyMatch(toolType -> toolType.getTool() == tool);
  }
  
  public List<Component> getDescription() {
    return (patternModId ? PatternSchematicsToolType.translatedOptions(PatternSchematics.MODID + ".schematic.tool." + Lang.asId(name()) + ".description", "0", "1", "2", "3")
        : Lang.translatedOptions("schematic.tool." + Lang.asId(name()) + ".description", "0", "1", "2", "3"));
  }
  
  public static List<Component> translatedOptions(String prefix, String... keys) {
    List<Component> result = new ArrayList<>(keys.length);
    for (String key : keys)
      result.add(Component.translatable((prefix != null ? prefix + "." : "") + key));
    return result;
  }
  
}
