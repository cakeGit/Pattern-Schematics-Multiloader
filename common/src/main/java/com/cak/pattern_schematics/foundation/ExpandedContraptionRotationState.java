package com.cak.pattern_schematics.foundation;


import com.simibubi.create.foundation.collision.Matrix3d;

public interface ExpandedContraptionRotationState {
    
    void pattern_schematics$setXRotation(float f);
    void pattern_schematics$setYRotation(float f);
    void pattern_schematics$setZRotation(float f);
    
    void pattern_schematics$setSecondYRotation(float f);
    
    Matrix3d getMatrix();
    
    float getXRotation();
    float getYRotation();
    float getZRotation();
    
    void setMatrix(Matrix3d matrix);
    
}
