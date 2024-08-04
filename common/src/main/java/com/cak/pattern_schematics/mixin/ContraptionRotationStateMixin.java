package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.foundation.ExpandedContraptionRotationState;
import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.foundation.collision.Matrix3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = AbstractContraptionEntity.ContraptionRotationState.class, remap = false)
public class ContraptionRotationStateMixin implements ExpandedContraptionRotationState {
    
    @Shadow
    float xRotation;
    
    @Shadow
    float yRotation;
    
    @Shadow
    float zRotation;
    
    @Shadow
    float secondYRotation;
    
    @Shadow private Matrix3d matrix;
    
    @Override
    public void pattern_schematics$setXRotation(float f) {
        xRotation = f;
    }
    
    @Override
    public void pattern_schematics$setYRotation(float f) {
        yRotation = f;
    }
    
    @Override
    public void pattern_schematics$setZRotation(float f) {
        zRotation = f;
    }
    
    @Override
    public void pattern_schematics$setSecondYRotation(float f) {
        secondYRotation = f;
    }
    
    @Override
    public Matrix3d getMatrix() {
        return matrix;
    }
    
    @Override
    public float getXRotation() {
        return xRotation;
    }
    
    @Override
    public float getYRotation() {
        return yRotation;
    }
    
    @Override
    public float getZRotation() {
        return zRotation;
    }
    
    @Override
    public void setMatrix(Matrix3d matrix) {
        this.matrix = matrix;
    }
    
}
