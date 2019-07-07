package de.maxhenkel.car.events;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.gui.CarOptionsSoundsScreen;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.OptionsSoundsScreen;
import net.minecraft.client.gui.screen.Screen;
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
        if (!(event.getGui() instanceof OptionsSoundsScreen)) {
            return;
        }

        OptionsSoundsScreen sounds = (OptionsSoundsScreen) event.getGui();

        if (!sounds.getClass().equals(OptionsSoundsScreen.class)) {
            return;
        }

        try {
            Field parentField = null;

            for (Field field : sounds.getClass().getDeclaredFields()) {
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

            event.setGui(new CarOptionsSoundsScreen(parent, Minecraft.getInstance().gameSettings));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onSound(PlaySoundAtEntityEvent event) {
        if (ModSounds.isCarSoundCategory(event.getSound())) {
            event.setVolume(Config.carVolume.get().floatValue());
        }
    }
}
