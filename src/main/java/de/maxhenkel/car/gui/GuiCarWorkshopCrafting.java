package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.car.net.MessageSpawnCar;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiCarWorkshopCrafting extends GuiBase<ContainerCarWorkshopCrafting> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car_workshop_crafting.png");

    private TileEntityCarWorkshop tile;
    private PlayerEntity player;

    private Button buttonSpawn;
    private Button buttonRepair;

    private EntityTools.CarRenderer carRenderer;

    public GuiCarWorkshopCrafting(ContainerCarWorkshopCrafting container, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, container, playerInventory, title);
        this.player = playerInventory.player;
        this.tile = container.getTile();
        this.carRenderer = new EntityTools.CarRenderer();

        xSize = 176;
        ySize = 222;
    }

    @Override
    protected void func_231160_c_() {
        super.func_231160_c_();

        buttonRepair = func_230480_a_(new Button(guiLeft + 105, guiTop + 72, 60, 20, new TranslationTextComponent("button.car.repair_car"), button -> {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenCarWorkshopGui(tile.getPos(), player, true));
        }));

        buttonSpawn = func_230480_a_(new Button(guiLeft + 105, guiTop + 106, 60, 20, new TranslationTextComponent("button.car.spawn_car"), button -> {
            if (tile.getWorld().isRemote) {
                if (tile.isCurrentCraftingCarValid()) {
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageSpawnCar(tile.getPos()));
                } else {
                    for (ITextComponent message : tile.getMessages()) {
                        playerInventory.player.sendMessage(message, Util.field_240973_b_);
                    }
                }
            }
        }));
        buttonSpawn.field_230693_o_ = false;
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);

        // Titles
        field_230712_o_.func_238422_b_(matrixStack, tile.getDisplayName(), 8, 6, FONT_COLOR);
        field_230712_o_.func_238422_b_(matrixStack, playerInventory.getDisplayName(), 8, ySize - 96 + 2, FONT_COLOR);

        RenderSystem.color4f(1F, 1F, 1F, 1F);

        EntityCarBase carTop = tile.getCarOnTop();
        EntityGenericCar car = tile.getCurrentCraftingCar();

        if (carTop != null) {
            drawCar(matrixStack, carTop);
            buttonSpawn.field_230693_o_ = false;
        } else {
            if (car != null) {
                drawCar(matrixStack, car);
            }
            buttonSpawn.field_230693_o_ = true;
        }
    }

    @Override
    public void func_231023_e_() {
        super.func_231023_e_();
        carRenderer.tick();
    }

    private void drawCar(MatrixStack matrixStack, EntityCarBase car) {
        carRenderer.render(matrixStack, car, xSize / 2, 55, 23);
    }

    @Override
    public boolean func_231177_au__() {
        return false;
    }

}
