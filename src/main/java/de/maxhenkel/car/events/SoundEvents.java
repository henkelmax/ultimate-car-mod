package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.gui.CarOptionsSoundsScreen;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.SoundOptionsScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.lang.reflect.Field;

@OnlyIn(Dist.CLIENT)
public class SoundEvents {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onOpenGUI(net.minecraftforge.client.event.GuiOpenEvent event) {
        if (!(event.getGui() instanceof SoundOptionsScreen)) {
            return;
        }

        SoundOptionsScreen sounds = (SoundOptionsScreen) event.getGui();

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

            event.setGui(new CarOptionsSoundsScreen(parent, Minecraft.getInstance().options));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onSound(PlaySoundAtEntityEvent event) {
        if (ModSounds.isCarSoundCategory(event.getSound())) {
            event.setVolume(Main.CLIENT_CONFIG.carVolume.get().floatValue());
        }
    }

}
