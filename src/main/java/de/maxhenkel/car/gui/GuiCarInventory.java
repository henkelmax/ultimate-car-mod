package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

public class GuiCarInventory extends ScreenBase<ContainerCarInventory> {

    private static final ResourceLocation GUI_TEXTURE_3 = new ResourceLocation("textures/gui/container/shulker_box.png");
    private static final ResourceLocation GUI_TEXTURE_6 = new ResourceLocation("textures/gui/container/generic_54.png");

    private EntityCarInventoryBase car;
    private PlayerInventory playerInventory;

    public GuiCarInventory(ContainerCarInventory carInventory, PlayerInventory playerInventory, ITextComponent title) {
        super(carInventory.getRows() == 3 ? GUI_TEXTURE_3 : GUI_TEXTURE_6, carInventory, playerInventory, title);
        this.car = carInventory.getCar();
        this.playerInventory = playerInventory;
        xSize = 176;
        ySize = carInventory.getRows() == 3 ? 166 : 222;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(matrixStack, mouseX, mouseY);
        font.func_238422_b_(matrixStack, car.getDisplayName().func_241878_f(), 8, 6, FONT_COLOR);
        font.func_238422_b_(matrixStack, playerInventory.getDisplayName().func_241878_f(), 8, ySize - 96 + 3, FONT_COLOR);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
