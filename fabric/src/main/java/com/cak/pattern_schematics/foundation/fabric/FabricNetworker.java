package com.cak.pattern_schematics.foundation.fabric;

import com.cak.pattern_schematics.foundation.GenericNetworker;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import me.pepperbell.simplenetworking.SimpleChannel;

public class FabricNetworker implements GenericNetworker {
    
    SimpleChannel channel;
    
    private FabricNetworker(SimpleChannel channel) {
        this.channel = channel;
    }
    
    public static GenericNetworker of(SimpleChannel channel) {
        return new FabricNetworker(channel);
    }
    
    @Override
    public void initServerListener() {
        channel.initServerListener();
    }
    
    @Override
    public void initClientListener() {
        channel.initClientListener();
    }
    
    @Override
    public void sendToServer(SimplePacketBase packet) {
        channel.sendToServer(packet);
    }
    
}
