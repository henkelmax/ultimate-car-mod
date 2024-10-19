package de.maxhenkel.car.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.net.MessageOpenCarWorkshopGui;
import de.maxhenkel.car.net.MessageRepairCar;
import de.maxhenkel.corelib.inventory.ScreenBase;
import de.maxhenkel.corelib.math.MathUtils;
import de.maxhenkel.tools.EntityTools;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class GuiCarWorkshopRepair extends ScreenBase<ContainerCarWorkshopRepair> {

    private static final ResourceLocation GUI_TEXTURE = ResourceLocation.fromNamespaceAndPath(Main.MODID, "textures/gui/gui_car_workshop_repair.png");

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

        this.buttonRepair = addRenderableWidget(Button.builder(Component.translatable("button.car.repair_car"), button -> {
            if (tile.getLevel().isClientSide) {
                PacketDistributor.sendToServer(new MessageRepairCar(tile.getBlockPos(), player));
            }
        }).bounds(leftPos + imageWidth - 7 - 60, topPos + 105, 60, 20).build());
        this.buttonRepair.active = false;

        this.buttonBack = addRenderableWidget(Button.builder(Component.translatable("button.car.back"), button -> {
            if (tile.getLevel().isClientSide) {
                PacketDistributor.sendToServer(new MessageOpenCarWorkshopGui(tile.getBlockPos(), player, false));
            }
        }).bounds(leftPos + 7, topPos + 105, 60, 20).build());
        this.buttonBack.active = true;
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        // Titles
        guiGraphics.drawString(font, tile.getDisplayName().getVisualOrderText(), 8, 6, FONT_COLOR, false);
        guiGraphics.drawString(font, player.getInventory().getDisplayName().getVisualOrderText(), 8, imageHeight - 96 + 2, FONT_COLOR, false);

        EntityCarBase carTop = tile.getCarOnTop();

        if (!(carTop instanceof EntityGenericCar car)) {
            buttonRepair.active = false;
            return;
        }

        if (mouseX >= leftPos + 52 && mouseX <= leftPos + 123) {
            if (mouseY >= topPos + 81 && mouseY <= topPos + 90) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.damage", MathUtils.round(car.getDamage(), 2)).getVisualOrderText());
                guiGraphics.renderTooltip(font, list, mouseX - leftPos, mouseY - topPos);
            }
        }

        if (tile.areRepairItemsInside() && car.getDamage() > 0) {
            buttonRepair.active = true;
        } else {
            buttonRepair.active = false;
        }
        drawCar(guiGraphics, car);
    }

    @Override
    public void containerTick() {
        super.containerTick();
        carRenderer.tick();
    }

    private void drawCar(GuiGraphics guiGraphics, EntityGenericCar car) {
        carRenderer.render(guiGraphics, car, imageWidth / 2, 55, 23);
    }

    public double getDamagePercent(EntityCarDamageBase car) {
        float dmg = car.getDamage();
        dmg = Math.min(dmg, 100);
        return MathUtils.round(dmg, 2);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        drawDamage(guiGraphics);
    }

    public void drawDamage(GuiGraphics guiGraphics) {
        EntityCarBase car = tile.getCarOnTop();
        if (!(car instanceof EntityCarDamageBase)) {
            return;
        }

        EntityCarDamageBase c = (EntityCarDamageBase) car;

        double percent = 100 - getDamagePercent(c);

        RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
        int scaled = (int) (72 * percent / 100);
        int i = this.leftPos;
        int j = this.topPos;
        guiGraphics.blit(RenderType::guiTextured, GUI_TEXTURE, i + 52, j + 81, 176, 0, scaled, 10, 256, 256);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}