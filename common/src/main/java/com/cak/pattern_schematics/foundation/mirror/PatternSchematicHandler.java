package com.cak.pattern_schematics.foundation.mirror;

import com.cak.pattern_schematics.foundation.util.Vec3iUtils;
import com.cak.pattern_schematics.registry.PatternSchematicsItems;
import com.cak.pattern_schematics.registry.PlatformPackets;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllKeys;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.StructureTransform;
import com.simibubi.create.content.schematics.SchematicItem;
import com.simibubi.create.content.schematics.SchematicWorld;
import com.simibubi.create.content.schematics.client.SchematicHandler;
import com.simibubi.create.content.schematics.client.SchematicHotbarSlotOverlay;
import com.simibubi.create.content.schematics.client.SchematicRenderer;
import com.simibubi.create.content.schematics.client.SchematicTransformation;
import com.simibubi.create.foundation.outliner.AABBOutline;
import com.simibubi.create.foundation.render.SuperRenderTypeBuffer;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Vector;

/**
 * Gave up with the copious amounts of mixins needed so i just copied the class file :pensive: praise be to mit
 * liscence
 */
public class PatternSchematicHandler extends SchematicHandler {
  
  protected String displayedSchematic;
  protected SchematicTransformation transformation;
  protected AABB bounds;
  protected boolean deployed;
  protected boolean active;
  protected PatternSchematicsToolType currentTool;
  
  protected static final int SYNC_DELAY = 10;
  protected int syncCooldown;
  protected int activeHotbarSlot;
  protected ItemStack activeSchematicItem;
  protected AABBOutline outline;
  
  protected Vector<SchematicRenderer> renderers;
  protected SchematicHotbarSlotOverlay overlay;
  protected PatternSchematicsToolSelectionScreen selectionScreen;
  
  protected Vec3i cloneScaleMin = new Vec3i(0, 0, 0);
  protected Vec3i cloneScaleMax = new Vec3i(0, 0, 0);
  protected Vec3i cloneOffset = new Vec3i(0, 0, 0);
  protected boolean isRenderingMain;
  protected boolean isRenderingMultiple;
  
  public PatternSchematicHandler() {
    renderers = new Vector<>(3);
    for (int i = 0; i < renderers.capacity(); i++)
      renderers.add(new SchematicRenderer());
    
    overlay = new SchematicHotbarSlotOverlay();
    currentTool = PatternSchematicsToolType.DEPLOY;
    selectionScreen = new PatternSchematicsToolSelectionScreen(ImmutableList.of(PatternSchematicsToolType.DEPLOY), this::equip);
    transformation = new SchematicTransformation();
  }
  
  public void tick() {
    Minecraft mc = Minecraft.getInstance();
    if (mc.gameMode.getPlayerMode() == GameType.SPECTATOR) {
      if (active) {
        active = false;
        syncCooldown = 0;
        activeHotbarSlot = 0;
        activeSchematicItem = null;
        renderers.forEach(r -> r.setActive(false));
      }
      return;
    }
    
    if (activeSchematicItem != null && transformation != null)
      transformation.tick();
    
    LocalPlayer player = mc.player;
    ItemStack stack = findBlueprintInHand(player);
    if (stack == null) {
      active = false;
      syncCooldown = 0;
      if (activeSchematicItem != null && itemLost(player)) {
        activeHotbarSlot = 0;
        activeSchematicItem = null;
        renderers.forEach(r -> r.setActive(false));
      }
      return;
    }
    
    if (!active || !stack.getTag()
        .getString("File")
        .equals(displayedSchematic))
      init(player, stack);
    if (!active)
      return;
    
    renderers.forEach(SchematicRenderer::tick);
    if (syncCooldown > 0)
      syncCooldown--;
    if (syncCooldown == 1)
      sync();
    
    selectionScreen.update();
    currentTool.getTool()
        .updateSelection();
  }
  
  private void init(LocalPlayer player, ItemStack stack) {
    loadSettings(stack);
    displayedSchematic = stack.getTag()
        .getString("File");
    active = true;
    if (deployed) {
      setupRenderer();
      PatternSchematicsToolType toolBefore = currentTool;
      selectionScreen = new PatternSchematicsToolSelectionScreen(PatternSchematicsToolType.getTools(player.isCreative()), this::equip);
      if (toolBefore != null) {
        selectionScreen.setSelectedElement(toolBefore);
        equip(toolBefore);
      }
    } else
      selectionScreen = new PatternSchematicsToolSelectionScreen(ImmutableList.of(PatternSchematicsToolType.DEPLOY), this::equip);
  }
  
