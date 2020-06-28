package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public abstract class GuiBase<T extends Container> extends ContainerScreen<T> {

    protected static final int FONT_COLOR = 4210752;

    protected ResourceLocation texture;

    public GuiBase(ResourceLocation texture, T inventorySlotsIn, PlayerInventory playerInventory, ITextComponent name) {
        super(inventorySlotsIn, playerInventory, name);
        this.texture = texture;
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        func_230446_a_(matrixStack);
        RenderSystem.color4f(1F, 1F, 1F, 1F);
        field_230706_i_.getTextureManager().bindTexture(texture);

        func_238474_b_(matrixStack, guiLeft, guiTop, 0, 0, xSize, ySize);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {

    }
}
