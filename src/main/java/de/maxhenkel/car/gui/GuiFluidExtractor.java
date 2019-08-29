package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidExtractor extends GuiBase<ContainerFluidExtractor> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_fluid_extractor.png");
    private static final int fontColor = 4210752;

    private PlayerInventory playerInv;
    private TileEntityFluidExtractor tile;

    public GuiFluidExtractor(ContainerFluidExtractor container, PlayerInventory player, ITextComponent title) {
        super(GUI_TEXTURE, container, player, title);
        this.playerInv = player;
        this.tile = container.getTile();

        xSize = 176;
        ySize = 139;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Title
        font.drawString(playerInv.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2,
                fontColor);
        font.drawString(tile.getDisplayName().getFormattedText(), 8, 6, fontColor);

        drawFilter();
    }

    private void drawFilter() {
        ITextComponent name;

        Fluid f = tile.getFilterFluid();

        if (f == null) {
            name = new StringTextComponent("-");
        } else {
            name = new FluidStack(f, 1).getDisplayName();
        }

        font.drawString(new TranslationTextComponent("filter.fluid", name.applyTextStyle(TextFormatting.WHITE)).getFormattedText(), 46, 28, fontColor);
    }

}
