package com.cak.pattern_schematics.foundation;

import com.simibubi.create.foundation.networking.SimplePacketBase;

public interface GenericNetworker {
    
    void initServerListener();
    void initClientListener();
    void sendToServer(SimplePacketBase packet);
    
}
