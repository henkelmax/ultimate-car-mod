package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.blocks.tileentity.TileEntityEnergyFluidProducer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public abstract class GuiEnergyFluidProducer extends GuiBase {

    private static final int fontColor = 4210752;

    private PlayerInventory playerInv;
    private TileEntityEnergyFluidProducer tile;

    public GuiEnergyFluidProducer(ResourceLocation texture, ContainerEnergyFluidProducer container, PlayerInventory playerInventory, ITextComponent title) {
        super(texture, container, playerInventory, title);
        this.playerInv = playerInventory;
        this.tile = container.getTile();

        xSize = 176;
        ySize = 166;
    }

    public abstract ResourceLocation getGuiTexture();

    public String getUnlocalizedTooltipEnergy() {
        return "tooltip.energy";
    }

    public String getUnlocalizedTooltipProgress() {
        return "tooltip.progress";
    }

    public abstract String getUnlocalizedTooltipLiquid();

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Titles
        font.drawString(getTitle().getFormattedText(), 38, 6, fontColor);
        font.drawString(playerInv.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2,
                fontColor);

        if (mouseX >= guiLeft + 11 && mouseX <= guiLeft + 16 + 11) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent(getUnlocalizedTooltipEnergy(), tile.getField(1)).getFormattedText());
                drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (mouseX >= guiLeft + 148 && mouseX <= guiLeft + 16 + 148) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent(getUnlocalizedTooltipLiquid(), tile.getField(2)).getFormattedText());
                drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (mouseX >= guiLeft + 79 && mouseX <= guiLeft + 24 + 79) {
            if (mouseY >= guiTop + 34 && mouseY <= guiTop + 17 + 34) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent(getUnlocalizedTooltipProgress(), ((int) (getProgress() * 100F))).getFormattedText());
                drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

    }

    public void drawEnergy() {
        float perc = getEnergy();

        int texX = 176;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 11;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawFluid() {
        float perc = getFluid();

        int texX = 192;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 148;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawProgress() {
        float perc = getProgress();

        int texX = 176;
        int texY = 0;
        int texW = 24;
        int texH = 17;
        int targetX = 79;
        int targetY = 34;

        int scWidth = (int) (texW * perc);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + targetX, j + targetY, texX, texY, scWidth, texH);
    }

    public float getEnergy() {
        return ((float) tile.getField(1)) / ((float) tile.getMaxStorage());
    }

    public float getFluid() {
        return ((float) tile.getField(2)) / ((float) tile.getMaxMillibuckets());
    }

    public float getProgress() {
        if (tile.getField(0) == 0) {
            return 0;
        }

        int time = tile.getGeneratingTime() - tile.getField(0);
        return ((float) time) / ((float) tile.getGeneratingTime());
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(getGuiTexture());
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i, j, 0, 0, this.xSize, this.ySize);

        drawEnergy();
        drawFluid();
        drawProgress();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
