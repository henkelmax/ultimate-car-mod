package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiGenerator extends GuiBase<ContainerGenerator> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_generator.png");
    private static final int fontColor = 4210752;

    private PlayerInventory playerInv;
    private TileEntityGenerator tile;

    public GuiGenerator(ContainerGenerator containerGenerator, PlayerInventory playerInv, ITextComponent title) {
        super(GUI_TEXTURE, containerGenerator, playerInv, title);
        this.playerInv = playerInv;
        this.tile = containerGenerator.getGenerator();

        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Title
        font.drawString(playerInv.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2,
                fontColor);
        font.drawString(tile.getDisplayName().getFormattedText(), 62, 6, fontColor);

        if (mouseX >= guiLeft + 122 && mouseX <= guiLeft + 16 + 122) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.energy", tile.getStoredEnergy())
                        .getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (mouseX >= guiLeft + 39 && mouseX <= guiLeft + 16 + 39) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.fuel", tile.getCurrentMillibuckets())
                        .getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        drawEnergy();
        drawFluid();
    }

    public void drawEnergy() {
        float perc = getEnergy();

        int texX = 176;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 122;
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
        int targetX = 39;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public float getEnergy() {
        return ((float) tile.getStoredEnergy()) / ((float) tile.maxStorage);
    }

    public float getFluid() {
        return ((float) tile.getCurrentMillibuckets()) / ((float) tile.maxMillibuckets);
    }

}
