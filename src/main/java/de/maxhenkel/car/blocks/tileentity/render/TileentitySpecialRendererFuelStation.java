package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.Direction;

public class TileentitySpecialRendererFuelStation extends TileEntityRenderer<TileEntityFuelStation> {

	@Override
	public void render(TileEntityFuelStation target, double x, double y, double z, float partialTicks, int destroyStage) {
		String name = target.getRenderText();

		if (name == null || name.isEmpty()) {
			return;
		}

		GlStateManager.pushMatrix();
		GlStateManager.translated(x + 0.5D, y + 1D, z + 0.5D);
		GlStateManager.rotatef(180.0F, 1.0F, 0.0F, 0.0F);

		Direction dir=target.getDirection();

		GlStateManager.rotatef(-getDirection(dir), 0.0F, 1.0F, 0.0F);

		FontRenderer renderer = getFontRenderer();

		if (renderer != null) {
			int textWidth = renderer.getStringWidth(name);
			float textScale = 0.36F / textWidth;
			textScale = Math.min(textScale, 0.01F);

			float posX=-(textScale * textWidth) / 2.0F;

			GlStateManager.translatef(posX, -0.815F, -0.188F);

			GlStateManager.scalef(textScale, textScale, textScale);

			GlStateManager.depthMask(false);
			renderer.drawString(name, 0, 0, 0);
			GlStateManager.depthMask(true);
		}

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
