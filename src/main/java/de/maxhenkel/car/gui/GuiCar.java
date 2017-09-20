package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.MathTools;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiCar extends GuiContainer{

	private static final ResourceLocation CAR_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car.png");
	
	private static final int fontColor=4210752;
	
	private IInventory playerInv;
	private EntityCarInventoryBase car;
	
	public GuiCar(IInventory playerInv, EntityCarInventoryBase car) {
		super(new ContainerCar(car, playerInv));
		this.playerInv=playerInv;
		this.car=car;
		
		xSize=176;
		ySize=222;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		 
		//Titles
		this.fontRendererObj.drawString(car.getCarName().getUnformattedText(), 7, 61, fontColor);
	    this.fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, fontColor);
	   
	    this.fontRendererObj.drawString(getFuelString(), 7, 9, fontColor);
	    this.fontRendererObj.drawString(getDamageString(), 7, 35, fontColor);
	    
	    //drawCenteredString(fontRendererObj, new TextComponentTranslation("car.fuel_slot").getFormattedText(), 123, 28, fontColor);
	    
	}
	
	public double getFuelPercent(){
		float fuelPerc=((float)car.getFuel())/((float)car.getMaxFuel())*100F;
		return MathTools.round(fuelPerc, 2);
	}
	
	public double getDamagePercent(){
		float dmg=car.getDamage();
		dmg=Math.min(dmg, 100);
		return MathTools.round(dmg, 2);
	}
	
	public String getFuelString(){
		return new TextComponentTranslation("gui.fuel", String.valueOf(getFuelPercent())).getFormattedText();
	}
	
	public String getDamageString(){
		return new TextComponentTranslation("gui.damage", String.valueOf(getDamagePercent())).getFormattedText();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CAR_GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        drawFuel(getFuelPercent());
        drawDamage(100-getDamagePercent());
	}
	
	public void drawFuel(double percent){
		//72x10
		int scaled=(int) (72*percent/100F);
		int i = this.guiLeft;
        int j = this.guiTop;
		this.drawTexturedModalRect(i+8, j+20, 176, 0, scaled, 10);
	}
	
	public void drawDamage(double percent){
		int scaled=(int) (72*percent/100);
		int i = this.guiLeft;
        int j = this.guiTop;
		this.drawTexturedModalRect(i+8, j+46, 176, 10, scaled, 10);
	}

}
