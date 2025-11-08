package com.cak.pattern_schematics.foundation.forge;

import com.cak.pattern_schematics.foundation.GenericNetworker;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraftforge.network.simple.SimpleChannel;

public class ForgeNetworker implements GenericNetworker {
    
    SimpleChannel channel;
    
    private ForgeNetworker(SimpleChannel channel) {
        this.channel = channel;
    }
    
    public static GenericNetworker of(SimpleChannel channel) {
        return new ForgeNetworker(channel);
    }
    
    @Override
    public void initServerListener() {}
    
    @Override
    public void initClientListener() {}
    
    @Override
    public void sendToServer(SimplePacketBase packet) {
        channel.sendToServer(packet);
    }
    
}
