package de.maxhenkel.car.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidExtractor extends ScreenBase<ContainerFluidExtractor> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_fluid_extractor.png");

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
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        // Title
        font.draw(matrixStack, playerInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, FONT_COLOR);
        font.draw(matrixStack, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR);

        drawFilter(matrixStack);
    }

    private void drawFilter(PoseStack matrixStack) {
        MutableComponent name;

        Fluid f = tile.getFilterFluid();

        if (f == null) {
            name = Component.literal("-");
        } else {
            name = Component.literal(new FluidStack(f, 1).getDisplayName().getString());
        }

        font.draw(matrixStack, Component.translatable("filter.fluid", name.withStyle(ChatFormatting.WHITE)).getVisualOrderText(), 46, 28, FONT_COLOR);
    }

}
