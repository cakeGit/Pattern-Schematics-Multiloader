package com.cak.pattern_schematics.foundation.mirror;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.mojang.math.Vector4f;
import com.simibubi.create.AllSpecialTextures;
import com.simibubi.create.foundation.render.RenderTypes;
import com.simibubi.create.foundation.render.SuperRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.Vec3;

/**
 * From
 * {@link com.simibubi.create.foundation.outliner.AABBOutline#render(PoseStack, SuperRenderTypeBuffer, Vec3, float)}
 */
public class StaticRenderers {
    
    protected static final Vector4f colorTemp1 = new Vector4f();
    
    protected static void renderBoxFace(PoseStack.Pose pose, SuperRenderTypeBuffer buffer, boolean cull, boolean highlighted, Vector3f minPos, Vector3f maxPos, Direction face, Vector4f color, int lightmap) {
        RenderType renderType = RenderTypes.getOutlineTranslucent(AllSpecialTextures.CHECKERED.getLocation(), cull);
        VertexConsumer consumer = buffer.getLateBuffer(renderType);
        
        float alphaMult = highlighted ? 1 : 0.5f;
        colorTemp1.set(color.x(), color.y(), color.z(), color.w() * alphaMult);
        color = colorTemp1;
        
        renderBoxFace(pose, consumer, minPos, maxPos, face, color, lightmap);
    }
    
    protected static final Vector3f pos0Temp = new Vector3f();
    protected static final Vector3f pos1Temp = new Vector3f();
    protected static final Vector3f pos2Temp = new Vector3f();
    protected static final Vector3f pos3Temp = new Vector3f();
    protected static final Vector3f normalTemp = new Vector3f();
    
    protected static void renderBoxFace(PoseStack.Pose pose, VertexConsumer consumer, Vector3f minPos, Vector3f maxPos, Direction face, Vector4f color, int lightmap) {
        Vector3f pos0 = pos0Temp;
        Vector3f pos1 = pos1Temp;
        Vector3f pos2 = pos2Temp;
        Vector3f pos3 = pos3Temp;
        Vector3f normal = normalTemp;
        
        float minX = minPos.x();
        float minY = minPos.y();
        float minZ = minPos.z();
        float maxX = maxPos.x();
        float maxY = maxPos.y();
        float maxZ = maxPos.z();
        
        float maxU;
        float maxV;
        
        switch (face) {
            case DOWN -> {
                // 0 1 2 3
                pos0.set(minX, minY, maxZ);
                pos1.set(minX, minY, minZ);
                pos2.set(maxX, minY, minZ);
                pos3.set(maxX, minY, maxZ);
                maxU = maxX - minX;
                maxV = maxZ - minZ;
                normal.set(0, -1, 0);
            }
            case UP -> {
                // 4 5 6 7
                pos0.set(minX, maxY, minZ);
                pos1.set(minX, maxY, maxZ);
                pos2.set(maxX, maxY, maxZ);
                pos3.set(maxX, maxY, minZ);
                maxU = maxX - minX;
                maxV = maxZ - minZ;
                normal.set(0, 1, 0);
            }
            case NORTH -> {
                // 7 2 1 4
                pos0.set(maxX, maxY, minZ);
                pos1.set(maxX, minY, minZ);
                pos2.set(minX, minY, minZ);
                pos3.set(minX, maxY, minZ);
                maxU = maxX - minX;
                maxV = maxY - minY;
                normal.set(0, 0, -1);
            }
            case SOUTH -> {
                // 5 0 3 6
                pos0.set(minX, maxY, maxZ);
                pos1.set(minX, minY, maxZ);
                pos2.set(maxX, minY, maxZ);
                pos3.set(maxX, maxY, maxZ);
                maxU = maxX - minX;
                maxV = maxY - minY;
                normal.set(0, 0, 1);
            }
            case WEST -> {
                // 4 1 0 5
                pos0.set(minX, maxY, minZ);
                pos1.set(minX, minY, minZ);
                pos2.set(minX, minY, maxZ);
                pos3.set(minX, maxY, maxZ);
                maxU = maxZ - minZ;
                maxV = maxY - minY;
                normal.set(-1, 0, 0);
            }
            case EAST -> {
                // 6 3 2 7
                pos0.set(maxX, maxY, maxZ);
                pos1.set(maxX, minY, maxZ);
                pos2.set(maxX, minY, minZ);
                pos3.set(maxX, maxY, minZ);
                maxU = maxZ - minZ;
                maxV = maxY - minY;
                normal.set(1, 0, 0);
            }
            default -> {
                maxU = 1;
                maxV = 1;
            }
        }
        
        bufferQuad(pose, consumer, pos0, pos1, pos2, pos3, color, 0, 0, maxU, maxV, lightmap, normal);
    }
    
    protected static final Vector4f posTransformTemp = new Vector4f();
    protected static final Vector3f normalTransformTemp = new Vector3f();
    
    public static void bufferQuad(PoseStack.Pose pose, VertexConsumer consumer, Vector3f pos0, Vector3f pos1, Vector3f pos2,
                                  Vector3f pos3, Vector4f color, float minU, float minV, float maxU, float maxV, int lightmap, Vector3f normal) {
        Matrix4f posMatrix = pose.pose();
        
        posTransformTemp.set(pos0.x(), pos0.y(), pos0.z(), 1);
        posTransformTemp.transform(posMatrix);
        double x0 = posTransformTemp.x();
        double y0 = posTransformTemp.y();
        double z0 = posTransformTemp.z();
        
        posTransformTemp.set(pos1.x(), pos1.y(), pos1.z(), 1);
        posTransformTemp.transform(posMatrix);
        double x1 = posTransformTemp.x();
        double y1 = posTransformTemp.y();
        double z1 = posTransformTemp.z();
        
        posTransformTemp.set(pos2.x(), pos2.y(), pos2.z(), 1);
        posTransformTemp.transform(posMatrix);
        double x2 = posTransformTemp.x();
        double y2 = posTransformTemp.y();
        double z2 = posTransformTemp.z();
        
        posTransformTemp.set(pos3.x(), pos3.y(), pos3.z(), 1);
        posTransformTemp.transform(posMatrix);
        double x3 = posTransformTemp.x();
        double y3 = posTransformTemp.y();
        double z3 = posTransformTemp.z();
        
        float r = color.x();
        float g = color.y();
        float b = color.z();
        float a = color.w();
        
        normalTransformTemp.set(normal.x(), normal.y(), normal.z());
        normalTransformTemp.transform(pose.normal());
        float nx = normalTransformTemp.x();
        float ny = normalTransformTemp.y();
        float nz = normalTransformTemp.z();
        
        consumer.vertex(x0, y0, z0)
            .color(r, g, b, a)
            .uv(minU, minV)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(lightmap)
            .normal(nx, ny, nz)
            .endVertex();
        
        consumer.vertex(x1, y1, z1)
            .color(r, g, b, a)
            .uv(minU, maxV)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(lightmap)
            .normal(nx, ny, nz)
            .endVertex();
        
        consumer.vertex(x2, y2, z2)
            .color(r, g, b, a)
            .uv(maxU, maxV)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(lightmap)
            .normal(nx, ny, nz)
            .endVertex();
        
        consumer.vertex(x3, y3, z3)
            .color(r, g, b, a)
            .uv(maxU, minV)
            .overlayCoords(OverlayTexture.NO_OVERLAY)
            .uv2(lightmap)
            .normal(nx, ny, nz)
            .endVertex();
    }
    
}
