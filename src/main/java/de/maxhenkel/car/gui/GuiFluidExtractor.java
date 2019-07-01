package de.maxhenkel.car.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidExtractor extends GuiBase {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_fluid_extractor.png");
    private static final int fontColor = 4210752;

    private PlayerInventory playerInv;
    private TileEntityFluidExtractor tile;

    public GuiFluidExtractor(ContainerFluidExtractor container, PlayerInventory player) {
        super(GUI_TEXTURE, container, player, new TranslationTextComponent("block.car.fluid.extractor"));
        this.playerInv = player;
        this.tile = tile;

        xSize = 176;
        ySize = 139;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Title
        font.drawString(playerInv.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2,
                fontColor);
        font.drawString(tile.getDisplayName().getUnformattedText(), 8, 6, fontColor);

        drawFilter();
    }

    private void drawFilter() {
        String name = "-";

        Fluid f = tile.getFilterFluid();

        if (f != null) {
            name = f.getLocalizedName(new FluidStack(f, 1));
        }

        font.drawString(new TranslationTextComponent("filter.fluid", name).getFormattedText(), 46, 28, fontColor);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i, j, 0, 0, this.xSize, this.ySize);
    }

}
