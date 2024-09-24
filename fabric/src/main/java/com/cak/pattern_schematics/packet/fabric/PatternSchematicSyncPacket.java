package com.cak.pattern_schematics.packet.fabric;

import com.cak.pattern_schematics.foundation.util.Vec3iUtils;
import com.cak.pattern_schematics.registry.PatternSchematicsRegistry;
import com.simibubi.create.content.schematics.SchematicInstances;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

public class PatternSchematicSyncPacket extends SimplePacketBase {
    
    public int slot;
    public boolean deployed;
    public BlockPos anchor;
    public Rotation rotation;
    public Mirror mirror;
    public Vec3i cloneScaleMin, cloneScaleMax, cloneOffset;
    
    public PatternSchematicSyncPacket(int slot, StructurePlaceSettings settings,
                                      BlockPos anchor, boolean deployed,
                                      Vec3i cloneScaleMin, Vec3i cloneScaleMax, Vec3i cloneOffset) {
        this.slot = slot;
        this.deployed = deployed;
        this.anchor = anchor;
        this.rotation = settings.getRotation();
        this.mirror = settings.getMirror();
        this.cloneScaleMin = cloneScaleMin;
        this.cloneScaleMax = cloneScaleMax;
        this.cloneOffset = cloneOffset;
    }
    
    public PatternSchematicSyncPacket(FriendlyByteBuf buffer) {
        slot = buffer.readVarInt();
        deployed = buffer.readBoolean();
        anchor = buffer.readBlockPos();
        rotation = buffer.readEnum(Rotation.class);
        mirror = buffer.readEnum(Mirror.class);
        
        this.cloneScaleMin = Vec3iUtils.unpackVec3i(buffer);
        this.cloneScaleMax = Vec3iUtils.unpackVec3i(buffer);
        this.cloneOffset = Vec3iUtils.unpackVec3i(buffer);
    }
    
    @Override
    public void write(FriendlyByteBuf buffer) {
        buffer.writeVarInt(slot);
        buffer.writeBoolean(deployed);
        buffer.writeBlockPos(anchor);
        buffer.writeEnum(rotation);
        buffer.writeEnum(mirror);
        
        Vec3iUtils.packVec3i(cloneScaleMin, buffer);
        Vec3iUtils.packVec3i(cloneScaleMax, buffer);
        Vec3iUtils.packVec3i(cloneOffset, buffer);
    }
    
    @Override
    public boolean handle(Context context) {
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player == null)
                return;
            ItemStack stack = ItemStack.EMPTY;
            if (slot == -1) {
                stack = player.getMainHandItem();
            } else {
                stack = player.getInventory().getItem(slot);
            }
            if (!PatternSchematicsRegistry.PATTERN_SCHEMATIC.isIn(stack)) {
                return;
            }
            CompoundTag tag = stack.getOrCreateTag();
            tag.putBoolean("Deployed", deployed);
            tag.put("Anchor", NbtUtils.writeBlockPos(anchor));
            tag.putString("Rotation", rotation.name());
            tag.putString("Mirror", mirror.name());
            
            Vec3iUtils.putVec3i("CloneScaleMin", cloneScaleMin, tag);
            Vec3iUtils.putVec3i("CloneScaleMax", cloneScaleMax, tag);
            Vec3iUtils.putVec3i("CloneOffset", cloneOffset, tag);
            
            SchematicInstances.clearHash(stack);
        });
        return true;
    }
    
}
