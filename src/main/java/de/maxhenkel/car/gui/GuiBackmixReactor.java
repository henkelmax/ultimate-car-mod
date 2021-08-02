package de.maxhenkel.car.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiBackmixReactor extends ScreenBase<ContainerBackmixReactor> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_backmix_reactor.png");

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
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        // Title
        font.draw(matrixStack, playerInventoryTitle.getVisualOrderText(), 8, this.imageHeight - 96 + 2, FONT_COLOR);

        if (mouseX >= leftPos + 11 && mouseX <= leftPos + 16 + 11) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(new TranslatableComponent("tooltip.energy", tile.getStoredEnergy()).getVisualOrderText());
                renderTooltip(matrixStack, list, mouseX - leftPos, mouseY - topPos);
            }
        }

        if (mouseX >= leftPos + 33 && mouseX <= leftPos + 16 + 33) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(new TranslatableComponent("tooltip.oil", tile.getCurrentCanola()).getVisualOrderText());
                renderTooltip(matrixStack, list, mouseX - leftPos, mouseY - topPos);
            }
        }

        if (mouseX >= leftPos + 55 && mouseX <= leftPos + 16 + 55) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(new TranslatableComponent("tooltip.methanol", tile.getCurrentMethanol()).getVisualOrderText());
                renderTooltip(matrixStack, list, mouseX - leftPos, mouseY - topPos);
            }
        }

        if (mouseX >= leftPos + 122 && mouseX <= leftPos + 16 + 122) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(new TranslatableComponent("tooltip.mix", tile.getCurrentMix()).getVisualOrderText());
                renderTooltip(matrixStack, list, mouseX - leftPos, mouseY - topPos);
            }
        }

        if (mouseX >= leftPos + 79 && mouseX <= leftPos + 24 + 79) {
            if (mouseY >= topPos + 34 && mouseY <= topPos + 17 + 34) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(new TranslatableComponent("tooltip.progress", ((int) (getProgress() * 100F))).getVisualOrderText());
                renderTooltip(matrixStack, list, mouseX - leftPos, mouseY - topPos);
            }
        }
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);

        drawProgress(matrixStack);
        drawEnergy(matrixStack);
        drawCanola(matrixStack);
        drawMethanol(matrixStack);
        drawMix(matrixStack);
    }

    public void drawEnergy(PoseStack matrixStack) {
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
        blit(matrixStack, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawCanola(PoseStack matrixStack) {
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
        blit(matrixStack, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawMethanol(PoseStack matrixStack) {
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
        blit(matrixStack, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawMix(PoseStack matrixStack) {
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
        blit(matrixStack, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawProgress(PoseStack matrixStack) {
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
        blit(matrixStack, i + targetX, j + targetY, texX, texY, scWidth, texH);
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
