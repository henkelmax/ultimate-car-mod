package de.maxhenkel.car.mixins;

import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Gui.class)
public class GuiMixin {

    //TODO Re-add experience bar rendering
    /*@Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    public void renderExperienceBar(GuiGraphics guiGraphics, int i, CallbackInfo ci) {
        if (RenderEvents.onRenderExperienceBar(guiGraphics, i)) {
            ci.cancel();
        }
    }*/

}
