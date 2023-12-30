package me.lynix.launchscreenchanger.client;

import me.lynix.launchscreenchanger.MidnightLibConfig;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.text.Text;
import net.minecraft.util.math.ColorHelper;
import org.jetbrains.annotations.Nullable;
import eu.midnightdust.lib.config.MidnightConfig;

import java.awt.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class LaunchScreenChangerClient implements ClientModInitializer {

    public static int color = ColorHelper.Argb.getArgb(255, 239, 50, 61);

    @Override
    public void onInitializeClient() {
        MidnightConfig.init("launchscreenchanger", MidnightLibConfig.class);
        Color decodedColor = Color.decode(MidnightLibConfig.bgColor);
        color = (!Objects.equals(MidnightLibConfig.bgColor, "")) ? ColorHelper.Argb.getArgb(decodedColor.getAlpha(), decodedColor.getRed(), decodedColor.getGreen(), decodedColor.getBlue()) : ColorHelper.Argb.getArgb(255, 239, 50, 61);
        System.out.println("LaunchScreenChanger initialized!");
    }


}
