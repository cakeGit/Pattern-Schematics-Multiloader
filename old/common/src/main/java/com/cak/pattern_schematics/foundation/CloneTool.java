package com.cak.pattern_schematics.foundation;

import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import com.simibubi.create.AllKeys;
import com.simibubi.create.content.schematics.client.tools.SchematicToolBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

public class CloneTool extends SchematicToolBase {
  
  @Override
  public boolean handleRightClick() {
    return false;
  }
  
  @Override
  public boolean handleMouseWheel(double delta) {
    if (!schematicSelected && !AllKeys.shiftDown())
      return true;
    
    if (!(schematicHandler instanceof PatternSchematicHandler patternSchematicHandler))
      throw new RuntimeException("Clone tool bound in a normal SchematicHandler!");
    
    LocalPlayer player = Minecraft.getInstance().player;
    Direction face = (!AllKeys.shiftDown() && player != null) ? facingOfPlayer(player).getOpposite() : selectedFace;
    
    if (face == null)
      return true;
    
    boolean isPositive = face.getAxisDirection() == Direction.AxisDirection.POSITIVE;
    
    Vec3i cloneScale;
    if (isPositive)
      cloneScale = patternSchematicHandler.getCloneScaleMax();
    else
      cloneScale = patternSchematicHandler.getCloneScaleMin();
  
    cloneScale = cloneScale.relative(face, Mth.sign(delta));
    
    if (isPositive)
      patternSchematicHandler.setCloneScaleMax(cloneScale);
    else
      patternSchematicHandler.setCloneScaleMin(cloneScale);
  
    schematicHandler.markDirty();
    return true;
  }
  
  private Direction facingOfPlayer(LocalPlayer player) {
    Vec3 lookAngle = player.getLookAngle();
    return Direction.getNearest(lookAngle.x, lookAngle.y, lookAngle.z);
  }
  
}
