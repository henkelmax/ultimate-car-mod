package de.maxhenkel.car.gui;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.tools.EntityTools;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.net.MessageSpawnCar;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiCarWorkshopCrafting extends GuiBase<ContainerCarWorkshopCrafting> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_car_workshop_crafting.png");

    private static final int fontColor = 4210752;

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
    protected void init() {
        super.init();

        buttonRepair = addButton(new Button(guiLeft + 105, guiTop + 72, 60, 20, new TranslationTextComponent("button.repair_car").getFormattedText(), button -> {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenCarWorkshopGui(tile.getPos(), player, true));
        }));

        buttonSpawn = addButton(new Button(guiLeft + 105, guiTop + 106, 60, 20, new TranslationTextComponent("button.spawn_car").getFormattedText(), button -> {
            if (tile.getWorld().isRemote) {
                if (tile.isCurrentCraftingCarValid()) {
                    Main.SIMPLE_CHANNEL.sendToServer(new MessageSpawnCar(tile.getPos()));
                } else {
                    for (ITextComponent message : tile.getMessages()) {
                        playerInventory.player.sendMessage(message);
                    }
                }
            }
        }));
        buttonSpawn.active = false;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Titles
        font.drawString(tile.getDisplayName().getFormattedText(), 8, 6, fontColor);
        font.drawString(playerInventory.getDisplayName().getFormattedText(), 8, ySize - 96 + 2,
                fontColor);

        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);

        EntityCarBase carTop = tile.getCarOnTop();
        EntityGenericCar car = tile.getCurrentCraftingCar();

        if (carTop != null) {
            drawCar(carTop);
            buttonSpawn.active = false;
        } else {
            if (car != null) {
                drawCar(car);
            }
            buttonSpawn.active = true;
        }
    }

    @Override
    public void tick() {
        super.tick();
        carRenderer.tick();
    }

    private void drawCar(EntityCarBase car) {
        carRenderer.render(car, xSize / 2, 55, 23);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
