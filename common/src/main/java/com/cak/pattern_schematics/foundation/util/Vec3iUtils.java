package com.cak.pattern_schematics.foundation.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;

public class Vec3iUtils {
  
  public static void packVec3i(Vec3i vec3i, FriendlyByteBuf buf) {
    buf.writeVarInt(vec3i.getX());
    buf.writeVarInt(vec3i.getY());
    buf.writeVarInt(vec3i.getZ());
  }
  
  public static Vec3i unpackVec3i(FriendlyByteBuf buf) {
    return new Vec3i(buf.readVarInt(), buf.readVarInt(), buf.readVarInt());
  }
  
  public static void putVec3i(String name, Vec3i vec3i, CompoundTag tag) {
    tag.putInt(name + "_x", vec3i.getX());
    tag.putInt(name + "_y", vec3i.getY());
    tag.putInt(name + "_z", vec3i.getZ());
  }
  
  public static Vec3i getVec3i(String name, CompoundTag tag) {
    return new Vec3i(
        tag.getInt(name + "_x"),
        tag.getInt(name + "_y"),
        tag.getInt(name + "_z")
    );
  }
  
  public static Vec3i multiplyVec3i(Vec3i vec1, Vec3i vec2) {
    return new Vec3i(
        vec1.getX() * vec2.getX(),
        vec1.getY() * vec2.getY(),
        vec1.getZ() * vec2.getZ()
    );
  }
  
  public static Vec3i min(Vec3i vec, int i) {
    return new Vec3i(
        Math.min(vec.getX(), i),
        Math.min(vec.getY(), i),
        Math.min(vec.getZ(), i)
    );
  }
  
  public static Vec3i min(Vec3i vec1, Vec3i vec2) {
    return new Vec3i(
        Math.min(vec1.getX(), vec2.getX()),
        Math.min(vec1.getY(), vec2.getY()),
        Math.min(vec1.getZ(), vec2.getZ())
    );
  }
  
  public static Vec3i max(Vec3i vec, int i) {
    return new Vec3i(
        Math.max(vec.getX(), i),
        Math.max(vec.getY(), i),
        Math.max(vec.getZ(), i)
    );
  }
  
  public static Vec3i max(Vec3i vec1, Vec3i vec2) {
    return new Vec3i(
        Math.max(vec1.getX(), vec2.getX()),
        Math.max(vec1.getY(), vec2.getY()),
        Math.max(vec1.getZ(), vec2.getZ())
    );
  }
  
  public static Vec3i abs(Vec3i vec) {
    return new Vec3i(
        Math.abs(vec.getX()),
        Math.abs(vec.getY()),
        Math.abs(vec.getZ())
    );
  }
  
  public static BoundingBox rotate(BoundingBox originalBounds, Rotation rotation) {
    return BoundingBox.fromCorners(
        rotate(new Vec3i(originalBounds.minX(), originalBounds.minY(), originalBounds.minZ()), rotation),
        rotate(new Vec3i(originalBounds.maxX(), originalBounds.maxY(), originalBounds.maxZ()), rotation)
    );
  }
  private static Vec3i rotate(Vec3i vec3i, Rotation rotation) {
    return new BlockPos(vec3i).rotate(rotation);
  }
  private static BoundingBox boundingBoxFromVecs(Vec3i vector1, Vec3i vector2) {
    return new BoundingBox(vector1.getX(), vector1.getY(), vector1.getZ(), vector2.getX(), vector2.getY(), vector2.getZ());
  }
  
}
