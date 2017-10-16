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
		bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

		GlStateManager.pushMatrix();
		GlStateManager.translate(x, y, z);

		float amount = te.getFillPercent();
		FluidStack stack = te.getFluid();
		if (amount > 0 && stack != null) {
			renderFluid(te, stack.getFluid(), amount, 0.0F);
		}

		bindTexture(LOCATION_TANK);
		renderLine(te);
		GlStateManager.popMatrix();
	}

	public static void renderFluid(TileEntityTank tank, Fluid fluid, float amount, float yStart) {
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		GlStateManager.color(1, 1, 1, 1);
		// GlStateManager.scale(0.98F, 0.98F, 0.98F);
		// GlStateManager.translate(0.01F, 0.01F, 0.01F);
		GlStateManager.enableBlend();
		GlStateManager.enableAlpha();

		TextureAtlasSprite texture = Minecraft.getMinecraft().getTextureMapBlocks()
				.getAtlasSprite(fluid.getStill().toString());

		final double uMin = texture.getMinU();
		final double uMax = texture.getMaxU();
		final double vMin = texture.getMinV();
		final double vMax = texture.getMaxV();

		final double vHeight = vMax - vMin;

		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

		float s = 0.0F;

		if (!tank.isConnectedToFluid(EnumFacing.NORTH)) {
			// North
			buffer.pos(1 - s, yStart, 0 + s).tex(uMax, vMin).endVertex();
			buffer.pos(0 + s, yStart, 0 + s).tex(uMin, vMin).endVertex();
			buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMin, vMin + vHeight * amount).endVertex();
			buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin + vHeight * amount).endVertex();
		}

		if (!tank.isConnectedToFluid(EnumFacing.SOUTH)) {
			// South
			buffer.pos(1 - s, yStart, 1 - s).tex(uMin, vMin).endVertex();
			buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin + vHeight * amount).endVertex();
			buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMax, vMin + vHeight * amount).endVertex();
			buffer.pos(0 + s, yStart, 1 - s).tex(uMax, vMin).endVertex();
		}

		if (!tank.isConnectedToFluid(EnumFacing.EAST)) {
			// East
			buffer.pos(1 - s, yStart, 0 + s).tex(uMin, vMin).endVertex();
			buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMin, vMin + vHeight * amount).endVertex();
			buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMax, vMin + vHeight * amount).endVertex();
			buffer.pos(1 - s, yStart, 1 - s).tex(uMax, vMin).endVertex();
		}

		if (!tank.isConnectedToFluid(EnumFacing.WEST)) {
			// West
			buffer.pos(0 + s, yStart, 1 - s).tex(uMin, vMin).endVertex();
			buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin + vHeight * amount).endVertex();
			buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin + vHeight * amount).endVertex();
			buffer.pos(0 + s, yStart, 0 + s).tex(uMax, vMin).endVertex();
		}

		if (!tank.isConnectedToFluid(EnumFacing.DOWN)) {
			// Down
			buffer.pos(1 - s, yStart, 0 + s).tex(uMax, vMin).endVertex();
			buffer.pos(1 - s, yStart, 1 - s).tex(uMin, vMin).endVertex();
			buffer.pos(0 + s, yStart, 1 - s).tex(uMin, vMax).endVertex();
			buffer.pos(0 + s, yStart, 0 + s).tex(uMax, vMax).endVertex();
		}

		if (!tank.isConnectedToFluid(EnumFacing.UP)) {
			// Up
			buffer.pos(0 + s, yStart + amount - s * 2, 0 + s).tex(uMax, vMax).endVertex();
			buffer.pos(0 + s, yStart + amount - s * 2, 1 - s).tex(uMin, vMax).endVertex();
			buffer.pos(1 - s, yStart + amount - s * 2, 1 - s).tex(uMin, vMin).endVertex();
			buffer.pos(1 - s, yStart + amount - s * 2, 0 + s).tex(uMax, vMin).endVertex();
		}

		tessellator.draw();

		GlStateManager.popMatrix();
	}

	public static void renderLine(TileEntityTank te) {
		for (EnumFacing facing : EnumFacing.values()) {
			if (!te.isConnectedTo(facing)) {
				for (EnumDirection direction : EnumDirection.values()) {
					if (!te.isConnectedTo(direction.to(facing))) {
						draw(facing, direction);
					}
				}
			}
		}
	}

	public static void draw(EnumFacing side, EnumDirection line) {
		GlStateManager.pushMatrix();
		GlStateManager.disableLighting();
		
		Tessellator tessellator = Tessellator.getInstance();
		BufferBuilder buffer = tessellator.getBuffer();

		buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
		rotate(side);
		// Resize a little bit
		GlStateManager.scale(1.01F, 1.01F, 1.01F);
		GlStateManager.translate(-0.005F, -0.005F, -0.005F);
		drawSide(line, side, buffer);
		tessellator.draw();
		
		GlStateManager.popMatrix();
	}

	public static void rotate(EnumFacing facing) {
		GlStateManager.translate(0.5F, 0.5F, 0.5F);

		switch (facing) {
		case NORTH:
			GlStateManager.rotate(0, 0, 1, 0);
			break;
		case SOUTH:
			GlStateManager.rotate(180, 0, 1, 0);
			break;
		case EAST:
			GlStateManager.rotate(270, 0, 1, 0);
			break;
		case WEST:
			GlStateManager.rotate(90, 0, 1, 0);
			break;
		case UP:
			GlStateManager.rotate(180, 0, 1, 0);
			GlStateManager.rotate(90, 1, 0, 0);
			break;
		case DOWN:
			GlStateManager.rotate(180, 0, 1, 0);
			GlStateManager.rotate(270, 1, 0, 0);
			break;
		}

		GlStateManager.translate(-0.5F, -0.5F, -0.5F);
	}

	public static void drawSide(EnumDirection line, EnumFacing side, BufferBuilder buffer) {
		switch (line) {
		case UP:
			// Top
			buffer.pos(0, 0, 0).tex(1, 1).endVertex();//1432
			buffer.pos(0, 1, 0).tex(1, 0).endVertex();
			buffer.pos(1, 1, 0).tex(0, 0).endVertex();
			buffer.pos(1, 0, 0).tex(0, 1).endVertex();
			
			buffer.pos(0, 0, 0).tex(1, 1).endVertex();
			buffer.pos(1, 0, 0).tex(0, 1).endVertex();
			buffer.pos(1, 1, 0).tex(0, 0).endVertex();
			buffer.pos(0, 1, 0).tex(1, 0).endVertex();
			break;
		case DOWN:
			// Bottom
			buffer.pos(0, 0, 0).tex(0, 0).endVertex();
			buffer.pos(0, 1, 0).tex(0, 1).endVertex();
			buffer.pos(1, 1, 0).tex(1, 1).endVertex();
			buffer.pos(1, 0, 0).tex(1, 0).endVertex();
			
			buffer.pos(0, 0, 0).tex(0, 0).endVertex();
			buffer.pos(1, 0, 0).tex(1, 0).endVertex();
			buffer.pos(1, 1, 0).tex(1, 1).endVertex();
			buffer.pos(0, 1, 0).tex(0, 1).endVertex();
			break;
		case RIGHT:
			// Right
			buffer.pos(0, 0, 0).tex(1, 0).endVertex();
			buffer.pos(0, 1, 0).tex(0, 0).endVertex();
			buffer.pos(1, 1, 0).tex(0, 1).endVertex();
			buffer.pos(1, 0, 0).tex(1, 1).endVertex();
			
			buffer.pos(0, 0, 0).tex(1, 0).endVertex();
			buffer.pos(1, 0, 0).tex(1, 1).endVertex();
			buffer.pos(1, 1, 0).tex(0, 1).endVertex();
			buffer.pos(0, 1, 0).tex(0, 0).endVertex();
			break;
		case LEFT:
			// Left
			buffer.pos(0, 0, 0).tex(0, 1).endVertex();
			buffer.pos(0, 1, 0).tex(1, 1).endVertex();
			buffer.pos(1, 1, 0).tex(1, 0).endVertex();
			buffer.pos(1, 0, 0).tex(0, 0).endVertex();
			
			buffer.pos(0, 0, 0).tex(0, 1).endVertex();
			buffer.pos(1, 0, 0).tex(0, 0).endVertex();
			buffer.pos(1, 1, 0).tex(1, 0).endVertex();
			buffer.pos(0, 1, 0).tex(1, 1).endVertex();
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
