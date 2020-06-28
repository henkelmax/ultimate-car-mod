package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidExtractor extends GuiBase<ContainerFluidExtractor> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_fluid_extractor.png");

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
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);

        // Title
        field_230712_o_.func_238422_b_(matrixStack, playerInv.getDisplayName(), 8, this.ySize - 96 + 2, FONT_COLOR);
        field_230712_o_.func_238422_b_(matrixStack, tile.getDisplayName(), 8, 6, FONT_COLOR);

        drawFilter(matrixStack);
    }

    private void drawFilter(MatrixStack matrixStack) {
        IFormattableTextComponent name;

        Fluid f = tile.getFilterFluid();

        if (f == null) {
            name = new StringTextComponent("-");
        } else {
            name = new StringTextComponent(new FluidStack(f, 1).getDisplayName().getString());
        }

        field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("filter.fluid", name.func_240699_a_(TextFormatting.WHITE)), 46, 28, FONT_COLOR);
    }

}
