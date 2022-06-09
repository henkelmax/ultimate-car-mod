package de.maxhenkel.car.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.car.net.MessageRepairCar;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.corelib.math.MathUtils;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;
import java.util.List;

public class GuiCarWorkshopRepair extends ScreenBase<ContainerCarWorkshopRepair> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car_workshop_repair.png");

    private Player player;
    private TileEntityCarWorkshop tile;

    private Button buttonBack;
    private Button buttonRepair;

    private EntityTools.CarRenderer carRenderer;

    public GuiCarWorkshopRepair(ContainerCarWorkshopRepair container, Inventory playerInventory, Component title) {
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

        this.buttonRepair = addRenderableWidget(new Button(leftPos + imageWidth - 7 - 60, topPos + 105, 60, 20, Component.translatable("button.car.repair_car"), button -> {
            if (tile.getLevel().isClientSide) {
                Main.SIMPLE_CHANNEL.sendToServer(new MessageRepairCar(tile.getBlockPos(), player));
            }
        }));
        this.buttonRepair.active = false;

        this.buttonBack = addRenderableWidget(new Button(leftPos + 7, topPos + 105, 60, 20, Component.translatable("button.car.back"), button -> {
            if (tile.getLevel().isClientSide) {
                Main.SIMPLE_CHANNEL.sendToServer(new MessageOpenCarWorkshopGui(tile.getBlockPos(), player, false));
            }
        }));
        this.buttonBack.active = true;
    }

    @Override
    protected void renderLabels(PoseStack matrixStack, int mouseX, int mouseY) {
        super.renderLabels(matrixStack, mouseX, mouseY);

        // Titles
        font.draw(matrixStack, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR);
        font.draw(matrixStack, player.getInventory().getDisplayName().getVisualOrderText(), 8, imageHeight - 96 + 2, FONT_COLOR);

        EntityCarBase carTop = tile.getCarOnTop();

        if (!(carTop instanceof EntityCarDamageBase)) {
            buttonRepair.active = false;
            return;
        }

        EntityCarDamageBase car = (EntityCarDamageBase) carTop;

        if (mouseX >= leftPos + 52 && mouseX <= leftPos + 123) {
            if (mouseY >= topPos + 81 && mouseY <= topPos + 90) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.damage", MathUtils.round(car.getDamage(), 2)).getVisualOrderText());
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
    public void containerTick() {
        super.containerTick();
        carRenderer.tick();
    }

    private void drawCar(PoseStack matrixStack, EntityCarBase car) {
        carRenderer.render(matrixStack, car, imageWidth / 2, 55, 23);
    }

    public double getDamagePercent(EntityCarDamageBase car) {
        float dmg = car.getDamage();
        dmg = Math.min(dmg, 100);
        return MathUtils.round(dmg, 2);
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(matrixStack, partialTicks, mouseX, mouseY);
        drawDamage(matrixStack);
    }

    public void drawDamage(PoseStack matrixStack) {
        EntityCarBase car = tile.getCarOnTop();
        if (!(car instanceof EntityCarDamageBase)) {
            return;
        }

        EntityCarDamageBase c = (EntityCarDamageBase) car;

        double percent = 100 - getDamagePercent(c);

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        RenderSystem.setShaderTexture(0, GUI_TEXTURE);
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