  private void setupRenderer() {
    Level clientWorld = Minecraft.getInstance().level;
    StructureTemplate schematic =
        SchematicItem.loadSchematic(clientWorld.holderLookup(Registries.BLOCK), activeSchematicItem);
    Vec3i size = schematic.getSize();
    if (size.equals(Vec3i.ZERO))
      return;
    
    SchematicWorld w = new SchematicWorld(clientWorld);
    SchematicWorld wMirroredFB = new SchematicWorld(clientWorld);
    SchematicWorld wMirroredLR = new SchematicWorld(clientWorld);
    StructurePlaceSettings placementSettings = new StructurePlaceSettings();
    StructureTransform transform;
    BlockPos pos;
    
    pos = BlockPos.ZERO;
    
    try {
      schematic.placeInWorld(w, pos, pos, placementSettings, w.getRandom(), Block.UPDATE_CLIENTS);
    } catch (Exception e) {
      Minecraft.getInstance().player.displayClientMessage(Lang.translate("schematic.error")
          .component(), false);
      Create.LOGGER.error("Failed to load Schematic for Previewing", e);
      return;
    }
    
    placementSettings.setMirror(Mirror.FRONT_BACK);
    pos = BlockPos.ZERO.east(size.getX() - 1);
    schematic.placeInWorld(wMirroredFB, pos, pos, placementSettings, wMirroredFB.getRandom(), Block.UPDATE_CLIENTS);
    transform = new StructureTransform(placementSettings.getRotationPivot(), Direction.Axis.Y, Rotation.NONE,
        placementSettings.getMirror());
    for (BlockEntity be : wMirroredFB.getRenderedBlockEntities())
      transform.apply(be);
    
    placementSettings.setMirror(Mirror.LEFT_RIGHT);
    pos = BlockPos.ZERO.south(size.getZ() - 1);
    schematic.placeInWorld(wMirroredLR, pos, pos, placementSettings, wMirroredFB.getRandom(), Block.UPDATE_CLIENTS);
    transform = new StructureTransform(placementSettings.getRotationPivot(), Direction.Axis.Y, Rotation.NONE,
        placementSettings.getMirror());
    for (BlockEntity be : wMirroredLR.getRenderedBlockEntities())
      transform.apply(be);
    
    renderers.get(0)
        .display(w);
    renderers.get(1)
        .display(wMirroredFB);
    renderers.get(2)
        .display(wMirroredLR);
  }
  
  public void render(PoseStack ms, SuperRenderTypeBuffer buffer, Vec3 camera) {
    boolean present = activeSchematicItem != null;
    if (!active && !present)
      return;
  
    if (active) {
      isRenderingMultiple = false;
      isRenderingMain = true;
      ms.pushPose();
      currentTool.getTool()
          .renderTool(ms, buffer, camera);
      ms.popPose();
    }
    
    Vec3 schematicSize = new Vec3(bounds.getXsize(), bounds.getYsize(), bounds.getZsize());
    
    isRenderingMultiple = !cloneScaleMax.equals(cloneScaleMin);
    
    for (int x = cloneScaleMin.getX(); x <= cloneScaleMax.getX(); x++) {
      for (int y = cloneScaleMin.getY(); y <= cloneScaleMax.getY(); y++) {
        for (int z = cloneScaleMin.getZ(); z <= cloneScaleMax.getZ(); z++) {
          isRenderingMain = (x == 0) && (y == 0) && (z == 0);
          renderSchematic(transformation, new Vec3(x, y, z).multiply(schematicSize), ms, camera, buffer);
        }
      }
    }
    
  }
  
  protected void renderSchematic(SchematicTransformation transformation, Vec3 cloneOffset, PoseStack ms, Vec3 camera, SuperRenderTypeBuffer buffer) {
    ms.pushPose();
    
    transformation.applyTransformations(ms, camera);
    ms.translate(cloneOffset.x(), cloneOffset.y(), cloneOffset.z());
    
    if (!renderers.isEmpty()) {
      float pt = AnimationTickHolder.getPartialTicks();
      boolean lr = transformation.getScaleLR()
          .getValue(pt) < 0;
      boolean fb = transformation.getScaleFB()
          .getValue(pt) < 0;
      if (lr && !fb)
        renderers.get(2)
            .render(ms, buffer);
      else if (fb && !lr)
        renderers.get(1)
            .render(ms, buffer);
      else
        renderers.get(0)
            .render(ms, buffer);
    }
    
    if (active)
      if (isRenderingMain) {
        currentTool.getTool()
            .renderOnSchematic(ms, buffer);
      } else SimpleSchematicOutlineRenderer.render(ms, this, buffer);
    
    ms.popPose();
  }
  
  public void updateRenderers() {
    for (SchematicRenderer renderer : renderers) {
      renderer.update();
    }
  }
  
  public boolean onMouseInput(int button, boolean pressed) {
    if (!active)
      return false;
    if (!pressed || button != 1)
      return false;
    Minecraft mc = Minecraft.getInstance();
    if (mc.player.isShiftKeyDown())
      return false;
    if (mc.hitResult instanceof BlockHitResult) {
      BlockHitResult blockRayTraceResult = (BlockHitResult) mc.hitResult;
      BlockState clickedBlock = mc.level.getBlockState(blockRayTraceResult.getBlockPos());
      if (AllBlocks.SCHEMATICANNON.has(clickedBlock))
        return false;
      if (AllBlocks.DEPLOYER.has(clickedBlock))
        return false;
    }
    return currentTool.getTool()
        .handleRightClick();
  }
  
