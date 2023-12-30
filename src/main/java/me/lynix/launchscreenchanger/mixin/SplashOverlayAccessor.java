package me.lynix.launchscreenchanger.mixin;

import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Optional;
import java.util.function.Consumer;

@Mixin(SplashOverlay.class)
public interface SplashOverlayAccessor {
    @Accessor
    boolean getReloading();

    @Accessor
    long getReloadStartTime();

    @Accessor
    long getReloadCompleteTime();

    @Accessor
    Identifier getLOGO();

    @Accessor
    float getProgress();

    @Accessor
    Consumer<Optional<Throwable>> getExceptionHandler();

    @Accessor("progress")
    void setProgress(float progress);

    @Accessor("reloadStartTime")
    void setReloadStartTime(long reloadStartTime);

    @Accessor("reloadCompleteTime")
    void setReloadCompleteTime(long reloadCompleteTime);

    @Accessor
    ResourceReload getReload();
}
