package me.lynix.launchscreenchanger.mixin;

import eu.midnightdust.lib.config.MidnightConfig;
import me.lynix.launchscreenchanger.MidnightLibConfig;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitleScreenMixin extends Screen {
    protected TitleScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("RETURN"), method = "initWidgetsNormal")
    private void addModsButton(int y, int spacingY, CallbackInfo ci) {
        this.addDrawableChild(ButtonWidget.builder(Text.of("Launch Screen Changer Config"), (button) -> {
            MinecraftClient.getInstance().setScreen(MidnightConfig.getScreen(null, "launchscreenchanger"));
        }).dimensions(this.width - 100, 0, 100, 20).build());
    }
}
