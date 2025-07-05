package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiOilMill extends GuiEnergyFluidProducer<ContainerOilMill> {

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_oil_mill.png");

    public GuiOilMill(ContainerOilMill containerOilMill, Inventory playerInventory, Component title) {
        super(GUI_TEXTURE, containerOilMill, playerInventory, title);
    }

    @Override
    public String getUnlocalizedTooltipLiquid() {
        return "tooltip.oil";
    }

}
