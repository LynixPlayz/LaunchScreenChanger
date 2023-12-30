package me.lynix.launchscreenchanger.mixin;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import me.lynix.launchscreenchanger.client.LaunchScreenChangerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Overlay;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceReload;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.ColorHelper;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.IntSupplier;

import static net.minecraft.util.math.ColorHelper.Abgr.withAlpha;

@Mixin(SplashOverlay.class)
public class SplashOverlayMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private void modifyRender(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        // Cancel the original method
        ci.cancel();

        // Your custom rendering logic here
        renderCustom((SplashOverlay) (Object) this, context, mouseX, mouseY, delta);
    }

    private void renderCustom(SplashOverlay splashOverlay, DrawContext context, int mouseX, int mouseY, float delta) {
        // Your custom rendering logic here
        // You can modify BRAND_ARGB or perform other customizations

        int BRAND_ARGB = LaunchScreenChangerClient.color;
        int i = context.getScaledWindowWidth();
        int j = context.getScaledWindowHeight();
        long l = Util.getMeasuringTimeMs();
        if (((SplashOverlayAccessor) splashOverlay).getReloading() && ((SplashOverlayAccessor) splashOverlay).getReloadStartTime() == -1L) {
            ((SplashOverlayAccessor) splashOverlay).setReloadStartTime(l);
        }

        float f = ((SplashOverlayAccessor) splashOverlay).getReloadCompleteTime() > -1L ? (float)(l - ((SplashOverlayAccessor) splashOverlay).getReloadCompleteTime()) / 1000.0F : -1.0F;
        float g = ((SplashOverlayAccessor) splashOverlay).getReloadStartTime() > -1L ? (float)(l - ((SplashOverlayAccessor) splashOverlay).getReloadStartTime()) / 500.0F : -1.0F;
        float h;
        int k;
        if (f >= 1.0F) {
            if (MinecraftClient.getInstance().currentScreen != null) {
                MinecraftClient.getInstance().currentScreen.render(context, 0, 0, delta);
            }

            k = MathHelper.ceil((1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F)) * 255.0F);
            context.fill(RenderLayer.getGuiOverlay(), 0, 0, i, j, withAlpha(BRAND_ARGB, k));
            h = 1.0F - MathHelper.clamp(f - 1.0F, 0.0F, 1.0F);
        } else if (((SplashOverlayAccessor) splashOverlay).getReloading()) {
            if (MinecraftClient.getInstance().currentScreen != null && g < 1.0F) {
                MinecraftClient.getInstance().currentScreen.render(context, mouseX, mouseY, delta);
            }

            k = MathHelper.ceil(MathHelper.clamp((double)g, 0.15, 1.0) * 255.0);
            context.fill(RenderLayer.getGuiOverlay(), 0, 0, i, j, withAlpha(BRAND_ARGB, k));
            h = MathHelper.clamp(g, 0.0F, 1.0F);
        } else {
            k = BRAND_ARGB;
            float m = (float)(k >> 16 & 255) / 255.0F;
            float n = (float)(k >> 8 & 255) / 255.0F;
            float o = (float)(k & 255) / 255.0F;
            GlStateManager._clearColor(m, n, o, 1.0F);
            GlStateManager._clear(16384, MinecraftClient.IS_SYSTEM_MAC);
            h = 1.0F;
        }

        k = (int)((double)context.getScaledWindowWidth() * 0.5);
        int p = (int)((double)context.getScaledWindowHeight() * 0.5);
        double d = Math.min((double)context.getScaledWindowWidth() * 0.75, (double)context.getScaledWindowHeight()) * 0.25;
        int q = (int)(d * 0.5);
        double e = d * 4.0;
        int r = (int)(e * 0.5);
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(770, 1);
        context.setShaderColor(1.0F, 1.0F, 1.0F, h);
        context.drawTexture(((SplashOverlayAccessor) splashOverlay).getLOGO(), k - r, p - q, r, (int)d, -0.0625F, 0.0F, 120, 60, 120, 120);
        context.drawTexture(((SplashOverlayAccessor) splashOverlay).getLOGO(), k, p - q, r, (int)d, 0.0625F, 60.0F, 120, 60, 120, 120);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        int s = (int)((double)context.getScaledWindowHeight() * 0.8325);
        float t = ((SplashOverlayAccessor) splashOverlay).getReload().getProgress();
        ((SplashOverlayAccessor) splashOverlay).setProgress(MathHelper.clamp(((SplashOverlayAccessor) splashOverlay).getProgress() * 0.95F + t * 0.050000012F, 0.0F, 1.0F));
        if (f < 1.0F) {
            ((SplashOverlayInvoker) splashOverlay).invokeRenderProgressBar(context, i / 2 - r, s - 5, i / 2 + r, s + 5, 1.0F - MathHelper.clamp(f, 0.0F, 1.0F));
        }

        if (f >= 2.0F) {
            MinecraftClient.getInstance().setOverlay((Overlay)null);
        }

        if (((SplashOverlayAccessor) splashOverlay).getReloadCompleteTime() == -1L && ((SplashOverlayAccessor) splashOverlay).getReload().isComplete() && (!((SplashOverlayAccessor) splashOverlay).getReloading() || g >= 2.0F)) {
            try {
                ((SplashOverlayAccessor) splashOverlay).getReload().throwException();
                ((SplashOverlayAccessor) splashOverlay).getExceptionHandler().accept(Optional.empty());
            } catch (Throwable var23) {
                ((SplashOverlayAccessor) splashOverlay).getExceptionHandler().accept(Optional.of(var23));
            }

            ((SplashOverlayAccessor) splashOverlay).setReloadCompleteTime(Util.getMeasuringTimeMs());
            if (MinecraftClient.getInstance().currentScreen != null) {
                MinecraftClient.getInstance().currentScreen.init(MinecraftClient.getInstance(), context.getScaledWindowWidth(), context.getScaledWindowHeight());
            }
        }
    }
}