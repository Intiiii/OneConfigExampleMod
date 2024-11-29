package org.inti.ares.mixin;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.inti.ares.AresMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RenderPlayer.class)
public class RenderPlayerMixin {

    @Inject(method = "doRender", at = @At("HEAD"))
    private void onPreRender(EntityPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity.isInvisible()) {
            float opacity = AresMod.config.invisiblePlayerOpacity / 100f;
            if (opacity > 0f && opacity < 1.0f) {
                GlStateManager.pushMatrix();
                GlStateManager.enableBlend();
                GlStateManager.enableAlpha();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.color(1.0f, 1.0f, 1.0f, opacity);
            }
        }
    }

    @Inject(method = "doRender", at = @At("RETURN"))
    private void onPostRender(EntityPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity.isInvisible()) {
            float opacity = AresMod.config.invisiblePlayerOpacity / 100f;
            if (opacity > 0f && opacity < 1.0f) {
                GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
                GlStateManager.disableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.popMatrix();
            }
        }
    }
}
