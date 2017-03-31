package de.maxhenkel.car.blocks.tileentity.render;

import org.lwjgl.opengl.GL11;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class TileentitySpecialRendererFuelStation extends TileEntitySpecialRenderer<TileEntityFuelStation>{

	@Override
	public void renderTileEntityAt(TileEntityFuelStation target, double x, double y, double z, float partialTicks, int destroyStage) {
		String name = target.getRenderText();

		if (name == null || name.isEmpty()) {
			return;
		}

		GL11.glPushMatrix();
		GL11.glTranslated(x + 0.5, y + 1, z + 0.5);
		GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);

		EnumFacing dir=target.getDirection();
		
		GL11.glRotatef(-getDirection(dir), 0.0F, 1.0F, 0.0F);

		FontRenderer renderer = getFontRenderer();

		if (renderer != null) {
			int textWidth = renderer.getStringWidth(name);
			float textScale = 0.36F / textWidth;
			textScale = Math.min(textScale, 0.01F);

			float posX=-(textScale * textWidth) / 2.0F;
			
			GL11.glTranslatef(posX, -0.815F, -0.188F);

			GL11.glScalef(textScale, textScale, textScale);

			GL11.glDepthMask(false);
			renderer.drawString(name, 0, 0, 0);
			GL11.glDepthMask(true);
		}

		GL11.glPopMatrix();
	}
	
	private int getDirection(EnumFacing facing) {
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
