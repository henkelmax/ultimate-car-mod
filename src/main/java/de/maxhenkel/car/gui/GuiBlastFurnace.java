package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class GuiBlastFurnace extends GuiEnergyFluidProducer<ContainerBlastFurnace> {

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_blastfurnace.png");

    public GuiBlastFurnace(ContainerBlastFurnace container, Inventory playerInventory, Component title) {
        super(GUI_TEXTURE, container, playerInventory, title);

    }

    @Override
    public String getUnlocalizedTooltipLiquid() {
        return "tooltip.methanol";
    }

}
