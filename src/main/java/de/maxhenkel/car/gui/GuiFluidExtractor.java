package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;

public class GuiFluidExtractor extends ScreenBase<ContainerFluidExtractor> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_fluid_extractor.png");

    private Inventory playerInv;
    private TileEntityFluidExtractor tile;

    public GuiFluidExtractor(ContainerFluidExtractor container, Inventory player, Component title) {
        super(GUI_TEXTURE, container, player, title, 176, 139);
        this.playerInv = player;
        this.tile = container.getTile();
    }

    @Override
    protected void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        super.extractLabels(guiGraphics, mouseX, mouseY);

        // Title
        guiGraphics.text(font, playerInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, FONT_COLOR, false);
        guiGraphics.text(font, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR, false);

        drawFilter(guiGraphics);
    }

    private void drawFilter(GuiGraphicsExtractor guiGraphics) {
        MutableComponent name;

        tile.setFilter(menu.getFilter());

        Fluid f = tile.getFilterFluid();

        if (f == null) {
            name = Component.literal("-");
        } else {
            name = Component.literal(new FluidStack(f, 1).getHoverName().getString());
        }

        guiGraphics.text(font, Component.translatable("filter.fluid", name.withStyle(ChatFormatting.WHITE)).getVisualOrderText(), 46, 28, FONT_COLOR, false);
    }

}
