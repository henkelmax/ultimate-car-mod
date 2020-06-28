package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiPainter extends GuiBase<ContainerPainter> {

    private static final ResourceLocation PAINTER_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_painter.png");

    public GuiPainter(ContainerPainter containerPainter, PlayerInventory playerInventory, ITextComponent title) {
        super(PAINTER_GUI_TEXTURE, containerPainter, playerInventory, title);
        xSize = 176;
        ySize = 96;
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);
        field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("gui.painter"), 8, 6, FONT_COLOR);
    }

    @Override
    public boolean func_231177_au__() {
        return false;
    }
}
