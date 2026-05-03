package com.cpsmod;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class CpsModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        CpsTracker.register();
        CpsHudRenderer.register();
    }
}
