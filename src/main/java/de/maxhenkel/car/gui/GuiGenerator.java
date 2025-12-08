package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;

import java.util.ArrayList;
import java.util.List;

public class GuiGenerator extends ScreenBase<ContainerGenerator> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_generator.png");

    private Inventory playerInv;
    private TileEntityGenerator tile;

    public GuiGenerator(ContainerGenerator containerGenerator, Inventory playerInv, Component title) {
        super(GUI_TEXTURE, containerGenerator, playerInv, title);
        this.playerInv = playerInv;
        this.tile = containerGenerator.getGenerator();

        imageWidth = 176;
        imageHeight = 166;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        // Title
        guiGraphics.drawString(font, playerInv.getDisplayName().getVisualOrderText(), 8, this.imageHeight - 96 + 2, FONT_COLOR, false);
        guiGraphics.drawString(font, tile.getDisplayName().getVisualOrderText(), 62, 6, FONT_COLOR, false);

        if (mouseX >= leftPos + 122 && mouseX <= leftPos + 16 + 122) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.energy", tile.getStoredEnergy()).getVisualOrderText());
                guiGraphics.setTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        }

        if (mouseX >= leftPos + 39 && mouseX <= leftPos + 16 + 39) {
            if (mouseY >= topPos + 8 && mouseY <= topPos + 57 + 8) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.fuel", tile.getCurrentMillibuckets()).getVisualOrderText());
                guiGraphics.setTooltipForNextFrame(font, list, mouseX, mouseY);
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        drawEnergy(guiGraphics);
        drawFluid(guiGraphics);
    }

    public void drawEnergy(GuiGraphics guiGraphics) {
        float perc = getEnergy();

        int texX = 176;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 122;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight, 256, 256);
    }

    public void drawFluid(GuiGraphics guiGraphics) {
        float perc = getFluid();

        int texX = 192;
        int texY = 17;
        int texW = 16;
        int texH = 57;
        int targetX = 39;
        int targetY = 8;

        int scHeight = (int) (texH * (1 - perc));
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED, texture, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight, 256, 256);
    }

    public float getEnergy() {
        return ((float) tile.getStoredEnergy()) / ((float) tile.maxStorage);
    }

    public float getFluid() {
        return ((float) tile.getCurrentMillibuckets()) / ((float) tile.maxMillibuckets);
    }

}
