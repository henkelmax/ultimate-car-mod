package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class GuiFluidExtractor extends ScreenBase<ContainerFluidExtractor> {

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_fluid_extractor.png");

    private Inventory playerInv;
    private TileEntityFluidExtractor tile;

    public GuiFluidExtractor(ContainerFluidExtractor container, Inventory player, Component title) {
        super(GUI_TEXTURE, container, player, title);
        this.playerInv = player;
        this.tile = container.getTile();

        imageWidth = 176;
        imageHeight = 139;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        // Title
        guiGraphics.drawString(font, playerInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, FONT_COLOR, false);
        guiGraphics.drawString(font, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR, false);

        drawFilter(guiGraphics);
    }

    private void drawFilter(GuiGraphics guiGraphics) {
        MutableComponent name;

        tile.setFilter(menu.getFilter());

        Fluid f = tile.getFilterFluid();

        if (f == null) {
            name = Component.literal("-");
        } else {
            name = Component.literal(new FluidStack(f, 1).getHoverName().getString());
        }

        guiGraphics.drawString(font, Component.translatable("filter.fluid", name.withStyle(ChatFormatting.WHITE)).getVisualOrderText(), 46, 28, FONT_COLOR, false);
    }

}
