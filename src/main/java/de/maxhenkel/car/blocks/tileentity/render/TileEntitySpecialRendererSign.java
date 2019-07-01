package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.blocks.BlockSign;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;

public class TileEntitySpecialRendererSign extends TileEntityRenderer<TileEntitySign> {

	@Override
	public void render(TileEntitySign te, double x, double y, double z, float partialTicks, int destroyStage) {
		if (getFontRenderer() == null) {
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.translated(x + 0.5D, y + 1D, z + 0.5D);
		GlStateManager.rotatef(180.0F, 1.0F, 0.0F, 0.0F);
		GlStateManager.rotatef(-getDirection(te.getBlockState().get(BlockSign.FACING)), 0.0F, 1.0F, 0.0F);
		GlStateManager.depthMask(false);

		//----------Front-----------
		GlStateManager.pushMatrix();

		GlStateManager.translated(0D, 0.27D, -0.51D/16.0D);

		drawText(te.getText(0));
		GlStateManager.translated(0D, 0.116D, 0D);
		drawText(te.getText(1));
		GlStateManager.translated(0D, 0.116D, 0D);
		drawText(te.getText(2));
		GlStateManager.translated(0D, 0.116D, 0D);
		drawText(te.getText(3));

		GlStateManager.popMatrix();

		//----------Back-----------
		GlStateManager.pushMatrix();
		GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);

		GlStateManager.translated(0D, 0.27D, -0.51D/16.0D);

		drawText(te.getText(4));
		GlStateManager.translated(0D, 0.116D, 0D);
		drawText(te.getText(5));
		GlStateManager.translated(0D, 0.116D, 0D);
		drawText(te.getText(6));
		GlStateManager.translated(0D, 0.116D, 0D);
		drawText(te.getText(7));

		GlStateManager.popMatrix();
		//---------

		GlStateManager.depthMask(true);

		GlStateManager.popMatrix();
	}
	
	private void drawText(String txt) {
		GlStateManager.pushMatrix();
		
		int textWidth = getFontRenderer().getStringWidth(txt);
		double textScale = 0.008;

		GlStateManager.translated(-(textScale * textWidth) / 2D, 0D, 0D);

		GlStateManager.scaled(textScale, textScale, textScale);
		
		getFontRenderer().drawString(txt, 0, 0, 0);
		
		GlStateManager.popMatrix();
	}

	private int getDirection(Direction facing) {
		switch (facing) {
			case SOUTH:
				return 0;
			case EAST:
				return 90;
			case NORTH:
				return 180;
			case WEST:
				return 270;
			default:
				return 0;
		}
	}

}
