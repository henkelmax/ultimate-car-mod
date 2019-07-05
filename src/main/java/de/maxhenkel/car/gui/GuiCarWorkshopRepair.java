package de.maxhenkel.car.gui;

import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.net.MessageRepairCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class GuiCarWorkshopRepair extends GuiBase<ContainerCarWorkshopRepair> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
            "textures/gui/gui_car_workshop_repair.png");

    private static final int fontColor = 4210752;

    private PlayerEntity player;
    private TileEntityCarWorkshop tile;
    private float rotoation;

    private Button buttonBack;
    private Button buttonRepair;

    public GuiCarWorkshopRepair(ContainerCarWorkshopRepair container, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, container, playerInventory, title);
        this.player = container.getPlayer();
        this.tile = container.getTile();

        xSize = 176;
        ySize = 222;
    }

    @Override
    protected void init() {
        super.init();

        this.buttonRepair = addButton(new Button(guiLeft + xSize - 7 - 60, guiTop + 105, 60, 20, new TranslationTextComponent("button.repair_car").getFormattedText(), button -> {
            if (tile.getWorld().isRemote) {
                Main.SIMPLE_CHANNEL.sendToServer(new MessageRepairCar(tile.getPos(), player));
            }
        }));
        this.buttonRepair.active = false;

        this.buttonBack = addButton(new Button(guiLeft + 7, guiTop + 105, 60, 20, new TranslationTextComponent("button.back").getFormattedText(), button -> {
            if (tile.getWorld().isRemote) {
                Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenCarWorkshopGui(tile.getPos(), player, false));
            }
        }));
        this.buttonBack.active = true;
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        // Titles
        font.drawString(tile.getDisplayName().getFormattedText(), 8, 6, fontColor);
        font.drawString(player.inventory.getDisplayName().getFormattedText(), 8, ySize - 96 + 2,
                fontColor);

        EntityCarBase carTop = tile.getCarOnTop();

        if (!(carTop instanceof EntityCarDamageBase)) {
            buttonRepair.active = false;
            return;
        }

        EntityCarDamageBase car = (EntityCarDamageBase) carTop;

        if (mouseX >= guiLeft + 52 && mouseX <= guiLeft + 123) {
            if (mouseY >= guiTop + 81 && mouseY <= guiTop + 90) {
                List<String> list = new ArrayList<String>();
                list.add(new TranslationTextComponent("tooltip.damage", MathTools.round(car.getDamage(), 2))
                        .getFormattedText());
                renderTooltip(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }

        if (tile.areRepairItemsInside() && car.getDamage() > 0) {
            buttonRepair.active = true;
        } else {
            buttonRepair.active = false;
        }
        drawCar(carTop);

    }

    private void drawCar(EntityCarBase car) {
        GlStateManager.color4f(1.0F, 1.0F, 1.0F, 1.0F);
        MathTools.drawCarOnScreen(xSize / 2, 55, 23, rotoation, car);
        float parts = Minecraft.getInstance().getRenderPartialTicks();
        rotoation += parts / 4;
        if (!(rotoation < 360)) {
            rotoation = 0F;
        }

    }

    public double getDamagePercent(EntityCarDamageBase car) {
        float dmg = car.getDamage();
        dmg = Math.min(dmg, 100);
        return MathTools.round(dmg, 2);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);

        drawDamage();
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    public void drawDamage() {
        EntityCarBase car = tile.getCarOnTop();
        if (!(car instanceof EntityCarDamageBase)) {
            return;
        }

        EntityCarDamageBase c = (EntityCarDamageBase) car;

        double percent = 100 - getDamagePercent(c);

        minecraft.getTextureManager().bindTexture(GUI_TEXTURE);
        int scaled = (int) (72 * percent / 100);
        int i = this.guiLeft;
        int j = this.guiTop;
        blit(i + 52, j + 81, 176, 0, scaled, 10);
    }

}
