package de.maxhenkel.car.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.car.net.MessageSpawnCar;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.Util;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

public class GuiCarWorkshopCrafting extends ScreenBase<ContainerCarWorkshopCrafting> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car_workshop_crafting.png");

    private TileEntityCarWorkshop tile;
    private Player player;

    private Button buttonSpawn;
    private Button buttonRepair;

    private EntityTools.CarRenderer carRenderer;

    public GuiCarWorkshopCrafting(ContainerCarWorkshopCrafting container, Inventory playerInventory, Component title) {
        super(GUI_TEXTURE, container, playerInventory, title);
        this.player = playerInventory.player;
        this.tile = container.getTile();
        this.carRenderer = new EntityTools.CarRenderer();

        imageWidth = 176;
        imageHeight = 222;
    }

    @Override
    protected void init() {
        super.init();

        buttonRepair = addRenderableWidget(new Button(leftPos + 105, topPos + 72, 60, 20, new TranslatableComponent("button.car.repair_car"), button -> {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenCarWorkshopGui(tile.getBlockPos(), player, true));
        }));

        buttonSpawn = addRenderableWidget(new Button(leftPos + 105, topPos + 106, 60, 20, new TranslatableComponent("button.car.spawn_car"), button -> {
            if (tile.getLevel().isClientSide) {
                if (tile.isCurrentCraftingCarValid()) {
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageSpawnCar(tile.getBlockPos()));
                } else {
                    for (Component message : tile.getMessages()) {
                        player.sendMessage(message, Util.NIL_UUID);
                    }
                }
            }
        }));
        buttonSpawn.active = false;
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        // Titles
        font.draw(matrixStack, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR);
        font.draw(matrixStack, playerInventoryTitle.getVisualOrderText(), 8, imageHeight - 96 + 2, FONT_COLOR);

        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        EntityCarBase carTop = tile.getCarOnTop();
        EntityGenericCar car = tile.getCurrentCraftingCar();

        if (carTop != null) {
            drawCar(matrixStack, carTop);
            buttonSpawn.active = false;
        } else {
            if (car != null) {
                drawCar(matrixStack, car);
            }
            buttonSpawn.active = true;
        }
    }

    @Override
    public void containerTick() {
        super.containerTick();
        carRenderer.tick();
    }

    private void drawCar(PoseStack matrixStack, EntityCarBase car) {
        carRenderer.render(matrixStack, car, imageWidth / 2, 55, 23);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
