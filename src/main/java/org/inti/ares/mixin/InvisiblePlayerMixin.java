package org.inti.ares.mixin;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import org.inti.ares.AresMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import java.util.List;

@Mixin(RenderPlayer.class)
public abstract class InvisiblePlayerMixin extends RendererLivingEntity<AbstractClientPlayer> {

    @Shadow @Final private boolean smallArms;

    protected InvisiblePlayerMixin(RenderManager renderManagerIn, ModelBase modelBaseIn, float shadowSizeIn) {
        super(renderManagerIn, modelBaseIn, shadowSizeIn);
    }

    @Inject(method = "doRender(Lnet/minecraft/client/entity/AbstractClientPlayer;DDDFF)V", at = @At("HEAD"), cancellable = true)
    public void onDoRender(AbstractClientPlayer entity, double x, double y, double z, float entityYaw, float partialTicks, CallbackInfo ci) {
        if (entity.isInvisible()) {
            float opacity = AresMod.config.invisiblePlayerOpacity / 100f;
            
            // Als opacity 0 is, gebruik vanilla rendering
            if (opacity <= 0f) {
                return;
            }

            // Begin met render setup
            GlStateManager.pushMatrix();
            GlStateManager.disableCull();
            
            // Positioneer de entity
            GlStateManager.translate(x, y, z);
            GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);

            // Setup transparantie als nodig
            if (opacity < 1.0f) {
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.color(1.0F, 1.0F, 1.0F, opacity);
            }

            // Render de player model en skin
            float f = 0.0625F;
            float scale = 0.9375F;
            GlStateManager.enableAlpha();
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);
            GlStateManager.translate(0.0F, -1.5F, 0.0F);

            // Bind de player skin texture
            RenderPlayer self = (RenderPlayer)(Object)this;
            ResourceLocation skin = entity.getLocationSkin();
            if (skin != null) {
                self.bindTexture(skin);
            }

            // Render het model en layers
            self.getMainModel().render(entity, entity.limbSwing, entity.limbSwingAmount, entity.ticksExisted, entity.rotationYawHead - entity.rotationYaw, entity.rotationPitch, f);

            // Cleanup
            if (opacity < 1.0f) {
                GlStateManager.disableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            }
            
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
            
            ci.cancel();
        }
    }
}
