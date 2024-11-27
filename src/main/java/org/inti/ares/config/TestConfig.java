package org.inti.ares.config;

import org.inti.ares.AresMod;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Slider;
import cc.polyfrost.oneconfig.config.annotations.Switch;
import cc.polyfrost.oneconfig.config.data.Mod;
import cc.polyfrost.oneconfig.config.data.ModType;

/**
 * Configuration for the Ares mod footstep volume controls
 */
public class TestConfig extends Config {
    @Slider(
            name = "Own Footsteps Volume",
            min = 0f, max = 100f,
            step = 5,
            description = "Adjust the volume of your own footsteps"
    )
    public static float ownFootstepsVolume = 100f;

    @Slider(
            name = "Other Players Footsteps Volume",
            min = 0f, max = 100f,
            step = 5,
            description = "Adjust the volume of other players' footsteps"
    )
    public static float otherFootstepsVolume = 100f;

    @Switch(
        name = "See Invisible Players",
        description = "Allows you to see players that are invisible"
    )
    public boolean seeInvisiblePlayers = false;

    @Slider(
        name = "Invisible Player Opacity", 
        description = "Opacity of invisible players when revealed",
        min = 0, 
        max = 100
    )
    public float invisiblePlayerOpacity = 50;

    public TestConfig() {
        super(new Mod(AresMod.MOD_ID, ModType.UTIL_QOL), AresMod.MOD_ID + ".json");
        initialize();
    }
}
