package de.maxhenkel.car.blocks.tileentity.render;

import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.EnumFacing;

public class TileentitySpecialRendererFuelStation extends TileEntitySpecialRenderer<TileEntityFuelStation>{

	@Override
	public void render(TileEntityFuelStation target, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
		String name = target.getRenderText();

		if (name == null || name.isEmpty()) {
			return;
		}

		GlStateManager.pushMatrix();
		//GL11.glPushMatrix();
        GlStateManager.translate(x + 0.5, y + 1, z + 0.5);
		//GL11.glTranslated(x + 0.5, y + 1, z + 0.5);
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
		//GL11.glRotatef(180.0F, 1.0F, 0.0F, 0.0F);

		EnumFacing dir=target.getDirection();

        GlStateManager.rotate(-getDirection(dir), 0.0F, 1.0F, 0.0F);
		//GL11.glRotatef(-getDirection(dir), 0.0F, 1.0F, 0.0F);

		FontRenderer renderer = getFontRenderer();

		if (renderer != null) {
			int textWidth = renderer.getStringWidth(name);
			float textScale = 0.36F / textWidth;
			textScale = Math.min(textScale, 0.01F);

			float posX=-(textScale * textWidth) / 2.0F;
			
			//GL11.glTranslatef(posX, -0.815F, -0.188F);
            GlStateManager.translate(posX, -0.815F, -0.188F);

			//GL11.glScalef(textScale, textScale, textScale);
            GlStateManager.scale(textScale, textScale, textScale);

			//GL11.glDepthMask(false);
            GlStateManager.depthMask(false);
			renderer.drawString(name, 0, 0, 0);
			//GL11.glDepthMask(true);
            GlStateManager.depthMask(true);
		}

        GlStateManager.popMatrix();
		//GL11.glPopMatrix();
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
