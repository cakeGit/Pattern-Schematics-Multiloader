package com.cak.pattern_schematics.foundation.mirror;

import com.cak.pattern_schematics.foundation.util.Vec3iUtils;
import com.simibubi.create.content.schematics.SchematicChunkSource;
import com.simibubi.create.content.schematics.SchematicItem;
import com.simibubi.create.content.schematics.SchematicWorld;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Function;

public class PatternSchematicWorld extends SchematicWorld {
  
  public Vec3i cloneScaleMin;
  public Vec3i cloneScaleMax;
  public Vec3i cloneOffset;
  public BoundingBox sourceBounds;
  
  public PatternSchematicWorld(BlockPos anchor, Level original) {
    super(anchor, original);
    setChunkSource(new SchematicChunkSource(this));
    this.blocks = new HashMap<>();
    this.blockEntities = new HashMap<>();
    this.bounds = new BoundingBox(BlockPos.ZERO);
    this.anchor = anchor;
    this.entities = new ArrayList<>();
    this.renderedBlockEntities = new ArrayList<>();
  }
  
  public void putExtraData(ItemStack blueprint, StructureTemplate template) {
    CompoundTag tag = blueprint.getTag();
    assert tag != null;
    cloneScaleMin = Vec3iUtils.getVec3i("CloneScaleMin", tag);
    cloneScaleMax = Vec3iUtils.getVec3i("CloneScaleMax", tag);
    cloneOffset = Vec3iUtils.getVec3i("CloneOffset", tag);
  
    sourceBounds = template.getBoundingBox(SchematicItem.getSettings(blueprint), anchor);
  }
  
  @Override
  public boolean addFreshEntity(Entity entityIn) {
    return applyToClones(clonePos -> {
      Entity newEntity = cloneEntity(entityIn);
      newEntity.setPos(applyCloneToRealLoc(newEntity.position(), clonePos));
      return super.addFreshEntity(newEntity);
    });
  }
  
  protected Entity cloneEntity(Entity source) {
    CompoundTag tag = new CompoundTag();
    source.save(tag);
    Entity newEntity = EntityType.create(tag, world).orElseThrow();
    newEntity.setUUID(UUID.randomUUID());
    return newEntity;
  }
  
  protected boolean applyToClones(Function<Vec3i, Boolean> function) {
    AtomicBoolean result = new AtomicBoolean(false);
    forEachClone(clonePos -> {
      if (function.apply(clonePos))
        result.set(true);
    });
    return result.get();
  }
  
  protected  void forEachClone(Consumer<Vec3i> consumer) {
    for (int x = cloneScaleMin.getX(); x <= cloneScaleMax.getX(); x++) {
      for (int y = cloneScaleMin.getY(); y <= cloneScaleMax.getY(); y++) {
        for (int z = cloneScaleMin.getZ(); z <= cloneScaleMax.getZ(); z++) {
          consumer.accept(new Vec3i(x, y, z));
        }
      }
    }
  }
  
  public BoundingBox genBounds(BoundingBox originalBounds, StructurePlaceSettings placeSettings) {
//    // HSould probably be absed d off fht  from the stucrtut e place settingf sbut  i c  bbb aaaaaaaaaaaaaaaaaaaaaaaaaa
//    System.out.println("bounds1" + originalBounds);
//    //originalBounds = Vec3iUtils.rotate(originalBounds, placeSettings.getRotation());
//    Vec3i lengths = originalBounds.getLength();
//    System.out.println("bounds2" + originalBounds);
//    System.out.println("rot" + placeSettings.getRotation());
//
//    // the issue is is that th lengths is fucked ig , cause it sitn get rotate i try fix but code bad and no work ig o think
//
//    Vec3i minScale = cloneScaleMin;
//    Vec3i maxScale = cloneScaleMax;
//
//    Vec3i scale1 = new BlockPos(minScale).rotate(placeSettings.getRotation());
//    Vec3i scale2 = new BlockPos(maxScale).rotate(placeSettings.getRotation());
//    minScale = Vec3iUtils.min(scale1, scale2);
//    maxScale = Vec3iUtils.max(scale1, scale2);
//    System.out.println("lens2" + lengths);
//    lengths = Vec3iUtils.abs(new BlockPos(lengths).rotate(placeSettings.getRotation()));
//    System.out.println("lens" +  lengths);
//
//
//    BoundingBox returnedBounds = new BoundingBox(originalBounds.minX(), originalBounds.minY(), originalBounds.minZ(), originalBounds.maxX(), originalBounds.maxY(), originalBounds.maxZ());
//
//    for (int x = minScale.getX(); x <= maxScale.getX(); x++) {
//      for (int y = minScale.getY(); y <= maxScale.getY(); y++) {
//        for (int z = minScale.getZ(); z <= maxScale.getZ(); z++) {
//          Vec3i offset = Vec3iUtils.multiplyVec3i(lengths, new Vec3i(x, y, z));
//          System.out.println(offset);
//          returnedBounds.encapsulate(originalBounds.moved(offset.getX(), offset.getY(), offset.getZ()));
//        }
//      }
//    }
//    System.out.println(returnedBounds);
//    return returnedBounds;
    return originalBounds;
  }
  
  /**This should only really be used for working at creation, use {@link ContraptionSchematicTransform} for active contraptions if thats needed*/
  protected Vec3 applyCloneToRealLoc(Vec3 local, Vec3i clone) {
    return local.add(Vec3.atLowerCornerOf(Vec3iUtils.multiplyVec3i(clone, sourceBounds.getLength().offset(1, 1, 1))));
  }
  
}
