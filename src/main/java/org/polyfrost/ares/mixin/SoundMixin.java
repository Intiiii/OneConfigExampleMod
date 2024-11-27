package org.polyfrost.ares.mixin;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import org.polyfrost.ares.AresMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SoundManager.class)
public class SoundMixin {
    
    @ModifyVariable(
        method = "playSound",
        at = @At("HEAD"),
        ordinal = 0
    )
    private ISound modifySound(ISound sound) {
        if (sound == null) return sound;
        
        String soundName = sound.getSoundLocation().getResourcePath().toLowerCase();
        System.out.println("[Ares] Sound playing: " + soundName);
        
        // Check of het een voetstap geluid is
        if (soundName.contains("step") || soundName.contains("dig")) {
            float x = sound.getXPosF();
            float y = sound.getYPosF();
            float z = sound.getZPosF();
            
            Entity closestEntity = null;
            double closestDistance = 1.0; // Maximale afstand om een entity te vinden
            
            // Zoek de dichtstbijzijnde entity bij het geluid
            for (Entity entity : Minecraft.getMinecraft().theWorld.loadedEntityList) {
                double distance = entity.getDistanceSq(x, y, z);
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestEntity = entity;
                }
            }
            
            // Als we een entity hebben gevonden
            if (closestEntity != null) {
                System.out.println("[Ares] Found entity: " + closestEntity.getClass().getName());
                
                // Check of het de speler zelf is
                if (closestEntity == Minecraft.getMinecraft().thePlayer) {
                    return new ModifiedSound(sound, AresMod.config.ownFootstepsVolume / 100f);
                } else {
                    return new ModifiedSound(sound, AresMod.config.otherFootstepsVolume / 100f);
                }
            }
        }
        
        return sound;
    }
    
    // Inner class om het volume van een geluid aan te passen
    private static class ModifiedSound implements ISound {
        private final ISound original;
        private final float volumeMultiplier;
        
        public ModifiedSound(ISound original, float volumeMultiplier) {
            this.original = original;
            this.volumeMultiplier = volumeMultiplier;
        }
        
        @Override
        public net.minecraft.util.ResourceLocation getSoundLocation() {
            return original.getSoundLocation();
        }
        
        @Override
        public float getVolume() {
            return Math.max(0.0001f, original.getVolume() * volumeMultiplier);
        }
        
        @Override
        public float getPitch() {
            return original.getPitch();
        }
        
        @Override
        public float getXPosF() {
            return original.getXPosF();
        }
        
        @Override
        public float getYPosF() {
            return original.getYPosF();
        }
        
        @Override
        public float getZPosF() {
            return original.getZPosF();
        }
        
        @Override
        public net.minecraft.client.audio.ISound.AttenuationType getAttenuationType() {
            return original.getAttenuationType();
        }
        
        @Override
        public boolean canRepeat() {
            return original.canRepeat();
        }
        
        @Override
        public int getRepeatDelay() {
            return original.getRepeatDelay();
        }
    }
}