  public void onKeyInput(int key, boolean pressed) {
    if (!active)
      return;
    if (key != AllKeys.TOOL_MENU.getBoundCode())
      return;
    
    if (pressed && !selectionScreen.focused)
      selectionScreen.focused = true;
    if (!pressed && selectionScreen.focused) {
      selectionScreen.focused = false;
      selectionScreen.onClose();
    }
  }
  
  public boolean mouseScrolled(double delta) {
    if (!active)
      return false;
    
    if (selectionScreen.focused) {
      selectionScreen.cycle((int) delta);
      return true;
    }
    if (AllKeys.ctrlDown())
      return currentTool.getTool()
          .handleMouseWheel(delta);
    return false;
  }
  
  private ItemStack findBlueprintInHand(Player player) {
    ItemStack stack = player.getMainHandItem();
    if (!PatternSchematicsItems.PATTERN_SCHEMATIC.isIn(stack))
      return null;
    if (!stack.hasTag())
      return null;
    
    activeSchematicItem = stack;
    activeHotbarSlot = player.getInventory().selected;
    return stack;
  }
  
  private boolean itemLost(Player player) {
    for (int i = 0; i < Inventory.getSelectionSize(); i++) {
      if (!ItemStack.matches(player.getInventory()
          .getItem(i), activeSchematicItem))
        continue;
      return false;
    }
    return true;
  }
  
  public void markDirty() {
    syncCooldown = SYNC_DELAY;
  }
  
  public void sync() {
    if (activeSchematicItem == null)
      return;
    PlatformPackets.sendPatternSchematicSyncPacket(activeHotbarSlot, transformation.toSettings(),
        transformation.getAnchor(), deployed, cloneScaleMin, cloneScaleMax, cloneOffset);
  }
  
  public void equip(PatternSchematicsToolType tool) {
    this.currentTool = tool;
    currentTool.getTool()
        .init();
  }
  
  public void loadSettings(ItemStack blueprint) {
    CompoundTag tag = blueprint.getTag();
    assert tag != null;
    
    BlockPos anchor = BlockPos.ZERO;
    StructurePlaceSettings settings = SchematicItem.getSettings(blueprint);
  
    cloneScaleMin = Vec3iUtils.getVec3i("CloneScaleMin", tag);
    cloneScaleMax = Vec3iUtils.getVec3i("CloneScaleMax", tag);
    cloneOffset = Vec3iUtils.getVec3i("CloneOffset", tag);
    
    transformation = new SchematicTransformation();
    deployed = tag.getBoolean("Deployed");
    if (deployed)
      anchor = NbtUtils.readBlockPos(tag.getCompound("Anchor"));
    Vec3i size = NBTHelper.readVec3i(tag.getList("Bounds", Tag.TAG_INT));
    
    bounds = new AABB(0, 0, 0, size.getX(), size.getY(), size.getZ());
    outline = new AABBOutline(bounds);
    outline.getParams()
        .colored(0xa586a5)
        .lineWidth(1 / 16f);
    transformation.init(anchor, settings, bounds);
  }
  
  public void deploy() {
    if (!deployed) {
      List<PatternSchematicsToolType> tools = PatternSchematicsToolType.getTools(Minecraft.getInstance().player.isCreative());
      selectionScreen = new PatternSchematicsToolSelectionScreen(tools, this::equip);
    }
    deployed = true;
    setupRenderer();
  }
  
  public String getCurrentSchematicName() {
    return displayedSchematic != null ? displayedSchematic : "-";
  }
  
  public boolean isActive() {
    return active;
  }
  
  public AABB getBounds() {
    return bounds;
  }
  
  public SchematicTransformation getTransformation() {
    return transformation;
  }
  
  public boolean isDeployed() {
    return deployed;
  }
  
  public ItemStack getActiveSchematicItem() {
    return activeSchematicItem;
  }
  
  public AABBOutline getOutline() {
    return outline;
  }
  
  public void setCloneScaleMin(Vec3i min) {
    cloneScaleMin = Vec3iUtils.min(min, 0);
  }
  
  public void setCloneScaleMax(Vec3i max) {
    cloneScaleMax = Vec3iUtils.max(max, 0);
  }
  
  public Vec3i getCloneScaleMin() {
    return cloneScaleMin;
  }
  
  public Vec3i getCloneScaleMax() {
    return cloneScaleMax;
  }
  
  public void setCloneOffset(Vec3i offset) {
    cloneOffset = offset;
  }
  
  public boolean isRenderingMain() {
    return isRenderingMain;
  }
  
  public boolean isRenderingMultiple() {
    return isRenderingMultiple;
  }
  
}

