package org.inti.ares.mixin;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.inti.ares.AresMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class InvisiblePlayerMixin {

    @Inject(method = "isInvisible()Z", at = @At("HEAD"), cancellable = true)
    private void onIsInvisible(CallbackInfoReturnable<Boolean> cir) {
        if ((Object)this instanceof EntityPlayer) {
            EntityPlayer self = (EntityPlayer)(Object)this;
            
            if (AresMod.config.seeInvisiblePlayers) {
                // If the player is the local player or another player in render distance
                if (self == Minecraft.getMinecraft().thePlayer || 
                    Minecraft.getMinecraft().theWorld.playerEntities.contains(self)) {
                    cir.setReturnValue(false);
                    AresMod.LOGGER.info("Forcing player to be visible: " + self.getName());
                }
            }
        }
    }
}
