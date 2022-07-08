package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.gui.CarOptionsSoundsScreen;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.event.PlayLevelSoundEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Field;

@OnlyIn(Dist.CLIENT)
public class SoundEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onOpenGUI(ScreenEvent.Opening event) {
        if (!(event.getScreen() instanceof SoundOptionsScreen)) {
            return;
        }

        SoundOptionsScreen sounds = (SoundOptionsScreen) event.getScreen();

        if (!sounds.getClass().equals(SoundOptionsScreen.class)) {
            return;
        }

        try {
            Field parentField = null;

            for (Field field : sounds.getClass().getSuperclass().getDeclaredFields()) {
                if (field.getType().equals(Screen.class)) {
                    parentField = field;
                    break;
                }
            }

            if (parentField == null) {
                return;
            }
            parentField.setAccessible(true);

            Screen parent = (Screen) parentField.get(sounds);

            event.setNewScreen(new CarOptionsSoundsScreen(parent, Minecraft.getInstance().options));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onSound(PlayLevelSoundEvent.AtEntity event) {
        if (ModSounds.isCarSoundCategory(event.getSound())) {
            event.setNewVolume(Main.CLIENT_CONFIG.carVolume.get().floatValue());
        }
    }

    @SubscribeEvent
    public void onSound(PlayLevelSoundEvent.AtPosition event) {
        if (ModSounds.isCarSoundCategory(event.getSound())) {
            event.setNewVolume(Main.CLIENT_CONFIG.carVolume.get().floatValue());
        }
    }

}
