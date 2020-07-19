package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiGenerator extends ScreenBase<ContainerGenerator> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_generator.png");

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
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);

        // Title
        field_230712_o_.func_238422_b_(matrixStack, playerInv.getDisplayName(), 8, this.ySize - 96 + 2, FONT_COLOR);
        field_230712_o_.func_238422_b_(matrixStack, tile.getDisplayName(), 62, 6, FONT_COLOR);

        if (mouseX >= guiLeft + 122 && mouseX <= guiLeft + 16 + 122) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<IFormattableTextComponent> list = new ArrayList<>();
                list.add(new TranslationTextComponent("tooltip.energy", tile.getStoredEnergy()));
                func_238654_b_(matrixStack, list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (mouseX >= guiLeft + 39 && mouseX <= guiLeft + 16 + 39) {
            if (mouseY >= guiTop + 8 && mouseY <= guiTop + 57 + 8) {
                List<IFormattableTextComponent> list = new ArrayList<>();
                list.add(new TranslationTextComponent("tooltip.fuel", tile.getCurrentMillibuckets()));
                func_238654_b_(matrixStack, list, mouseX - guiLeft, mouseY - guiTop);
            }
        }
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.func_230450_a_(matrixStack, partialTicks, mouseX, mouseY);
        drawEnergy(matrixStack);
        drawFluid(matrixStack);
    }

    public void drawEnergy(MatrixStack matrixStack) {
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
        func_238474_b_(matrixStack, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public void drawFluid(MatrixStack matrixStack) {
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
        func_238474_b_(matrixStack, i + targetX, j + targetY + scHeight, texX, texY + scHeight, texW, texH - scHeight);
    }

    public float getEnergy() {
        return ((float) tile.getStoredEnergy()) / ((float) tile.maxStorage);
    }

    public float getFluid() {
        return ((float) tile.getCurrentMillibuckets()) / ((float) tile.maxMillibuckets);
    }

}
