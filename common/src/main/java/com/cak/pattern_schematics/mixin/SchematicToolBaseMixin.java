package com.cak.pattern_schematics.mixin;

import com.cak.pattern_schematics.PatternSchematicsClient;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicHandler;
import com.cak.pattern_schematics.foundation.mirror.PatternSchematicsToolType;
import com.cak.pattern_schematics.foundation.mirror.SimpleSchematicOutlineRenderer;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.schematics.client.SchematicHandler;
import com.simibubi.create.content.schematics.client.tools.SchematicToolBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SchematicToolBase.class, remap = false)
public class SchematicToolBaseMixin {
  
  @Shadow
  protected SchematicHandler schematicHandler;
  
  /**Praise be to simi that none of the calls to this are specific enough that a slapped on extends in {@link PatternSchematicHandler} works*/
  @Inject(method = "init", at = @At("TAIL"))
  public void init(CallbackInfo ci) {
    schematicHandler =
        (PatternSchematicsToolType.isPatternSchematicTool((SchematicToolBase) (Object) this) ?
        PatternSchematicsClient.PATTERN_SCHEMATIC_HANDLER : CreateClient.SCHEMATIC_HANDLER);
  }
  
  @Inject(method = "renderOnSchematic", at = @At(value = "INVOKE", shift = At.Shift.BEFORE, target = "Lcom/simibubi/create/foundation/outliner/AABBOutline;render(Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/simibubi/create/foundation/render/SuperRenderTypeBuffer;Lnet/minecraft/world/phys/Vec3;F)V"))
  public void renderOnSchematic(CallbackInfo ci) {
    SimpleSchematicOutlineRenderer.applyOutlineModification(schematicHandler);
  }
  
}
