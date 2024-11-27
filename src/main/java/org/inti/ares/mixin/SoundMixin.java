package org.inti.ares.mixin;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.inti.ares.AresMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SoundManager.class)
public class SoundMixin {
    private static final Logger LOGGER = LogManager.getLogger(AresMod.MOD_ID);

    /**
     * Modifies sound volume for footstep and digging sounds.
     * 
     * @param sound Original sound to be played
     * @return Modified sound with adjusted volume
     */
    @ModifyVariable(
        method = "playSound",
        at = @At("HEAD"),
        ordinal = 0
    )
    private ISound modifySound(ISound sound) {
        try {
            if (sound == null) return sound;
            
            String soundName = sound.getSoundLocation().getResourcePath().toLowerCase();
            
            // Check if it's a footstep or digging sound
            if (soundName.contains("step") || soundName.contains("dig")) {
                float x = sound.getXPosF();
                float y = sound.getYPosF();
                float z = sound.getZPosF();
                
                Entity closestEntity = findClosestEntity(x, y, z);
                
                if (closestEntity != null) {
                    if (closestEntity == Minecraft.getMinecraft().thePlayer) {
                        LOGGER.debug("Modifying own footstep sound volume to: {}", AresMod.config.ownFootstepsVolume);
                        return new ModifiedSound(sound, AresMod.config.ownFootstepsVolume / 100f);
                    } else {
                        LOGGER.debug("Modifying other player's footstep sound volume to: {}", AresMod.config.otherFootstepsVolume);
                        return new ModifiedSound(sound, AresMod.config.otherFootstepsVolume / 100f);
                    }
                }
            }
            
            return sound;
        } catch (Exception e) {
            LOGGER.error("Error modifying sound volume", e);
            return sound;
        }
    }

    /**
     * Find the closest entity to the sound's location.
     * 
     * @param x X coordinate of the sound
     * @param y Y coordinate of the sound
     * @param z Z coordinate of the sound
     * @return Closest entity or null
     */
    private Entity findClosestEntity(float x, float y, float z) {
        Entity closestEntity = null;
        double closestDistance = 1.0; // Maximum distance to find an entity
        
        for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
            double distance = entity.getDistanceSq(x, y, z);
            if (distance < closestDistance) {
                closestDistance = distance;
                closestEntity = entity;
            }
        }
        
        return closestEntity;
    }

    /**
     * Custom sound implementation that wraps the original sound
     * and allows volume modification.
     */
    private static class ModifiedSound implements ISound {
        private final ISound original;
        private final float volumeMultiplier;

        public ModifiedSound(ISound original, float volumeMultiplier) {
            this.original = original;
            this.volumeMultiplier = volumeMultiplier;
        }

        @Override
        public float getVolume() {
            return Math.max(0.0001f, original.getVolume() * volumeMultiplier);
        }

        // Delegate all other methods to the original sound
        @Override public float getPitch() { return original.getPitch(); }
        @Override public float getXPosF() { return original.getXPosF(); }
        @Override public float getYPosF() { return original.getYPosF(); }
        @Override public float getZPosF() { return original.getZPosF(); }
        @Override public net.minecraft.util.ResourceLocation getSoundLocation() { return original.getSoundLocation(); }
        @Override public net.minecraft.client.audio.ISound.AttenuationType getAttenuationType() { return original.getAttenuationType(); }
        @Override public boolean canRepeat() { return original.canRepeat(); }
        @Override public int getRepeatDelay() { return original.getRepeatDelay(); }
    }
}
