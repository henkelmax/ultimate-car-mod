package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiBlastFurnace extends GuiEnergyFluidProducer<ContainerBlastFurnace> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_blastfurnace.png");

    public GuiBlastFurnace(ContainerBlastFurnace container, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, container, playerInventory, title);

    }

    @Override
    public ResourceLocation getGuiTexture() {
        return GUI_TEXTURE;
    }

    @Override
    public String getUnlocalizedTooltipLiquid() {
        return "tooltip.methanol";
    }

}
