package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.sounds.ModSounds;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;

@OnlyIn(Dist.CLIENT)
public class SoundEvents {

    @SubscribeEvent
    public void onSound(PlayLevelSoundEvent.AtEntity event) {
        if (event.getSound() != null && ModSounds.isCarSoundCategory(event.getSound().value())) {
            event.setNewVolume(Main.CLIENT_CONFIG.carVolume.get().floatValue());
        }
    }

    @SubscribeEvent
    public void onSound(PlayLevelSoundEvent.AtPosition event) {
        if (event.getSound() != null && ModSounds.isCarSoundCategory(event.getSound().value())) {
            event.setNewVolume(Main.CLIENT_CONFIG.carVolume.get().floatValue());
        }
    }

}
