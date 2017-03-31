package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import net.minecraft.util.ResourceLocation;

public class GuiBlastFurnace extends GuiEnergyFluidProducer{

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/gui_blastfurnace.png");
	
	public GuiBlastFurnace(ContainerEnergyFluidProducer container) {
		super(container);
		
	}

	@Override
	public ResourceLocation getGuiTexture() {
		return GUI_TEXTURE;
	}

	@Override
	public String getUnlocalizedTooltipLiquid() {
		return "tooltip.methanol";
	}

}
