package de.maxhenkel.car.blocks.tileentity.render;

import org.lwjgl.opengl.GL11;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TileEntitySpecialRendererTank extends TileEntitySpecialRenderer<TileEntityTank> {

	@Override
	public void renderTileEntityAt(TileEntityTank te, double x, double y, double z, float f, int i) {
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
		
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		
		float amount = te.getFillPercent();
		FluidStack stack = te.getFluid();
		if (amount > 0 && stack != null) {
			renderFluid(stack.getFluid(), amount, 0.0F);
		}
		GlStateManager.popMatrix();
	}

	public static void renderFluid(Fluid fluid, float amount, float yStart) {
		GlStateManager.disableLighting();
		GlStateManager.color(1, 1, 1, 1);
		GlStateManager.pushMatrix();
		GlStateManager.scale(0.98F, 0.98F, 0.98F);
		GlStateManager.translate(0.01F, 0.01F, 0.01F);
		GlStateManager.enableBlend();
		
		TextureAtlasSprite texture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());
		
		final double uMin = texture.getMinU();
		final double uMax = texture.getMaxU();
		final double vMin = texture.getMinV();
		final double vMax = texture.getMaxV();

		final double vHeight = vMax - vMin;

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		float s = 0.0F;

		// North
		buffer.pos(1 - s, yStart, 0 + s).tex(uMax, vMin).endVertex();
		buffer.pos(0 + s, yStart, 0 + s).tex(uMin, vMin).endVertex();
		buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMin, vMin + vHeight * amount).endVertex();
		buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin + vHeight * amount).endVertex();

		// South
		buffer.pos(1 - s, yStart, 1 - s).tex(uMin, vMin).endVertex();
		buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin + vHeight * amount).endVertex();
		buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMax, vMin + vHeight * amount).endVertex();
		buffer.pos(0 + s, yStart, 1 - s).tex(uMax, vMin).endVertex();

		// East
		buffer.pos(1 - s, yStart, 0 + s).tex(uMin, vMin).endVertex();
		buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMin, vMin + vHeight * amount).endVertex();
		buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMax, vMin + vHeight * amount).endVertex();
		buffer.pos(1 - s, yStart, 1 - s).tex(uMax, vMin).endVertex();

		// West
		buffer.pos(0 + s, yStart, 1 - s).tex(uMin, vMin).endVertex();
		buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin + vHeight * amount).endVertex();
		buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin + vHeight * amount).endVertex();
		buffer.pos(0 + s, yStart, 0 + s).tex(uMax, vMin).endVertex();

		// Down
		buffer.pos(1 - s, yStart, 0 + s).tex(uMax, vMin).endVertex();
		buffer.pos(1 - s, yStart, 1 - s).tex(uMin, vMin).endVertex();
		buffer.pos(0 + s, yStart, 1 - s).tex(uMin, vMax).endVertex();
		buffer.pos(0 + s, yStart, 0 + s).tex(uMax, vMax).endVertex();

		// Up
		buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMax, vMax).endVertex();
		buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMin, vMax).endVertex();
		buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin).endVertex();
		buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin).endVertex();

		tessellator.draw();
		GlStateManager.popMatrix();
		GlStateManager.enableLighting();
	}

}
