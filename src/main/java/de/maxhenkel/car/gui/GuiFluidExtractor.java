package de.maxhenkel.car.gui;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class GuiFluidExtractor extends GuiContainer {

	private static final ResourceLocation GUI_TEXTURE = new ResourceLocation(Main.MODID,
			"textures/gui/gui_fluid_extractor.png");
	private static final int fontColor = 4210752;

	private IInventory playerInv;
	private TileEntityFluidExtractor tile;

	public GuiFluidExtractor(TileEntityFluidExtractor tile, EntityPlayer player) {
		super(new ContainerFluidExtractor(tile, player));
		this.playerInv = player.inventory;
		this.tile = tile;

		xSize = 176;
		ySize = 139;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);

		// Title
		this.fontRendererObj.drawString(playerInv.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2,
				fontColor);
		this.fontRendererObj.drawString(tile.getDisplayName().getUnformattedText(), 8, 6, fontColor);
		
		drawFilter();
	}
	
	private void drawFilter(){
		String name="-";
		
		Fluid f=tile.getFilterFluid();
		
		if(f!=null){
			name=f.getLocalizedName(new FluidStack(f, 1));
		}
		
		this.fontRendererObj.drawString(new TextComponentTranslation("filter.fluid", name).getFormattedText(), 46, 28, fontColor);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(GUI_TEXTURE);
		int i = this.guiLeft;
		int j = this.guiTop;
		this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
	}

}
