package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiOilMill extends GuiEnergyFluidProducer<ContainerOilMill> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_oil_mill.png");

    public GuiOilMill(ContainerOilMill containerOilMill, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, containerOilMill, playerInventory, title);
    }

    @Override
    public String getUnlocalizedTooltipLiquid() {
        return "tooltip.oil";
    }

}
