package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

public class GuiCar extends GuiBase{

	private static final ResourceLocation CAR_GUI_TEXTURE = new ResourceLocation(Main.MODID, "textures/gui/gui_car.png");
	
	private static final int fontColor=4210752;
	
	private IInventory playerInv;
	private EntityCarInventoryBase car;
	
	public GuiCar(IInventory playerInv, EntityCarInventoryBase car) {
		super(new ContainerCar(car, playerInv));
		this.playerInv=playerInv;
		this.car=car;
		
		xSize=176;
		ySize=248;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		 
		//Titles
		fontRenderer.drawString(car.getCarName().getUnformattedText(), 7, 87, fontColor);
		fontRenderer.drawString(playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, fontColor);
	   
		fontRenderer.drawString(getFuelString(), 7, 9, fontColor);
		fontRenderer.drawString(getDamageString(), 7, 35, fontColor);
        fontRenderer.drawString(getBatteryString(), 95, 9, fontColor);
        fontRenderer.drawString(getTempString(), 95, 35, fontColor);

	    //drawCenteredString(fontRendererObj, new TextComponentTranslation("car.fuel_slot").getFormattedText(), 123, 28, fontColor);
	    
	}
	
	public float getFuelPercent(){
		float fuelPerc=((float)car.getFuelAmount())/((float)car.getMaxFuel())*100F;
		return MathTools.round(fuelPerc, 2);
	}

    public int getBatteryPercent(){
        return (int)(car.getBatteryPercentage()*100F);
    }

    public float getTemperatureCelsius(){
        return MathTools.round(car.getTemperature(), 2);
    }

    public float getTemperaturePercent(){
        float temp=car.getTemperature();
        if(temp>100F){
            temp=100F;
        }
        if(temp<0F){
            temp=0F;
        }
        return temp/100F;
    }
	
	public float getDamagePercent(){
		float dmg=car.getDamage();
		dmg=Math.min(dmg, 100);
		return MathTools.round(dmg, 2);
	}
	
	public String getFuelString(){
		return new TextComponentTranslation("gui.car_fuel", String.valueOf(getFuelPercent())).getFormattedText();
	}
	
	public String getDamageString(){
		return new TextComponentTranslation("gui.car_damage", String.valueOf(getDamagePercent())).getFormattedText();
	}

    public String getBatteryString(){
        return new TextComponentTranslation("gui.car_battery", String.valueOf(getBatteryPercent())).getFormattedText();
    }

    public String getTempString(){
        return new TextComponentTranslation("gui.car_temperature", String.valueOf(getTemperatureCelsius())).getFormattedText();
    }

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(CAR_GUI_TEXTURE);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        drawFuel(getFuelPercent());
        drawDamage(100F-getDamagePercent());
        drawBattery(car.getBatteryPercentage());
        drawTemp(getTemperaturePercent());
	}
	
	public void drawFuel(float percent){
		//72x10
		int scaled=(int) (72F*percent/100D);
		int i = this.guiLeft;
        int j = this.guiTop;
		this.drawTexturedModalRect(i+8, j+20, 176, 0, scaled, 10);
	}
	
	public void drawDamage(float percent){
		int scaled=(int) (72F*percent/100D);
		int i = this.guiLeft;
        int j = this.guiTop;
		this.drawTexturedModalRect(i+8, j+46, 176, 10, scaled, 10);
	}

	public void drawTemp(float percent){
		int scaled=(int) (72F*percent);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i+96, j+46, 176, 30, scaled, 10);
	}

	public void drawBattery(float percent){
		int scaled=(int) (72F*percent);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i+96, j+20, 176, 20, scaled, 10);
	}

}
