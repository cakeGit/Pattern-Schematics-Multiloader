package com.cak.pattern_schematics.foundation;

import net.createmod.catnip.net.base.BasePacketPayload;

public interface GenericNetworker {

    void initServerListener();
    void initClientListener();
    void sendToServer(BasePacketPayload packet);
    
}
