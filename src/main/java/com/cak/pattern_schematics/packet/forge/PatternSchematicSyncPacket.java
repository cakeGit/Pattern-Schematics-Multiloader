package com.cak.pattern_schematics.packet.forge;

import com.cak.pattern_schematics.registry.PatternSchematicsDataComponents;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.cak.pattern_schematics.registry.forge.PatternSchematicPackets;
import com.simibubi.create.AllDataComponents;
import com.simibubi.create.content.schematics.SchematicInstances;
import io.netty.buffer.ByteBuf;
import net.createmod.catnip.codecs.stream.CatnipStreamCodecs;
import net.createmod.catnip.net.base.ServerboundPacketPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public record PatternSchematicSyncPacket(
    int slot, boolean deployed, BlockPos anchor, Rotation rotation, Mirror mirror,
    Vec3i cloneScaleMin, Vec3i cloneScaleMax, Vec3i cloneOffset
) implements ServerboundPacketPayload {

    public static final StreamCodec<ByteBuf, PatternSchematicSyncPacket> STREAM_CODEC = new StreamCodec<>() {
        @Override
        public void encode(ByteBuf out, PatternSchematicSyncPacket src) {
            out.writeInt(src.slot);
            out.writeBoolean(src.deployed);
            BlockPos.STREAM_CODEC.encode(out, src.anchor);
            CatnipStreamCodecs.ROTATION.encode(out, src.rotation);
            CatnipStreamCodecs.MIRROR.encode(out, src.mirror);
            CatnipStreamCodecs.VEC3I.encode(out, src.cloneScaleMin);
            CatnipStreamCodecs.VEC3I.encode(out, src.cloneScaleMax);
            CatnipStreamCodecs.VEC3I.encode(out, src.cloneOffset);
        }

        @Override
        public PatternSchematicSyncPacket decode(ByteBuf buf) {
            return new PatternSchematicSyncPacket(
                buf.readInt(),
                buf.readBoolean(),
                BlockPos.STREAM_CODEC.decode(buf),
                CatnipStreamCodecs.ROTATION.decode(buf),
                CatnipStreamCodecs.MIRROR.decode(buf),
                CatnipStreamCodecs.VEC3I.decode(buf),
                CatnipStreamCodecs.VEC3I.decode(buf),
                CatnipStreamCodecs.VEC3I.decode(buf)
            );
        }
    };

    public PatternSchematicSyncPacket(
        int slot, StructurePlaceSettings settings, BlockPos anchor, boolean deployed,
        Vec3i cloneScaleMin, Vec3i cloneScaleMax, Vec3i cloneOffset) {
        this(slot, deployed, anchor, settings.getRotation(), settings.getMirror(), cloneScaleMin, cloneScaleMax, cloneOffset);
    }

    @Override
    public PacketTypeProvider getTypeProvider() {
        return PatternSchematicPackets.SYNC_PATTERN_SCHEMATIC;
    }

    @Override
    public void handle(ServerPlayer player) {
        ItemStack stack;
        if (slot == -1) {
            stack = player.getMainHandItem();
        } else {
            stack = player.getInventory().getItem(slot);
        }
        if (!PatternSchematicsRegistry.PATTERN_SCHEMATIC.isIn(stack)) {
            return;
        }
        stack.set(AllDataComponents.SCHEMATIC_DEPLOYED, deployed);
        stack.set(AllDataComponents.SCHEMATIC_ANCHOR, anchor);
        stack.set(AllDataComponents.SCHEMATIC_ROTATION, rotation);
        stack.set(AllDataComponents.SCHEMATIC_MIRROR, mirror);
        stack.set(PatternSchematicsDataComponents.SCHEMATIC_CLONE_OFFSET, cloneOffset);
        stack.set(PatternSchematicsDataComponents.SCHEMATIC_CLONE_SCALE_MIN, cloneScaleMin);
        stack.set(PatternSchematicsDataComponents.SCHEMATIC_CLONE_SCALE_MAX, cloneScaleMax);
        SchematicInstances.clearHash(stack);
    }
//  public int slot;
//  public boolean deployed;
//  public BlockPos anchor;
//  public Rotation rotation;
//  public Mirror mirror;
//  public Vec3i cloneScaleMin, cloneScaleMax, cloneOffset;
//
//  public PatternSchematicSyncPacket(int slot, StructurePlaceSettings settings,
//                                    BlockPos anchor, boolean deployed,
//                                    Vec3i cloneScaleMin, Vec3i cloneScaleMax, Vec3i cloneOffset) {
//    this.slot = slot;
//    this.deployed = deployed;
//    this.anchor = anchor;
//    this.rotation = settings.getRotation();
//    this.mirror = settings.getMirror();
//    this.cloneScaleMin = cloneScaleMin;
//    this.cloneScaleMax = cloneScaleMax;
//    this.cloneOffset = cloneOffset;
//  }
//
//  public PatternSchematicSyncPacket(FriendlyByteBuf buffer) {
//    slot = buffer.readVarInt();
//    deployed = buffer.readBoolean();
//    anchor = buffer.readBlockPos();
//    rotation = buffer.readEnum(Rotation.class);
//    mirror = buffer.readEnum(Mirror.class);
//
//    this.cloneScaleMin = Vec3iUtils.unpackVec3i(buffer);
//    this.cloneScaleMax = Vec3iUtils.unpackVec3i(buffer);
//    this.cloneOffset = Vec3iUtils.unpackVec3i(buffer);
//  }
//
//  @Override
//  public void write(FriendlyByteBuf buffer) {
//    buffer.writeVarInt(slot);
//    buffer.writeBoolean(deployed);
//    buffer.writeBlockPos(anchor);
//    buffer.writeEnum(rotation);
//    buffer.writeEnum(mirror);
//
//    Vec3iUtils.packVec3i(cloneScaleMin, buffer);
//    Vec3iUtils.packVec3i(cloneScaleMax, buffer);
//    Vec3iUtils.packVec3i(cloneOffset, buffer);
//  }
//
//  @Override
//  public boolean handle(NetworkEvent.Context context) {
//    context.enqueueWork(() -> {
//      ServerPlayer player = context.getSender();
//      if (player == null)
//        return;
//      ItemStack stack = ItemStack.EMPTY;
//      if (slot == -1) {
//        stack = player.getMainHandItem();
//      } else {
//        stack = player.getInventory().getItem(slot);
//      }
//      if (!PatternSchematicsRegistry.PATTERN_SCHEMATIC.isIn(stack)) {
//        return;
//      }
//      CompoundTag tag = stack.getOrCreateTag();
//      tag.putBoolean("Deployed", deployed);
//      tag.put("Anchor", NbtUtils.writeBlockPos(anchor));
//      tag.putString("Rotation", rotation.name());
//      tag.putString("Mirror", mirror.name());
//
//      Vec3iUtils.putVec3i("CloneScaleMin", cloneScaleMin, tag);
//      Vec3iUtils.putVec3i("CloneScaleMax", cloneScaleMax, tag);
//      Vec3iUtils.putVec3i("CloneOffset", cloneOffset, tag);
//
//      SchematicInstances.clearHash(stack);
//    });
//    return true;
//  }

}
