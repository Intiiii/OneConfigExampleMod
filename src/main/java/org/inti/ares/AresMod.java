package org.inti.ares;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inti.ares.config.TestConfig;
import cc.polyfrost.oneconfig.events.event.InitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * The Ares Mod - Footstep Volume Control
 */
@Mod(
    modid = AresMod.MOD_ID, 
    name = "Ares", 
    version = "1.0.0", 
    clientSideOnly = true
)
public class AresMod {
    public static final String MOD_ID = "ares";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);
    public static TestConfig config;

    @Mod.Instance(MOD_ID)
    public static AresMod INSTANCE;

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        LOGGER.info("Ares Mod Pre-Initialization");
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        LOGGER.info("Ares Mod Initialization");
        config = new TestConfig();
        LOGGER.info("Configuration loaded successfully");
    }
}
