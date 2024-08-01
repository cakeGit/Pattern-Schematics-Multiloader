package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.foundation.mirror.SimpleSchematicOutlineRenderer;
import com.simibubi.create.content.schematics.client.tools.DeployTool;
import com.simibubi.create.content.schematics.client.tools.SchematicToolBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = DeployTool.class, remap = false)
public class DeployBaseMixin extends SchematicToolBase {
  
  @Inject(method = "renderTool", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lcom/simibubi/create/foundation/outliner/AABBOutline;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/simibubi/create/foundation/render/SuperRenderTypeBuffer;Lnet/minecraft/world/phys/Vec3;F)V", remap = true))
  public void renderTool(CallbackInfo ci) {
    SimpleSchematicOutlineRenderer.applyOutlineModification(schematicHandler);
  }
  
  @Shadow @Override
  public boolean handleRightClick() {
    return false;
  }
  
  @Shadow @Override
  public boolean handleMouseWheel(double delta) {
    return false;
  }
  
}
