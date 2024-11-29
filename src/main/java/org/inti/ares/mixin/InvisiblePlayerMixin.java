package org.inti.ares.mixin;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import org.inti.ares.AresMod;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(RendererLivingEntity.class)
public abstract class InvisiblePlayerMixin<T extends EntityLivingBase> {
    
    @Shadow protected ModelBase mainModel;
    
    @Inject(method = "renderModel", at = @At("HEAD"), cancellable = true)
    protected void onRenderModel(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor, CallbackInfo ci) {
        if (entity instanceof EntityPlayer && entity.isInvisible()) {
            float opacity = AresMod.config.invisiblePlayerOpacity / 100f;
            
            // Als opacity 0 is, render helemaal niets
            if (opacity <= 0f) {
                ci.cancel();
                return;
            }

            // Als opacity tussen 0 en 100 is, render met transparantie
            if (opacity < 1.0f) {
                GlStateManager.pushMatrix();
                GlStateManager.color(1.0F, 1.0F, 1.0F, opacity);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(770, 771);
                GlStateManager.alphaFunc(516, 0.003921569F);
            }

            this.mainModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);

            if (opacity < 1.0f) {
                GlStateManager.disableBlend();
                GlStateManager.alphaFunc(516, 0.1F);
                GlStateManager.popMatrix();
                GlStateManager.depthMask(true);
            }

            ci.cancel();
        }
    }
}
