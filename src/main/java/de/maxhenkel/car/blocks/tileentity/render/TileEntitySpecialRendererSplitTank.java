package de.maxhenkel.car.blocks.tileentity.render;

import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TileEntitySpecialRendererSplitTank extends TileEntitySpecialRenderer<TileEntitySplitTank> {

	@Override
	public void render(TileEntitySplitTank te, double x, double y, double z, float f, int i, float alpha) {
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.disableBlend();
		
		float bioDiesel = te.getBioDieselPerc() / 2F;
		float glycerin = te.getGlycerinPerc() / 2F;

		if (bioDiesel > 0) {
			TileEntitySpecialRendererTank.renderFluid(ModFluids.BIO_DIESEL, bioDiesel, 1F/16F);
		}
		
		if (glycerin > 0) {
			TileEntitySpecialRendererTank.renderFluid(ModFluids.GLYCERIN, glycerin, bioDiesel+1F/16F);
		}
		
		GlStateManager.popMatrix();

	}
}
