package de.maxhenkel.car.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.car.net.MessageSpawnCar;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

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

        buttonRepair = addRenderableWidget(Button.builder(Component.translatable("button.car.repair_car"), button -> {
            PacketDistributor.sendToServer(new MessageOpenCarWorkshopGui(tile.getBlockPos(), player, true));
        }).bounds(leftPos + 105, topPos + 72, 60, 20).build());

        buttonSpawn = addRenderableWidget(Button.builder(Component.translatable("button.car.spawn_car"), button -> {
            if (tile.getLevel().isClientSide) {
                if (tile.isCurrentCraftingCarValid()) {
                    PacketDistributor.sendToServer(new MessageSpawnCar(tile.getBlockPos()));
                } else {
                    for (Component message : tile.getMessages()) {
                        player.sendSystemMessage(message);
                    }
                }
            }
        }).bounds(leftPos + 105, topPos + 106, 60, 20).build());
        buttonSpawn.active = false;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        // Titles
        guiGraphics.drawString(font, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR, false);
        guiGraphics.drawString(font, playerInventoryTitle.getVisualOrderText(), 8, imageHeight - 96 + 2, FONT_COLOR, false);

        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);

        EntityCarBase carTop = tile.getCarOnTop();
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

    private void drawCar(GuiGraphics guiGraphics, EntityCarBase car) {
        carRenderer.render(guiGraphics, car, imageWidth / 2, 55, 23);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
