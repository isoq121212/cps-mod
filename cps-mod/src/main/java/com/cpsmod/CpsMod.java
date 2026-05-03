package com.cpsmod;

import net.fabricmc.api.ModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CpsMod implements ModInitializer {
    public static final String MOD_ID = "cpsmod";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        LOGGER.info("CPS Mod для BedWars загружен!");
    }
}
