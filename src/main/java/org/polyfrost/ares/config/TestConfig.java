package org.polyfrost.ares.config;

import org.polyfrost.ares.AresMod;
import cc.polyfrost.oneconfig.config.Config;
import cc.polyfrost.oneconfig.config.annotations.Slider;
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

    public TestConfig() {
        super(new Mod(AresMod.NAME, ModType.UTIL_QOL), AresMod.MODID + ".json");
        initialize();
    }
}
