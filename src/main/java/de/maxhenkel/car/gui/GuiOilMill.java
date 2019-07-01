package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiOilMill extends GuiEnergyFluidProducer {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_oil_mill.png");

    public GuiOilMill(ContainerOilMill containerOilMill, PlayerInventory playerInventory) {
        super(GUI_TEXTURE, containerOilMill, playerInventory, new TranslationTextComponent("block.car.oil_mill"));
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return GUI_TEXTURE;
    }

    @Override
    public String getUnlocalizedTooltipLiquid() {
        return "tooltip.oil";
    }

}
