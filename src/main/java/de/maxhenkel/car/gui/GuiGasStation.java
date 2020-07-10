package de.maxhenkel.car.gui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.tools.FluidUtils;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class GuiGasStation extends GuiBase<ContainerGasStation> {

    private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_gas_station.png");

    private TileEntityGasStation gasStation;
    private PlayerInventory playerInventory;

    private static final int TITLE_COLOR = Color.WHITE.getRGB();
    private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();

    protected Button buttonStart;
    protected Button buttonStop;

    public GuiGasStation(ContainerGasStation gasStation, PlayerInventory playerInventory, ITextComponent title) {
        super(GUI_TEXTURE, gasStation, playerInventory, title);
        this.gasStation = gasStation.getGasStation();
        this.playerInventory = playerInventory;

        xSize = 176;
        ySize = 217;
    }

    @Override
    protected void func_231160_c_() {
        super.func_231160_c_();

        buttonStart = func_230480_a_(new Button((field_230708_k_ / 2) - 20, guiTop + 100, 40, 20, new TranslationTextComponent("button.car.start"), button -> {
            gasStation.setFueling(true);
            gasStation.sendStartFuelPacket(true);
        }));
        buttonStop = func_230480_a_(new Button(guiLeft + xSize - 40 - 7, guiTop + 100, 40, 20, new TranslationTextComponent("button.car.stop"), button -> {
            gasStation.setFueling(false);
            gasStation.sendStartFuelPacket(false);
        }));
    }

    @Override
    protected void func_230450_a_(MatrixStack matrixStack, float partialTicks, int mouseX, int mouseY) {
        super.func_230450_a_(matrixStack, partialTicks, mouseX, mouseY);
        // buttons
        buttonStart.field_230693_o_ = !gasStation.isFueling();
        buttonStop.field_230693_o_ = gasStation.isFueling();

        // text
        func_238471_a_(matrixStack, field_230712_o_, new TranslationTextComponent("gui.gas_station").getString(), field_230708_k_ / 2, guiTop + 5, TITLE_COLOR);

        // Car name
        IFluidHandler fluidHandler = gasStation.getFluidHandlerInFront();

        if (fluidHandler instanceof Entity) {
            drawCarName(matrixStack, (Entity) fluidHandler);
        }


        drawCarFuel(matrixStack, fluidHandler);
        drawRefueled(matrixStack);
        drawBuffer(matrixStack);

        field_230712_o_.func_238422_b_(matrixStack, playerInventory.getDisplayName(), guiLeft + 8, guiTop + ySize - 93, FONT_COLOR);
    }

    @Override
    protected void func_230451_b_(MatrixStack matrixStack, int mouseX, int mouseY) {
        super.func_230451_b_(matrixStack, mouseX, mouseY);

        ItemStack stack = gasStation.getTradingInventory().getStackInSlot(0);

        if (stack.isEmpty()) {
            return;
        }

        if (mouseX >= guiLeft + 18 && mouseX <= guiLeft + 33) {
            if (mouseY >= guiTop + 99 && mouseY <= guiTop + 114) {
                List<IFormattableTextComponent> list = new ArrayList<>();
                list.add(new TranslationTextComponent("tooltip.trade", stack.getCount(), stack.getDisplayName(), gasStation.getTradeAmount()));
                func_238654_b_(matrixStack, list, mouseX - guiLeft, mouseY - guiTop);
            }
        }
    }

    private void drawCarName(MatrixStack matrixStack, Entity entity) {
        String name;
        if (entity instanceof EntityGenericCar) {
            name = ((EntityGenericCar) entity).getShortName().getString();
        } else {
            name = entity.getDisplayName().getString();
        }
        field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("gas_station.car_info", new StringTextComponent(name).func_240699_a_(TextFormatting.WHITE)), guiLeft + 63, guiTop + 20, FONT_COLOR);
    }

    private void drawCarFuel(MatrixStack matrixStack, IFluidHandler handler) {
        if (handler == null) {
            field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("gas_station.no_car"), guiLeft + 63, guiTop + 30, FONT_COLOR);
            return;
        }
        if (handler.getTanks() <= 0) {
            field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("gas_station.fuel_empty"), guiLeft + 63, guiTop + 30, FONT_COLOR);
            return;
        }
        FluidStack tank = handler.getFluidInTank(0);
        TextComponent fuelText = new TranslationTextComponent("gas_station.car_fuel_amount",
                new StringTextComponent(String.valueOf(tank.getAmount())).func_240699_a_(TextFormatting.WHITE),
                new StringTextComponent(String.valueOf(handler.getTankCapacity(0))).func_240699_a_(TextFormatting.WHITE)
        );
        field_230712_o_.func_238422_b_(matrixStack, fuelText, guiLeft + 63, guiTop + 30, FONT_COLOR);

        if (FluidUtils.isEmpty(tank)) {
            return;
        } else {
            field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("gas_station.car_fuel_type", new StringTextComponent(tank.getDisplayName().getString()).func_240699_a_(TextFormatting.WHITE)), guiLeft + 63, guiTop + 40, FONT_COLOR);
        }
    }

    private void drawRefueled(MatrixStack matrixStack) {
        field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("gas_station.refueled", new StringTextComponent(String.valueOf(gasStation.getFuelCounter())).func_240699_a_(TextFormatting.WHITE)), guiLeft + 63, guiTop + 60, FONT_COLOR);
    }

    private void drawBuffer(MatrixStack matrixStack) {
        FluidStack stack = gasStation.getStorage();

        if (FluidUtils.isEmpty(stack)) {
            field_230712_o_.func_238422_b_(matrixStack, new TranslationTextComponent("gas_station.fuel_empty"), guiLeft + 63, guiTop + 70, FONT_COLOR);
            return;
        }

        int amount = gasStation.getFuelAmount();

        TextComponent amountText = new TranslationTextComponent("gas_station.fuel_buffer_amount",
                new StringTextComponent(String.valueOf(amount)).func_240699_a_(TextFormatting.WHITE),
                new StringTextComponent(String.valueOf(gasStation.maxStorageAmount)).func_240699_a_(TextFormatting.WHITE)
        );

        field_230712_o_.func_238422_b_(matrixStack, amountText, guiLeft + 63, guiTop + 70, FONT_COLOR);

        TextComponent bufferText = new TranslationTextComponent("gas_station.fuel_buffer_type",
                new StringTextComponent(stack.getDisplayName().getString()).func_240699_a_(TextFormatting.WHITE)
        );

        field_230712_o_.func_238422_b_(matrixStack, bufferText, guiLeft + 63, guiTop + 80, FONT_COLOR);
    }

    @Override
    public boolean func_231177_au__() {
        return false;
    }
}
