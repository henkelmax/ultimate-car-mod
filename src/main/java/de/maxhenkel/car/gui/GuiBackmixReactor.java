package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiBackmixReactor extends ScreenBase<ContainerBackmixReactor> {

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_backmix_reactor.png");

    private Container playerInv;
    private TileEntityBackmixReactor tile;

    public GuiBackmixReactor(ContainerBackmixReactor container, Inventory playerInv, Component name) {
        super(GUI_TEXTURE, container, playerInv, name);
        this.playerInv = playerInv;
        this.tile = container.getBackmixReactor();

        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        // Title
        guiGraphics.drawString(font, playerInventoryTitle.getVisualOrderText(), 8, this.imageHeight - 96 + 2, FONT_COLOR, false);

        if (mouseX >= leftPos + 11 && mouseX <= leftPos + 16 + 11) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.energy", tile.getStoredEnergy()).getVisualOrderText());
                guiGraphics.setTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        }

        if (mouseX >= leftPos + 33 && mouseX <= leftPos + 16 + 33) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.oil", tile.getCurrentCanola()).getVisualOrderText());
                guiGraphics.setTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        }

        if (mouseX >= leftPos + 55 && mouseX <= leftPos + 16 + 55) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.methanol", tile.getCurrentMethanol()).getVisualOrderText());
                guiGraphics.setTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        }

        if (mouseX >= leftPos + 122 && mouseX <= leftPos + 16 + 122) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.mix", tile.getCurrentMix()).getVisualOrderText());
                guiGraphics.setTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        }

        if (mouseX >= leftPos + 79 && mouseX <= leftPos + 24 + 79) {
            if (mouseY >= topPos + 34 && mouseY <= topPos + 17 + 34) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.progress", ((int) (getProgress() * 100F))).getVisualOrderText());
                guiGraphics.setTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);

        drawProgress(guiGraphics);
        drawEnergy(guiGraphics);
        drawCanola(guiGraphics);
        drawMethanol(guiGraphics);
        drawMix(guiGraphics);
    }

    public void drawEnergy(GuiGraphics guiGraphics) {
        float perc = getEnergy();

        int texX = 176;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 11;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight, 256, 256);
    }

    public void drawCanola(GuiGraphics guiGraphics) {
        float perc = getCanola();

        int texX = 192;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 33;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight, 256, 256);
    }

    public void drawMethanol(GuiGraphics guiGraphics) {
        float perc = getMethanol();

        int texX = 208;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 55;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight, 256, 256);
    }

    public void drawMix(GuiGraphics guiGraphics) {
        float perc = getMix();

        int texX = 224;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 122;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight, 256, 256);
    }

    public void drawProgress(GuiGraphics guiGraphics) {
        float perc = getProgress();

        int texX = 176;
        int texY = 0;
        int texW = 24;
        int texH = 17;
        int targetX = 79;
        int targetY = 34;

        int scWidth = (int) (texW * perc);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, GUI_TEXTURE, i + targetX, j + targetY, texX, texY, scWidth, texH, 256, 256);
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
