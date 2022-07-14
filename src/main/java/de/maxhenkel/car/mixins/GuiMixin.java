package de.maxhenkel.car.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.events.RenderEvents;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
public class GuiMixin {

    @Inject(method = "renderExperienceBar", at = @At("HEAD"), cancellable = true)
    public void renderExperienceBar(PoseStack poseStack, int i, CallbackInfo ci) {
        if (RenderEvents.onRenderExperienceBar(poseStack, i)) {
            ci.cancel();
        }
    }

}
