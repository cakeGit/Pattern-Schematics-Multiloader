package com.cak.pattern_schematics.foundation.mirror;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.content.schematics.client.SchematicHandler;
import com.simibubi.create.foundation.outliner.AABBOutline;
import com.simibubi.create.foundation.render.RenderTypes;
import com.simibubi.create.foundation.render.SuperRenderTypeBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Color;
import com.simibubi.create.foundation.utility.Iterate;
import net.fabricmc.loader.impl.lib.sat4j.core.Vec;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.List;
import java.util.Optional;

public class CloneSchematicOutlineRenderer {
    
    public static void renderCloneGridLines(PoseStack ms, PatternSchematicHandler schematicHandler, SuperRenderTypeBuffer buffer) {
        if (
            schematicHandler.cloneScaleMin.equals(new Vec3i(0, 0, 0))
                && schematicHandler.cloneScaleMax.equals(new Vec3i(0, 0, 0))
        ) return;
        
        ms.pushPose();
        AABBOutline outline = schematicHandler.getGreaterOutline();
        outline.setBounds(schematicHandler.calculateGreaterOutlineBounds());
        outline.getParams()
            .colored(0xa6a1af)
            .lineWidth(1 / 16f);
        outline.render(ms, buffer, Vec3.ZERO, AnimationTickHolder.getPartialTicks());
        
        Vec3i min = schematicHandler.cloneScaleMin;
        Vec3i max = schematicHandler.cloneScaleMax;
        
        AABB bounds = schematicHandler.bounds;
        Vec3 scale = new Vec3(
            bounds.getXsize(),
            bounds.getYsize(),
            bounds.getZsize()
        );
        
        Vector4f color = new Vector4f(166 / 256f, 161 / 256f, 175 / 256f, 1);
        
        for (Direction.Axis axis : Iterate.axes) {
            List<Direction.Axis> secondaries =
                axis == Direction.Axis.X ? List.of(Direction.Axis.Y, Direction.Axis.Z) :
                    axis == Direction.Axis.Y ? List.of(Direction.Axis.Z, Direction.Axis.X) :
                        List.of(Direction.Axis.X, Direction.Axis.Y);
            
            Direction.Axis secondaryA = secondaries.get(0);
            Direction.Axis secondaryB = secondaries.get(1);
            
            double secondaryScaleA = scale.get(secondaryA);
            double secondaryScaleB = scale.get(secondaryB);
            
            Direction secondaryDirectionA = Direction.fromAxisAndDirection(secondaryA, Direction.AxisDirection.POSITIVE);
            Direction secondaryDirectionB = Direction.fromAxisAndDirection(secondaryB, Direction.AxisDirection.POSITIVE);
            
            
            for (Direction.AxisDirection axisDirection : Direction.AxisDirection.values()) {
                Direction currentDirection = Direction.fromAxisAndDirection(axis, axisDirection);
                Vec3 surfaceOrigin = axisDirection == Direction.AxisDirection.POSITIVE ?
                    new Vec3(0, 0, 0).with(axis, (max.get(axis) + 1) * scale.get(axis)) :
                    new Vec3(0, 0, 0).with(axis, min.get(axis) * scale.get(axis));
                
                for (int secondaryCellA = min.get(secondaryA); secondaryCellA <= max.get(secondaryA); secondaryCellA++) {
                    for (int secondaryCellB = min.get(secondaryB); secondaryCellB <= max.get(secondaryB); secondaryCellB++) {
                        if (
                            secondaryCellA == 0 && secondaryCellB == 0 &&
                            ((axisDirection == Direction.AxisDirection.POSITIVE && max.get(axis) == 0)
                                || (axisDirection == Direction.AxisDirection.NEGATIVE && max.get(axis) == 0))
                        ) continue;
                        
                        Vec3 faceMin = surfaceOrigin
                            .relative(secondaryDirectionA, secondaryCellA * secondaryScaleA)
                            .relative(secondaryDirectionB, secondaryCellB * secondaryScaleB);
                        
                        StaticRenderers.renderBoxFace(
                            ms.last(), buffer, true, false,
                            toVec3f(faceMin), toVec3f(faceMin
                                .relative(secondaryDirectionA, secondaryScaleA)
                                .relative(secondaryDirectionB, secondaryScaleB)),
                            currentDirection, color, LightTexture.FULL_BRIGHT
                        );
                    }
                }
            }
        }
        ms.popPose();
    }
    
    private static Vector3f toVec3f(Vec3 vec) {
        return new Vector3f((float) vec.x, (float) vec.y, (float) vec.z);
    }
    
    public static void renderClone(PoseStack ms, SchematicHandler schematicHandler, SuperRenderTypeBuffer buffer) {
//        ms.pushPose();
//        AABBOutline outline = schematicHandler.getOutline();
//        outline.getParams()
//            .colored(0xa6a1af)
//            .withFaceTexture(AllSpecialTextures.CHECKERED)
//            .lineWidth(0);
//        outline.render(ms, buffer, Vec3.ZERO, AnimationTickHolder.getPartialTicks());
//        outline.getParams()
//            .clearTextures();
//        ms.popPose();
    }
    
    public static void applyOutlineModification(SchematicHandler schematicHandler) {
        if (schematicHandler instanceof PatternSchematicHandler) {
            AABBOutline outline = schematicHandler.getOutline();
            PatternSchematicHandler patternSchematicHandler = (PatternSchematicHandler) schematicHandler;
            outline.getParams()
                .colored(0x9352a3)
                .lineWidth(patternSchematicHandler.isRenderingMultiple() ? 3 / 32f : 1 / 16f);
        }
    }
    
}
