package de.maxhenkel.car.blocks.tileentity.render;

import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

public class TileEntitySpecialRendererSign extends TileEntitySpecialRenderer<TileEntitySign> {

	@Override
	public void render(TileEntitySign te, double x, double y, double z, float f, int i, float alpha) {
		if (getFontRenderer() == null) {
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.translate(x + 0.5, y + 1.0, z + 0.5);
		GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotate(-getDirection(te.getBlockMetadata()), 0.0F, 1.0F, 0.0F);
		GlStateManager.depthMask(false);
		
		//----------Front-----------
		GlStateManager.pushMatrix();
		
		GlStateManager.translate(0, 0.27, -0.51/16.0);
		
		drawText(te.getText(0));
		GlStateManager.translate(0, 0.116, 0);
		drawText(te.getText(1));
		GlStateManager.translate(0, 0.116, 0);
		drawText(te.getText(2));
		GlStateManager.translate(0, 0.116, 0);
		drawText(te.getText(3));
		
		GlStateManager.popMatrix();
		
		//----------Back-----------
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
		
		GlStateManager.translate(0, 0.27, -0.51/16.0);
		
		drawText(te.getText(4));
		GlStateManager.translate(0, 0.116, 0);
		drawText(te.getText(5));
		GlStateManager.translate(0, 0.116, 0);
		drawText(te.getText(6));
		GlStateManager.translate(0, 0.116, 0);
		drawText(te.getText(7));
		
		GlStateManager.popMatrix();
		//---------
		
		GlStateManager.depthMask(true);
		
		GlStateManager.popMatrix();
	}
	
	private void drawText(String txt) {
		GlStateManager.pushMatrix();
		
		int textWidth = getFontRenderer().getStringWidth(txt);
		double textScale = 0.013;

		GlStateManager.translate(-(textScale * textWidth) / 2.0, 0, 0);

		GlStateManager.scale(textScale, textScale, textScale);
		
		getFontRenderer().drawString(txt, 0, 0, 0);
		
		GlStateManager.popMatrix();
	}

	private int getDirection(int i) {
		switch (i) {
		case 2:
			return 0;
		case 4:
			return 90;
		case 3:
			return 180;
		case 5:
			return 270;
		default:
			return 0;
		}
	}

}
