package org.polyfrost.ares;

import org.polyfrost.ares.config.TestConfig;
import cc.polyfrost.oneconfig.events.event.InitializationEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

/**
 * The Ares Mod - Footstep Volume Control
 */
@Mod(modid = AresMod.MODID, name = AresMod.NAME, version = AresMod.VERSION)
public class AresMod {
    public static final String MODID = "@ID@";
    public static final String NAME = "@NAME@";
    public static final String VERSION = "@VER@";
    
    @Mod.Instance(MODID)
    public static AresMod INSTANCE;
    public static TestConfig config;

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        config = new TestConfig();
    }
}
