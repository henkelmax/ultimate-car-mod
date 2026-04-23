package de.maxhenkel.car.gui;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.car.net.MessageSpawnCar;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;

public class GuiCarWorkshopCrafting extends ScreenBase<ContainerCarWorkshopCrafting> {

    private static final Identifier GUI_TEXTURE = Identifier.fromNamespaceAndPath(CarMod.MODID, "textures/gui/gui_car_workshop_crafting.png");

    private TileEntityCarWorkshop tile;
    private Player player;

    private Button buttonSpawn;
    private Button buttonRepair;

    private EntityTools.CarRenderer carRenderer;

    public GuiCarWorkshopCrafting(ContainerCarWorkshopCrafting container, Inventory playerInventory, Component title) {
        super(GUI_TEXTURE, container, playerInventory, title, 176, 222);
        this.player = playerInventory.player;
        this.tile = container.getTile();
        this.carRenderer = new EntityTools.CarRenderer();
    }

    @Override
    protected void init() {
        super.init();

        buttonRepair = addRenderableWidget(Button.builder(Component.translatable("button.car.repair_car"), button -> {
            ClientPacketDistributor.sendToServer(new MessageOpenCarWorkshopGui(tile.getBlockPos(), player, true));
        }).bounds(leftPos + 105, topPos + 72, 60, 20).build());

        buttonSpawn = addRenderableWidget(Button.builder(Component.translatable("button.car.spawn_car"), button -> {
            if (tile.getLevel().isClientSide()) {
                if (tile.isCurrentCraftingCarValid()) {
                    ClientPacketDistributor.sendToServer(new MessageSpawnCar(tile.getBlockPos()));
                } else {
                    for (Component message : tile.getMessages()) {
                        minecraft.gui.getChat().addClientSystemMessage(message);
                    }
                }
            }
        }).bounds(leftPos + 105, topPos + 106, 60, 20).build());
        buttonSpawn.active = false;
    }

    @Override
    public void extractLabels(GuiGraphicsExtractor guiGraphics, int mouseX, int mouseY) {
        super.extractLabels(guiGraphics, mouseX, mouseY);

        // Titles
        guiGraphics.text(font, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR, false);
        guiGraphics.text(font, playerInventoryTitle.getVisualOrderText(), 8, imageHeight - 96 + 2, FONT_COLOR, false);

        EntityGenericCar carTop = tile.getCarOnTop();
        EntityGenericCar car = tile.getCurrentCraftingCar();

        if (carTop != null) {
            drawCar(guiGraphics, carTop);
            buttonSpawn.active = false;
        } else {
            if (car != null) {
                drawCar(guiGraphics, car);
            }
            buttonSpawn.active = true;
        }
    }

    @Override
    public void containerTick() {
        super.containerTick();
        carRenderer.tick();
    }

    private void drawCar(GuiGraphicsExtractor guiGraphics, EntityGenericCar car) {
        carRenderer.render(guiGraphics, car, getLeftPos() + 50, getTopPos() + 16, getLeftPos() + 126, getTopPos() + 59, 23);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
