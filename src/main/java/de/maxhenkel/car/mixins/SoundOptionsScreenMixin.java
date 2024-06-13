package de.maxhenkel.car.mixins;

import de.maxhenkel.car.Main;
import net.minecraft.client.OptionInstance;
import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SoundOptionsScreen.class)
public abstract class SoundOptionsScreenMixin extends OptionsSubScreen {

    public SoundOptionsScreenMixin(Screen screen, Options options, Component component) {
        super(screen, options, component);
    }

    @Inject(method = "getAllSoundOptionsExceptMaster", at = @At("RETURN"), cancellable = true)
    private void getAllSoundOptionsExceptMaster(CallbackInfoReturnable<OptionInstance<?>[]> cir) {
        OptionInstance<?>[] returnValue = cir.getReturnValue();
        OptionInstance<?>[] newReturnValue = new OptionInstance<?>[returnValue.length + 1];
        System.arraycopy(returnValue, 0, newReturnValue, 0, returnValue.length);
        newReturnValue[returnValue.length] = new OptionInstance<>("soundCategory.car", OptionInstance.noTooltip(), (component, volume) -> {
            return volume == 0D
                    ?
                    Component.translatable("options.generic_value", component, CommonComponents.OPTION_OFF)
                    :
                    Component.translatable("options.percent_value", component, (int) (volume * 100D));
        }, OptionInstance.UnitDouble.INSTANCE, Main.CLIENT_CONFIG.carVolume.get(), (value) -> {
            Main.CLIENT_CONFIG.carVolume.set(value);
            Main.CLIENT_CONFIG.carVolume.save();
        });

        cir.setReturnValue(newReturnValue);
    }

}
