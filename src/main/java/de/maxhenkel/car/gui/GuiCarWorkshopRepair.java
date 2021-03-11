package de.maxhenkel.car.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.car.net.MessageRepairCar;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.corelib.math.MathUtils;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class GuiCarWorkshopRepair extends ScreenBase<ContainerCarWorkshopRepair> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car_workshop_repair.png");

    private PlayerEntity player;
    private TileEntityCarWorkshop tile;

    private Button buttonBack;
    private Button buttonRepair;

    private EntityTools.CarRenderer carRenderer;

    public GuiCarWorkshopRepair(ContainerCarWorkshopRepair container, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, container, playerInventory, title);
        this.player = container.getPlayer();
        this.tile = container.getTile();
        this.carRenderer = new EntityTools.CarRenderer();

        imageWidth = 176;
        imageHeight = 222;
    }

    @Override
    protected void init() {
        super.init();

        this.buttonRepair = addButton(new Button(leftPos + imageWidth - 7 - 60, topPos + 105, 60, 20, new TranslationTextComponent("button.car.repair_car"), button -> {
            if (tile.getLevel().isClientSide) {
                Main.SIMPLE_CHANNEL.sendToServer(new MessageRepairCar(tile.getBlockPos(), player));
            }
        }));
        this.buttonRepair.active = false;

        this.buttonBack = addButton(new Button(leftPos + 7, topPos + 105, 60, 20, new TranslationTextComponent("button.car.back"), button -> {
            if (tile.getLevel().isClientSide) {
                Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenCarWorkshopGui(tile.getBlockPos(), player, false));
            }
        }));
        this.buttonBack.active = true;
    }

    @Override
    protected void renderLabels(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        // Titles
        font.draw(matrixStack, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR);
        font.draw(matrixStack, player.inventory.getDisplayName().getVisualOrderText(), 8, imageHeight - 96 + 2, FONT_COLOR);

        EntityCarBase carTop = tile.getCarOnTop();

        if (!(carTop instanceof EntityCarDamageBase)) {
            buttonRepair.active = false;
            return;
        }

        EntityCarDamageBase car = (EntityCarDamageBase) carTop;

        if (mouseX >= leftPos + 52 && mouseX <= leftPos + 123) {
            if (mouseY >= topPos + 81 && mouseY <= topPos + 90) {
                List<IReorderingProcessor> list = new ArrayList<>();
                list.add(new TranslationTextComponent("tooltip.damage", MathUtils.round(car.getDamage(), 2)).getVisualOrderText());
                renderTooltip(matrixStack, list, mouseX - leftPos, mouseY - topPos);
            }
        }

        if (tile.areRepairItemsInside() && car.getDamage() > 0) {
            buttonRepair.active = true;
        } else {
            buttonRepair.active = false;
        }
        drawCar(matrixStack, carTop);
    }

    @Override
    public void tick() {
        super.tick();
        carRenderer.tick();
    }

    private void drawCar(MatrixStack matrixStack, EntityCarBase car) {
        carRenderer.render(matrixStack, car, imageWidth / 2, 55, 23);
    }

    public double getDamagePercent(EntityCarDamageBase car) {
        float dmg = car.getDamage();
        dmg = Math.min(dmg, 100);
        return MathUtils.round(dmg, 2);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        drawDamage(matrixStack);
    }

    public void drawDamage(MatrixStack matrixStack) {
        EntityCarBase car = tile.getCarOnTop();
        if (!(car instanceof EntityCarDamageBase)) {
            return;
        }

        EntityCarDamageBase c = (EntityCarDamageBase) car;

        double percent = 100 - getDamagePercent(c);

        minecraft.getTextureManager().bind(GUI_TEXTURE);
        int scaled = (int) (72 * percent / 100);
        int i = this.leftPos;
        int j = this.topPos;
        blit(matrixStack, i + 52, j + 81, 176, 0, scaled, 10);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}