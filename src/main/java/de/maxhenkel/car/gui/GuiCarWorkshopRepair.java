package de.maxhenkel.car.gui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.MathTools;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.net.MessageOpenGui;
import de.maxhenkel.car.net.MessageRepairCar;
import de.maxhenkel.car.proxy.CommonProxy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiCarWorkshopRepair extends GuiContainer {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/gui_car_workshop_repair.png");

	private static final int fontColor = 4210752;

	private EntityPlayer player;
	private TileEntityCarWorkshop tile;
	private float rotoation;

	private GuiButton buttonBack;
	private GuiButton buttonRepair;

	public GuiCarWorkshopRepair(ContainerCarWorkshopRepair container) {
		super(container);
		this.player = container.getPlayer();
		this.tile = container.getTile();

		xSize = 176;
		ySize = 222;
	}

	@Override
	public void initGui() {
		super.initGui();

		this.buttonRepair = addButton(new GuiButton(0, guiLeft + xSize - 7 - 60, guiTop + 105, 60, 20,
				new TextComponentTranslation("button.repair_car").getFormattedText()));
		this.buttonRepair.enabled = false;

		this.buttonBack = addButton(new GuiButton(1, guiLeft + 7, guiTop + 105, 60, 20,
				new TextComponentTranslation("button.back").getFormattedText()));
		this.buttonBack.enabled = true;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		// Titles
		this.fontRendererObj.drawString(tile.getDisplayName().getUnformattedText(), 8, 6, fontColor);
		this.fontRendererObj.drawString(player.inventory.getDisplayName().getFormattedText(), 8, ySize - 96 + 2,
				fontColor);
		
		EntityCarBase carTop = tile.getCarOnTop();

		if (!(carTop instanceof EntityCarDamageBase)) {
			buttonRepair.enabled = false;
			return;
		}

		EntityCarDamageBase car = (EntityCarDamageBase) carTop;

		if (mouseX >= guiLeft + 52 && mouseX <= guiLeft + 123) {
			if (mouseY >= guiTop + 81 && mouseY <= guiTop + 90) {
				List<String> list = new ArrayList<String>();
				list.add(new TextComponentTranslation("tooltip.damage", MathTools.round(car.getDamage(), 2))
						.getFormattedText());
				drawHoveringText(list, mouseX - guiLeft, mouseY - guiTop);
			}
		}
		
		if (tile.areRepairItemsInside()&&car.getDamage()>0) {
			buttonRepair.enabled = true;
		} else {
			buttonRepair.enabled = false;
		}
		drawCar(carTop);

	}

	private void drawCar(EntityCarBase car) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		MathTools.drawCarOnScreen(xSize / 2, 55, 23, rotoation, car);
		float parts = Minecraft.getMinecraft().getRenderPartialTicks();
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
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);

		drawDamage();
	}

	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}

	public void drawDamage() {
		EntityCarBase car = tile.getCarOnTop();
		if (!(car instanceof EntityCarDamageBase)) {
			return;
		}

		EntityCarDamageBase c = (EntityCarDamageBase) car;

		double percent = 100 - getDamagePercent(c);

		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int scaled = (int) (72 * percent / 100);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i + 52, j + 81, 176, 0, scaled, 10);
	}

	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		super.actionPerformed(button);

		if (button.id == buttonBack.id) {
			if (tile.getWorld().isRemote) {
				CommonProxy.simpleNetworkWrapper.sendToServer(
						new MessageOpenGui(tile.getPos(), GuiHandler.GUI_CAR_WORKSHOP_CRAFTING).open(player));
			}
		} else if (button.id == buttonRepair.id) {
			if (tile.getWorld().isRemote) {
				CommonProxy.simpleNetworkWrapper.sendToServer(new MessageRepairCar(tile.getPos()));
			}
		}
	}

}
