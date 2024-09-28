package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.inventory.ScreenBase;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GuiGasStation extends ScreenBase<ContainerGasStation> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_gas_station.png");

    private TileEntityGasStation gasStation;
    private Inventory playerInventory;

    private static final int TITLE_COLOR = Color.WHITE.getRGB();
    private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();

    protected Button buttonStart;
    protected Button buttonStop;

    public GuiGasStation(ContainerGasStation gasStation, Inventory playerInventory, Component title) {
        super(GUI_TEXTURE, gasStation, playerInventory, title);
        this.gasStation = gasStation.getGasStation();
        this.playerInventory = playerInventory;

        imageWidth = 176;
        imageHeight = 217;
    }

    @Override
    protected void init() {
        super.init();

        buttonStart = addRenderableWidget(Button.builder(Component.translatable("button.car.start"), button -> {
            gasStation.setFueling(true);
            gasStation.sendStartFuelPacket(true);
        }).bounds((width / 2) - 20, topPos + 100, 40, 20).build());
        buttonStop = addRenderableWidget(Button.builder(Component.translatable("button.car.stop"), button -> {
            gasStation.setFueling(false);
            gasStation.sendStartFuelPacket(false);
        }).bounds(leftPos + imageWidth - 40 - 7, topPos + 100, 40, 20).build());
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        super.renderBg(guiGraphics, partialTicks, mouseX, mouseY);
        // buttons
        buttonStart.active = !gasStation.isFueling();
        buttonStop.active = gasStation.isFueling();

        // text
        guiGraphics.drawCenteredString(font, Component.translatable("gui.gas_station").getString(), width / 2, topPos + 5, TITLE_COLOR);

        // Car name
        IFluidHandler fluidHandler = gasStation.getFluidHandlerInFront();

        if (fluidHandler instanceof Entity) {
            drawCarName(guiGraphics, (Entity) fluidHandler);
        }


        drawCarFuel(guiGraphics, fluidHandler);
        drawRefueled(guiGraphics);
        drawBuffer(guiGraphics);

        guiGraphics.drawString(font, playerInventory.getDisplayName().getVisualOrderText(), leftPos + 8, topPos + imageHeight - 93, FONT_COLOR, false);
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        super.renderLabels(guiGraphics, mouseX, mouseY);

        ItemStack stack = gasStation.getTradingInventory().getItem(0);

        if (stack.isEmpty()) {
            return;
        }

        if (mouseX >= leftPos + 18 && mouseX <= leftPos + 33) {
            if (mouseY >= topPos + 99 && mouseY <= topPos + 114) {
                List<FormattedCharSequence> list = new ArrayList<>();
                list.add(Component.translatable("tooltip.trade", stack.getCount(), stack.getHoverName(), gasStation.getTradeAmount()).getVisualOrderText());
                guiGraphics.renderTooltip(font, list, mouseX - leftPos, mouseY - topPos);
            }
        }
    }

    private void drawCarName(GuiGraphics guiGraphics, Entity entity) {
        String name;
        if (entity instanceof EntityGenericCar) {
            name = ((EntityGenericCar) entity).getShortName().getString();
        } else {
            name = entity.getDisplayName().getString();
        }
        guiGraphics.drawString(font, Component.translatable("gas_station.car_info", Component.literal(name).withStyle(ChatFormatting.WHITE)).getVisualOrderText(), leftPos + 63, topPos + 20, FONT_COLOR, false);
    }

    private void drawCarFuel(GuiGraphics guiGraphics, IFluidHandler handler) {
        if (handler == null) {
            guiGraphics.drawString(font, Component.translatable("gas_station.no_vehicle").getVisualOrderText(), leftPos + 63, topPos + 30, FONT_COLOR, false);
            return;
        }
        if (handler.getTanks() <= 0) {
            guiGraphics.drawString(font, Component.translatable("gas_station.fuel_empty").getVisualOrderText(), leftPos + 63, topPos + 30, FONT_COLOR, false);
            return;
        }
        FluidStack tank = handler.getFluidInTank(0);
        Component fuelText = Component.translatable("gas_station.car_fuel_amount",
                Component.literal(String.valueOf(tank.getAmount())).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(handler.getTankCapacity(0))).withStyle(ChatFormatting.WHITE)
        );
        guiGraphics.drawString(font, fuelText.getVisualOrderText(), leftPos + 63, topPos + 30, FONT_COLOR, false);

        if (!tank.isEmpty()) {
            guiGraphics.drawString(font, Component.translatable("gas_station.car_fuel_type", Component.literal(tank.getDisplayName().getString()).withStyle(ChatFormatting.WHITE)).getVisualOrderText(), leftPos + 63, topPos + 40, FONT_COLOR, false);
        }
    }

    private void drawRefueled(GuiGraphics guiGraphics) {
        guiGraphics.drawString(font, Component.translatable("gas_station.refueled", Component.literal(String.valueOf(gasStation.getFuelCounter())).withStyle(ChatFormatting.WHITE)).getVisualOrderText(), leftPos + 63, topPos + 60, FONT_COLOR, false);
    }

    private void drawBuffer(GuiGraphics guiGraphics) {
        FluidStack stack = gasStation.getStorage();

        if (stack.isEmpty()) {
            guiGraphics.drawString(font, Component.translatable("gas_station.fuel_empty").getVisualOrderText(), leftPos + 63, topPos + 70, FONT_COLOR, false);
            return;
        }

        int amount = gasStation.getFuelAmount();

        Component amountText = Component.translatable("gas_station.fuel_buffer_amount",
                Component.literal(String.valueOf(amount)).withStyle(ChatFormatting.WHITE),
                Component.literal(String.valueOf(gasStation.maxStorageAmount)).withStyle(ChatFormatting.WHITE)
        );

        guiGraphics.drawString(font, amountText.getVisualOrderText(), leftPos + 63, topPos + 70, FONT_COLOR, false);

        Component bufferText = Component.translatable("gas_station.fuel_buffer_type",
                Component.literal(stack.getDisplayName().getString()).withStyle(ChatFormatting.WHITE)
        );

        guiGraphics.drawString(font, bufferText.getVisualOrderText(), leftPos + 63, topPos + 80, FONT_COLOR, false);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

}
