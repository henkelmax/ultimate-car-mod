package de.maxhenkel.car.events;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.sounds.ModSounds;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;

public class SoundEvents {

    @SubscribeEvent
    public void onSound(PlayLevelSoundEvent.AtEntity event) {
        if (event.getSound() != null && ModSounds.isCarSoundCategory(event.getSound().value())) {
            event.setNewVolume(CarMod.CLIENT_CONFIG.carVolume.get().floatValue());
        }
    }

    @SubscribeEvent
    public void onSound(PlayLevelSoundEvent.AtPosition event) {
        if (event.getSound() != null && ModSounds.isCarSoundCategory(event.getSound().value())) {
            event.setNewVolume(CarMod.CLIENT_CONFIG.carVolume.get().floatValue());
        }
    }

}
