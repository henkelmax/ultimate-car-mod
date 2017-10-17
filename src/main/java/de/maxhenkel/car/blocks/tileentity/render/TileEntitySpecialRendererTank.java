package de.maxhenkel.car.blocks.tileentity.render;

import org.lwjgl.opengl.GL11;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TileEntitySpecialRendererTank extends TileEntitySpecialRenderer<TileEntityTank> {

	public static final ResourceLocation LOCATION_TANK = new ResourceLocation(Main.MODID,
			"textures/blocks/tank_line.png");

	@Override
	public void render(TileEntityTank te, double x, double y, double z, float f, int i, float alpha) {
		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		double amount = te.getFillPercent();
		FluidStack stack = te.getFluid();
		if (amount > 0 && stack != null) {
			bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
			renderFluid(te, stack.getFluid(), amount, 0.0D);
		}

		bindTexture(LOCATION_TANK);
		renderLines(te);
		
		GlStateManager.popMatrix();
	}

	public static void renderFluid(TileEntityTank tank, Fluid fluid, double amount, double yStart) {
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);
		// GlStateManager.scale(0.98F, 0.98F, 0.98F);
		// GlStateManager.translate(0.01F, 0.01F, 0.01F);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

		TextureAtlasSprite texture = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(fluid.getStill().toString());

		double uMin = texture.getMinU();
		double uMax = texture.getMaxU();
		double vMin = texture.getMinV();
		double vMax = texture.getMaxV();

		double vHeight = vMax - vMin;

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		float s = 0.0F;

		if (!tank.isFluidConnected(EnumFacing.NORTH)) {
			// North
			buffer.pos(1D - s, yStart, 0D + s).tex(uMax, vMin).endVertex();
			buffer.pos(0D + s, yStart, 0D + s).tex(uMin, vMin).endVertex();
			buffer.pos(0D + s, yStart + amount - s * 2D, 0D + s).tex(uMin, vMin + vHeight * amount).endVertex();
			buffer.pos(1D - s, yStart + amount - s * 2D, 0D + s).tex(uMax, vMin + vHeight * amount).endVertex();
		}

		if (!tank.isFluidConnected(EnumFacing.SOUTH)) {
			// South
			buffer.pos(1D - s, yStart, 1D - s).tex(uMin, vMin).endVertex();
			buffer.pos(1D - s, yStart + amount - s * 2D, 1D - s).tex(uMin, vMin + vHeight * amount).endVertex();
			buffer.pos(0D + s, yStart + amount - s * 2D, 1D - s).tex(uMax, vMin + vHeight * amount).endVertex();
			buffer.pos(0D + s, yStart, 1D - s).tex(uMax, vMin).endVertex();
		}

		if (!tank.isFluidConnected(EnumFacing.EAST)) {
			// East
			buffer.pos(1D - s, yStart, 0D + s).tex(uMin, vMin).endVertex();
			buffer.pos(1D - s, yStart + amount - s * 2D, 0D + s).tex(uMin, vMin + vHeight * amount).endVertex();
			buffer.pos(1D - s, yStart + amount - s * 2D, 1D - s).tex(uMax, vMin + vHeight * amount).endVertex();
			buffer.pos(1D - s, yStart, 1D - s).tex(uMax, vMin).endVertex();
		}

		if (!tank.isFluidConnected(EnumFacing.WEST)) {
			// West
			buffer.pos(0D + s, yStart, 1D - s).tex(uMin, vMin).endVertex();
			buffer.pos(0D + s, yStart + amount - s * 2D, 1D - s).tex(uMin, vMin + vHeight * amount).endVertex();
			buffer.pos(0D + s, yStart + amount - s * 2D, 0D + s).tex(uMax, vMin + vHeight * amount).endVertex();
			buffer.pos(0D + s, yStart, 0D + s).tex(uMax, vMin).endVertex();
		}

		if (!tank.isFluidConnected(EnumFacing.DOWN)) {
			// Down
			buffer.pos(1D - s, yStart, 0D + s).tex(uMax, vMin).endVertex();
			buffer.pos(1D - s, yStart, 1D - s).tex(uMin, vMin).endVertex();
			buffer.pos(0D + s, yStart, 1D - s).tex(uMin, vMax).endVertex();
			buffer.pos(0D + s, yStart, 0D + s).tex(uMax, vMax).endVertex();
		}

		if (!tank.isFluidConnected(EnumFacing.UP)) {
			// Up
			buffer.pos(0D + s, yStart + amount - s * 2D, 0D + s).tex(uMax, vMax).endVertex();
			buffer.pos(0D + s, yStart + amount - s * 2D, 1D - s).tex(uMin, vMax).endVertex();
			buffer.pos(1D - s, yStart + amount - s * 2D, 1D - s).tex(uMin, vMin).endVertex();
			buffer.pos(1D - s, yStart + amount - s * 2D, 0D + s).tex(uMax, vMin).endVertex();
		}

		tessellator.draw();

		GlStateManager.popMatrix();
	}

	public static void renderLines(TileEntityTank te) {
		for (EnumFacing facing : EnumFacing.values()) {
			if (!te.isTankConnectedTo(facing)) {
				for (EnumDirection direction : EnumDirection.values()) {
					if (!te.isTankConnectedTo(direction.to(facing))) {
						drawLine(facing, direction);
					}
				}
			}
		}
	}

	public static void drawLine(EnumFacing side, EnumDirection line) {
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.color(1F, 1F, 1F, 1F);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		rotate(side);
		// Resize a little bit
		GlStateManager.scale(1.01D, 1.01D, 1.01D);
		GlStateManager.translate(-0.005D, -0.005D, -0.005D);
		drawSide(line, side, buffer);
		tessellator.draw();
		
		GlStateManager.popMatrix();
	}

	public static void rotate(EnumFacing facing) {
		GlStateManager.translate(0.5D, 0.5D, 0.5D);

		switch (facing) {
		case NORTH:
			GlStateManager.rotate(0F, 0F, 1F, 0F);
			break;
		case SOUTH:
			GlStateManager.rotate(180F, 0F, 1F, 0F);
			break;
		case EAST:
			GlStateManager.rotate(270F, 0F, 1F, 0F);
			break;
		case WEST:
			GlStateManager.rotate(90F, 0F, 1F, 0F);
			break;
		case UP:
			GlStateManager.rotate(180F, 0F, 1F, 0F);
			GlStateManager.rotate(90F, 1F, 0F, 0F);
			break;
		case DOWN:
			GlStateManager.rotate(180F, 0F, 1F, 0F);
			GlStateManager.rotate(270F, 1F, 0F, 0F);
			break;
		}

		GlStateManager.translate(-0.5D, -0.5D, -0.5D);
	}

	public static void drawSide(EnumDirection line, EnumFacing side, BufferBuilder buffer) {
		switch (line) {
		case UP:
			// Top
			buffer.pos(0D, 0D, 0D).tex(1D, 1D).endVertex();
			buffer.pos(0D, 1D, 0D).tex(1D, 0D).endVertex();
			buffer.pos(1D, 1D, 0D).tex(0D, 0D).endVertex();
			buffer.pos(1D, 0D, 0D).tex(0D, 1D).endVertex();
			//1432
			buffer.pos(0D, 0D, 0D).tex(1D, 1D).endVertex();
			buffer.pos(1D, 0D, 0D).tex(0D, 1D).endVertex();
			buffer.pos(1D, 1D, 0D).tex(0D, 0D).endVertex();
			buffer.pos(0D, 1D, 0D).tex(1D, 0D).endVertex();
			break;
		case DOWN:
			// Bottom
			buffer.pos(0D, 0D, 0D).tex(0D, 0D).endVertex();
			buffer.pos(0D, 1D, 0D).tex(0D, 1D).endVertex();
			buffer.pos(1D, 1D, 0D).tex(1D, 1D).endVertex();
			buffer.pos(1D, 0D, 0D).tex(1D, 0D).endVertex();
			
			buffer.pos(0D, 0D, 0D).tex(0D, 0D).endVertex();
			buffer.pos(1D, 0D, 0D).tex(1D, 0D).endVertex();
			buffer.pos(1D, 1D, 0D).tex(1D, 1D).endVertex();
			buffer.pos(0D, 1D, 0D).tex(0D, 1D).endVertex();
			break;
		case RIGHT:
			// Right
			buffer.pos(0D, 0D, 0D).tex(1D, 0D).endVertex();
			buffer.pos(0D, 1D, 0D).tex(0D, 0D).endVertex();
			buffer.pos(1D, 1D, 0D).tex(0D, 1D).endVertex();
			buffer.pos(1D, 0D, 0D).tex(1D, 1D).endVertex();
			
			buffer.pos(0D, 0D, 0D).tex(1D, 0D).endVertex();
			buffer.pos(1D, 0D, 0D).tex(1D, 1D).endVertex();
			buffer.pos(1D, 1D, 0D).tex(0D, 1D).endVertex();
			buffer.pos(0D, 1D, 0D).tex(0D, 0D).endVertex();
			break;
		case LEFT:
			// Left
			buffer.pos(0D, 0D, 0D).tex(0D, 1D).endVertex();
			buffer.pos(0D, 1D, 0D).tex(1D, 1D).endVertex();
			buffer.pos(1D, 1D, 0D).tex(1D, 0D).endVertex();
			buffer.pos(1D, 0D, 0D).tex(0D, 0D).endVertex();
			
			buffer.pos(0D, 0D, 0D).tex(0D, 1D).endVertex();
			buffer.pos(1D, 0D, 0D).tex(0D, 0D).endVertex();
			buffer.pos(1D, 1D, 0D).tex(1D, 0D).endVertex();
			buffer.pos(0D, 1D, 0D).tex(1D, 1D).endVertex();
			break;
		default:
			break;
		}
	}

	public static enum EnumDirection {
		UP, DOWN, LEFT, RIGHT;

		public EnumFacing to(EnumFacing facing) {
			switch (facing) {
			case NORTH:
				switch (this) {
				case UP:
					return EnumFacing.UP;
				case DOWN:
					return EnumFacing.DOWN;
				case LEFT:
					return EnumFacing.EAST;
				case RIGHT:
					return EnumFacing.WEST;
				}
			case SOUTH:
				switch (this) {
				case UP:
					return EnumFacing.UP;
				case DOWN:
					return EnumFacing.DOWN;
				case LEFT:
					return EnumFacing.WEST;
				case RIGHT:
					return EnumFacing.EAST;
				}
			case EAST:
				switch (this) {
				case UP:
					return EnumFacing.UP;
				case DOWN:
					return EnumFacing.DOWN;
				case LEFT:
					return EnumFacing.SOUTH;
				case RIGHT:
					return EnumFacing.NORTH;
				}
			case WEST:
				switch (this) {
				case UP:
					return EnumFacing.UP;
				case DOWN:
					return EnumFacing.DOWN;
				case LEFT:
					return EnumFacing.NORTH;
				case RIGHT:
					return EnumFacing.SOUTH;
				}
			case UP:
				switch (this) {
				case UP:
					return EnumFacing.NORTH;
				case DOWN:
					return EnumFacing.SOUTH;
				case LEFT:
					return EnumFacing.WEST;
				case RIGHT:
					return EnumFacing.EAST;
				}
			case DOWN:
				switch (this) {
				case UP:
					return EnumFacing.SOUTH;
				case DOWN:
					return EnumFacing.NORTH;
				case LEFT:
					return EnumFacing.EAST;
				case RIGHT:
					return EnumFacing.WEST;
				}
			}
			return EnumFacing.UP;
		}
	}

}
