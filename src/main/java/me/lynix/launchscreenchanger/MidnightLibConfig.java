package me.lynix.launchscreenchanger;

import eu.midnightdust.lib.config.MidnightConfig;

public class MidnightLibConfig extends MidnightConfig {
    @Comment public static Comment text1;

    @Entry(width = 7, min = 7, isColor = true, name = "Background Color") public static String bgColor = "#ef323d";
}
