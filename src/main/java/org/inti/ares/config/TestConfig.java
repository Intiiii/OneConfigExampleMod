package org.inti.ares.config;

import org.inti.ares.AresMod;
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

    @Slider(
        name = "Invisible Players Visibility", 
        description = "Control the visibility of invisible players (0 = invisible, 100 = fully visible)",
        min = 0, 
        max = 100
    )
    public float invisiblePlayerOpacity = 0;

    public TestConfig() {
        super(new Mod(AresMod.MOD_ID, ModType.UTIL_QOL), AresMod.MOD_ID + ".json");
        initialize();
    }
}
