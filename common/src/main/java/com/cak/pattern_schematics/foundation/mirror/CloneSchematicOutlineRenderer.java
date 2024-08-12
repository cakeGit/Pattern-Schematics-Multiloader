package com.cak.pattern_schematics.foundation.mirror;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.content.schematics.client.SchematicHandler;
import com.simibubi.create.foundation.outliner.AABBOutline;
import com.simibubi.create.foundation.render.SuperRenderTypeBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class CloneSchematicOutlineRenderer {
  
  public static void renderCloneGridLines(PoseStack ms, PatternSchematicHandler schematicHandler, SuperRenderTypeBuffer buffer) {
    ms.pushPose();
    AABBOutline outline = schematicHandler.getOutline();
    outline.getParams()
        .colored(0xa6a1af)
        .withFaceTexture(AllSpecialTextures.CHECKERED)
        .lineWidth(1/32f);
//    for (Direction.Axis axis : Iterate.axes) {
//      List<Direction.Axis> secondaries =
//          axis == Direction.Axis.X ? List.of(Direction.Axis.Y, Direction.Axis.Z) :
//              axis == Direction.Axis.Y ? List.of(Direction.Axis.Y, Direction.Axis.Z) :
//                  List.of(Direction.Axis.X, Direction.Axis.Y);
    //TODO: Actually do the optimisation, draw only outer lines
    Vec3i min = schematicHandler.cloneScaleMin;
    Vec3i max = schematicHandler.cloneScaleMax;
    AABB bounds = schematicHandler.getBounds();
    Vec3i size = new Vec3i(bounds.getXsize(), bounds.getYsize(), bounds.getZsize());
    
    for (int x = min.getX(); x <= max.getX(); x++) {
      for (int y = min.getY(); y <= max.getY(); y++) {
        for (int z = min.getZ(); z <= max.getZ(); z++) {
          boolean isRenderingMain = (x == 0) && (y == 0) && (z == 0);
          if (isRenderingMain) continue;
          ms.pushPose();
          ms.translate(x * size.getX(), y * size.getY(), z * size.getZ());
          outline.render(ms, buffer, Vec3.ZERO, AnimationTickHolder.getPartialTicks());
          ms.popPose();
        }
      }
    }
    ms.popPose();
  }
  
  public static void renderClone(PoseStack ms, SchematicHandler schematicHandler, SuperRenderTypeBuffer buffer) {
    ms.pushPose();
    AABBOutline outline = schematicHandler.getOutline();
    outline.getParams()
        .colored(0xa6a1af)
        .withFaceTexture(AllSpecialTextures.CHECKERED)
        .lineWidth(0);
    outline.render(ms, buffer, Vec3.ZERO, AnimationTickHolder.getPartialTicks());
    outline.getParams()
        .clearTextures();
    ms.popPose();
  }
  
  public static void applyOutlineModification(SchematicHandler schematicHandler) {
    if (schematicHandler instanceof PatternSchematicHandler) {
      AABBOutline outline = schematicHandler.getOutline();
      PatternSchematicHandler patternSchematicHandler = (PatternSchematicHandler) schematicHandler;
      outline.getParams()
          .colored(0x9352a3).lineWidth(patternSchematicHandler.isRenderingMultiple() ? 3/32f : 1/16f);
    }
  }
  
}
