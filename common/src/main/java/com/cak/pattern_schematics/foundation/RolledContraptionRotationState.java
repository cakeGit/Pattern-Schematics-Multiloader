package com.cak.pattern_schematics.foundation;

import com.simibubi.create.content.contraptions.AbstractContraptionEntity;
import com.simibubi.create.foundation.collision.Matrix3d;
import com.simibubi.create.foundation.utility.AngleHelper;

public class RolledContraptionRotationState extends AbstractContraptionEntity.ContraptionRotationState {
    
    float roll;
    AbstractContraptionEntity.ContraptionRotationState source;
    
    public RolledContraptionRotationState(AbstractContraptionEntity.ContraptionRotationState source) {
        this.source = source;
    }
    
    public void setRoll(float roll) {
        this.roll = roll;
    }
    
    @Override
    public boolean hasVerticalRotation() {
        return source.hasVerticalRotation();
    }
    
    @Override
    public float getYawOffset() {
        return source.getYawOffset();
    }
    
    Matrix3d matrix = null;
    
    @Override
    public Matrix3d asMatrix() {
        ExpandedContraptionRotationState crs = (ExpandedContraptionRotationState) source;
        
        if (matrix != null)
            return matrix;
        
        Matrix3d matrix = new Matrix3d().asIdentity();
        if (crs.getXRotation() != 0)
            matrix.multiply(new Matrix3d().asXRotation(AngleHelper.rad(-crs.getXRotation())));
        if (crs.getYRotation() != 0)
            matrix.multiply(new Matrix3d().asYRotation(AngleHelper.rad(-crs.getYRotation())));
        if (roll != 0)
            matrix.multiply(new Matrix3d().asXRotation(AngleHelper.rad(-roll)));
        if (crs.getZRotation() != 0)
            matrix.multiply(new Matrix3d().asZRotation(AngleHelper.rad(-crs.getZRotation())));
        this.matrix = matrix;
        return matrix;
    }
    
}
