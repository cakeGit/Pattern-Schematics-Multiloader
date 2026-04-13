package com.cak.pattern_schematics.foundation;

import com.cak.pattern_schematics.packet.PatternSchematicSyncPacket;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class FabricNetworker implements GenericNetworker {
    
    private static FabricNetworker INSTANCE;
    private final ResourceLocation channelName;
    
    private FabricNetworker(ResourceLocation channelName) {
        this.channelName = channelName;
    }
    
    public static GenericNetworker of(ResourceLocation channelName) {
        if (INSTANCE == null) {
            INSTANCE = new FabricNetworker(channelName);
        }
        return INSTANCE;
    }
    
    @Override
    public void initServerListener() {
        ServerPlayNetworking.registerGlobalReceiver(channelName, (server, player, handler, buf, sender) -> {
            PatternSchematicSyncPacket packet = new PatternSchematicSyncPacket(buf);
            server.execute(() -> packet.handleServer(player));
        });
    }
    
    @Override
    public void initClientListener() {
        // No S2C packets in this mod
    }
    
    @Override
    public void sendToServer(SimplePacketBase packet) {
        FriendlyByteBuf buf = PacketByteBufs.create();
        packet.write(buf);
        ClientPlayNetworking.send(channelName, buf);
    }
    
}
