package com.cak.pattern_schematics.registry.forge;

import com.cak.pattern_schematics.PatternSchematics;
import com.cak.pattern_schematics.packet.forge.PatternSchematicSyncPacket;
import net.createmod.catnip.net.base.BasePacketPayload;
import net.createmod.catnip.net.base.CatnipPacketRegistry;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

import java.util.Locale;

public enum PatternSchematicPackets implements BasePacketPayload.PacketTypeProvider {
    SYNC_PATTERN_SCHEMATIC(PatternSchematicSyncPacket.class, PatternSchematicSyncPacket.STREAM_CODEC);

    private final CatnipPacketRegistry.PacketType<?> type;

    <T extends BasePacketPayload> PatternSchematicPackets(Class<T> clazz, StreamCodec<? super RegistryFriendlyByteBuf, T> codec) {
        String name = this.name().toLowerCase(Locale.ROOT);
        this.type = new CatnipPacketRegistry.PacketType<>(
            new CustomPacketPayload.Type<>(PatternSchematics.asResource(name)),
            clazz, codec
        );
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends CustomPacketPayload> CustomPacketPayload.Type<T> getType() {
        return (CustomPacketPayload.Type<T>) this.type.type();
    }

    public static void register() {
        CatnipPacketRegistry packetRegistry = new CatnipPacketRegistry(PatternSchematics.MOD_ID, 1);
        for (PatternSchematicPackets packet : PatternSchematicPackets.values()) {
            packetRegistry.registerPacket(packet.type);
        }
        packetRegistry.registerAllPackets();
    }

}
