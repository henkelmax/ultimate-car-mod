package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import net.minecraft.client.gui.IHasContainer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiBackmixReactor extends GuiBase<ContainerBackmixReactor> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_backmix_reactor.png");
    private static final int fontColor = 4210752;

    private IInventory playerInv;
    private TileEntityBackmixReactor tile;

    public GuiBackmixReactor(ContainerBackmixReactor container, PlayerInventory playerInv, ITextComponent name) {
        super(GUI_TEXTURE, container, playerInv, name);
        this.playerInv = playerInv;
        this.tile = container.getBackmixReactor();

        xSize = 176;
        ySize = 166;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Title
        font.drawString(playerInventory.getDisplayName().getFormattedText(), 8, this.ySize - 96 + 2,
                fontColor);

        if (mouseX >= guiLeft + 11 && mouseX <= guiLeft + 16 + 11) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.energy", tile.getStoredEnergy()).getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (mouseX >= guiLeft + 33 && mouseX <= guiLeft + 16 + 33) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.oil", tile.getCurrentCanola()).getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (mouseX >= guiLeft + 55 && mouseX <= guiLeft + 16 + 55) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.methanol", tile.getCurrentMethanol())
                        .getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (mouseX >= guiLeft + 122 && mouseX <= guiLeft + 16 + 122) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.mix", tile.getCurrentMix())
                        .getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (mouseX >= guiLeft + 79 && mouseX <= guiLeft + 24 + 79) {
            if (mouseY >= guiTop + 34 && mouseY <= guiTop + 17 + 34) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.progress", ((int) (getProgress() * 100F))).getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i, j, 0, 0, this.xSize, this.ySize);

        drawProgress();
        drawEnergy();
        drawCanola();
        drawMethanol();
        drawMix();
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

    public void drawCanola() {
        float perc = getCanola();

        int texX = 192;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 33;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawMethanol() {
        float perc = getMethanol();

        int texX = 208;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 55;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawMix() {
        float perc = getMix();

        int texX = 224;
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
        return ((float) tile.getStoredEnergy()) / ((float) tile.maxStorage);
    }

    public float getCanola() {
        return ((float) tile.getCurrentCanola()) / ((float) tile.maxCanola);
    }

    public float getMethanol() {
        return ((float) tile.getCurrentMethanol()) / ((float) tile.maxMethanol);
    }

    public float getMix() {
        return ((float) tile.getCurrentMix()) / ((float) tile.maxMix);
    }

    public float getProgress() {
        if (tile.getTimeToGenerate() == 0) {
            return 0;
        }

        int time = tile.generatingTime - tile.getTimeToGenerate();
        return ((float) time) / ((float) tile.generatingTime);
    }

}
