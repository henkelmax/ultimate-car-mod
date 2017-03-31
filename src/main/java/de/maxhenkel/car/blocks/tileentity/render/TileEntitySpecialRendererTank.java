package de.maxhenkel.car.blocks.tileentity.render;

import org.lwjgl.opengl.GL11;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelChest;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.VertexBuffer;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.RenderItemFrame;
import net.minecraft.client.renderer.tileentity.TileEntityChestRenderer;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TileEntitySpecialRendererTank extends TileEntitySpecialRenderer<TileEntityTank> {

	@Override
	public void renderTileEntityAt(TileEntityTank te, double x, double y, double z, float f, int i) {
		float time = te.getWorld().getTotalWorldTime() + f;

		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);
		GlStateManager.disableBlend();
		float fluid = te.getFillPercent();
		FluidStack stack = te.getFluid();
		if (fluid > 0 && stack != null) {
			renderFluid(stack.getFluid(), time, fluid, 0.01F);
		}
		GlStateManager.popMatrix();
	}

	public static void renderFluid(Fluid fluid, float time, float amount, float yStart) {
		GlStateManager.disableLighting();
		GlStateManager.color(1, 1, 1, 1);

		TextureAtlasSprite texture = Minecraft.getMinecraft().getTextureMapBlocks()
				.getAtlasSprite(fluid.getStill().toString());

		final double uMin = texture.getMinU();
		final double uMax = texture.getMaxU();
		final double vMin = texture.getMinV();
		final double vMax = texture.getMaxV();

		final double vHeight = vMax - vMin;

		Tessellator tessellator = Tessellator.getInstance();
		VertexBuffer buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		float s = 0.01F;

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
		GlStateManager.enableLighting();
	}

}
