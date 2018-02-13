package de.maxhenkel.car.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.car.Main;
import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.entity.car.base.EntityCarFuelBase;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;

public class GuiFuelStation extends GuiBase {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/gui_fuelstation.png");

	private TileEntityFuelStation fuelStation;
    private IInventory inventoryPlayer;

	protected int xSize = 176;
	protected int ySize = 217;

	private static final int TITLE_COLOR = Color.WHITE.getRGB();
	private static final int FONT_COLOR = Color.DARK_GRAY.getRGB();
	private static final ChatFormatting INFO_COLOR = ChatFormatting.WHITE;

	protected int guiLeft;
	protected int guiTop;

	protected GuiButton buttonStart;
	protected GuiButton buttonStop;

	public GuiFuelStation(TileEntityFuelStation fuelStation, IInventory inventoryPlayer) {
		super(new ContainerFuelStation(fuelStation, inventoryPlayer));
		this.fuelStation = fuelStation;
        this.inventoryPlayer=inventoryPlayer;
	}

	@Override
	public void initGui() {
		super.initGui();

		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;

		this.buttonStart = addButton(new GuiButton(0, (width / 2) - 20, guiTop+100, 40, 20,
				new TextComponentTranslation("button.start").getFormattedText()));
		this.buttonStop = addButton(new GuiButton(1, guiLeft+xSize-40-7, guiTop+100, 40, 20,
				new TextComponentTranslation("button.stop").getFormattedText()));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		// Background
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		// buttons
		buttonStart.enabled = !fuelStation.isFueling();
		buttonStop.enabled = fuelStation.isFueling();

		// text
		drawCenteredString(fontRenderer, new TextComponentTranslation("gui.fuelstation").getFormattedText(),
				width / 2, guiTop + 5, TITLE_COLOR);

		// Car name
		EntityCarFuelBase car = fuelStation.getCarInFront();

		drawCarName(car);
		drawCarFuel(car);
		drawRefueled();
		drawBuffer();

        fontRenderer.drawString(inventoryPlayer.getDisplayName().getUnformattedText(), guiLeft+8, guiTop +ySize - 93, FONT_COLOR);
	}

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);

        ItemStack stack=fuelStation.getTradingInventory().getStackInSlot(0);

        if(ItemTools.isStackEmpty(stack)){
            return;
        }

        if (mouseX >= guiLeft + 18 && mouseX <= guiLeft + 33) {
            if (mouseY >= guiTop + 99 && mouseY <= guiTop + 114) {
                List<String> list = new ArrayList<String>();
                list.add(new TextComponentTranslation("tooltip.trade", stack.getCount(), stack.getDisplayName(), fuelStation.getField(2))
                        .getFormattedText());
                drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
            }
        }
    }

    private void drawCarName(EntityCarFuelBase car) {
		String carText;
		if (car == null) {
			carText = new TextComponentTranslation("fuelstation.nocar").getFormattedText();
		} else {
			carText = new TextComponentTranslation("fuelstation.carinfo",
					INFO_COLOR + car.getCarName().getFormattedText()).getFormattedText();
		}

		fontRenderer.drawString(carText, guiLeft+63, guiTop + 20, FONT_COLOR);
	}

	private void drawCarFuel(EntityCarFuelBase car) {
		if (car == null) {
			String empty = new TextComponentTranslation("fuelstation.fuel_empty").getFormattedText();
			fontRenderer.drawString(empty, guiLeft+63, guiTop + 30, FONT_COLOR);
			return;
		} else {
			String fuelText = new TextComponentTranslation("fuelstation.car_fuel_amount",
					INFO_COLOR + String.valueOf(car.getFuelAmount()),
					INFO_COLOR + String.valueOf(car.getMaxFuel())).getFormattedText();
			fontRenderer.drawString(fuelText, guiLeft+63, guiTop + 30, FONT_COLOR);
		}

		if(car.getFluid()==null){
			//TODO 
			return;
		}else {
			String typeText = new TextComponentTranslation("fuelstation.car_fuel_type", INFO_COLOR +car.getFluid().getLocalizedName(new FluidStack(car.getFluid(), 1))).getFormattedText();
			fontRenderer.drawString(typeText, guiLeft+63, guiTop + 40, FONT_COLOR);
		}
	}

	private void drawRefueled() {
		String refueledText = new TextComponentTranslation("fuelstation.refueled",
				INFO_COLOR + String.valueOf(fuelStation.getField(0))).getFormattedText();

		fontRenderer.drawString(refueledText, guiLeft+63, guiTop + 60, FONT_COLOR);
	}

	private void drawBuffer() {
		FluidStack stack = fuelStation.getStorage();

		if (stack == null) {

			String bufferText = new TextComponentTranslation("fuelstation.fuel_empty").getFormattedText();

			fontRenderer.drawString(bufferText, guiLeft+63, guiTop + 70, FONT_COLOR);

			return;
		}

		int amount = fuelStation.getField(1);
		
		String amountText = new TextComponentTranslation("fuelstation.fuel_buffer_amount",
				INFO_COLOR + String.valueOf(amount), INFO_COLOR + String.valueOf(fuelStation.maxStorageAmount)).getFormattedText();

		fontRenderer.drawString(amountText, guiLeft+63, guiTop + 70, FONT_COLOR);
		
		String fluidName = stack.getLocalizedName();
		
		String bufferText = new TextComponentTranslation("fuelstation.fuel_buffer_type", INFO_COLOR + fluidName)
				.getFormattedText();

		fontRenderer.drawString(bufferText, guiLeft+63, guiTop + 80, FONT_COLOR);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button.equals(buttonStart)) {
			fuelStation.setFueling(true);
			fuelStation.sendStartFuelPacket(true);
		} else if (button.equals(buttonStop)) {
			fuelStation.setFueling(false);
			fuelStation.sendStartFuelPacket(false);
		}
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

}
