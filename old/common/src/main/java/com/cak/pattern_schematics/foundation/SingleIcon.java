package com.cak.pattern_schematics.foundation;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.simibubi.create.foundation.gui.AllIcons;
import net.createmod.catnip.gui.element.DelegatedStencilElement;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix4f;

public class SingleIcon extends AllIcons {
    
    public static final int SOURCE_SIZE = 16;
    
    public SingleIcon(int x, int y, ResourceLocation source) {
        super(x, y);
        iconX = x * 16;
        iconY = y * 16;
        this.source = source;
    }
    
    public ResourceLocation source;
    private int iconX;
    private int iconY;
    
    public void bind() {
        RenderSystem.setShaderTexture(0, source);
    }
    
    @Override
    public void render(GuiGraphics graphics, int x, int y) {
        graphics.blit(source, x, y, 0, iconX, iconY, 16, 16, SOURCE_SIZE, SOURCE_SIZE);
    }
    
    public void render(PoseStack ms, MultiBufferSource buffer, int color) {
        VertexConsumer builder = buffer.getBuffer(RenderType.text(source));
        Matrix4f matrix = ms.last().pose();
        Color rgb = new Color(color);
        int light = LightTexture.FULL_BRIGHT;
        
        Vec3 vec1 = new Vec3(0, 0, 0);
        Vec3 vec2 = new Vec3(0, 1, 0);
        Vec3 vec3 = new Vec3(1, 1, 0);
        Vec3 vec4 = new Vec3(1, 0, 0);
        
        float u1 = iconX * 1f / SOURCE_SIZE;
        float u2 = (iconX + 16) * 1f / SOURCE_SIZE;
        float v1 = iconY * 1f / SOURCE_SIZE;
        float v2 = (iconY + 16) * 1f / SOURCE_SIZE;
        
        vertex(builder, matrix, vec1, rgb, u1, v1, light);
        vertex(builder, matrix, vec2, rgb, u1, v2, light);
        vertex(builder, matrix, vec3, rgb, u2, v2, light);
        vertex(builder, matrix, vec4, rgb, u2, v1, light);
    }
    
    private void vertex(VertexConsumer builder, Matrix4f matrix, Vec3 vec, Color rgb, float u, float v, int light) {
        builder.vertex(matrix, (float) vec.x, (float) vec.y, (float) vec.z)
            .color(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), 255)
            .uv(u, v)
            .uv2(light)
            .endVertex();
    }
    
    public DelegatedStencilElement asStencil() {
        return new DelegatedStencilElement().withStencilRenderer((ms, w, h, alpha) -> this.render(ms, 0, 0)).withBounds(16, 16);
    }
    
}
