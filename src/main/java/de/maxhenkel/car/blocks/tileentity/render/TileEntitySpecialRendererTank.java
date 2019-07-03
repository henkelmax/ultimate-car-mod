package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.tools.FluidStackWrapper;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.util.Direction;
import org.lwjgl.opengl.GL11;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

public class TileEntitySpecialRendererTank extends TileEntityRenderer<TileEntityTank> {

    public static final ResourceLocation LOCATION_TANK = new ResourceLocation(Main.MODID,
            "textures/block/tank_line.png");

    @Override
    public void render(TileEntityTank te, double x, double y, double z, float partialTicks, int destroyStage) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y, z);

        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.enableAlphaTest();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        double amount = te.getFillPercent();
        FluidStack stack = te.getFluid();
        if (amount > 0 && stack != null) {

            renderFluid(te, stack.getFluid(), amount, 0.0D);
        }

        bindTexture(LOCATION_TANK);
        renderLines(te);

        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.disableAlphaTest();

        GlStateManager.popMatrix();
    }

    public void renderFluid(TileEntityTank tank, Fluid fluid, double amount, double yStart) {
        //bindTexture(AtlasTexture.LOCATION_BLOCKS_TEXTURE);
        bindTexture(FluidStackWrapper.getTexture(fluid));

        GlStateManager.pushMatrix();
		/*TextureAtlasSprite texture = Minecraft.getInstance().getTextureMap().getAtlasSprite(fluid.getStill().toString());

		double uMin = texture.getMinU();
		double uMax = texture.getMaxU();
		double vMin = texture.getMinV();
		double vMax = texture.getMaxV();*/

        double uMin = 0D;
        double uMax = 1D;
        double vMin = 0D;
        double vMax = 1D / 32D;

        double vHeight = vMax - vMin;

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);

        float s = 0.0F;

        if (!tank.isFluidConnected(Direction.NORTH)) {
            // North
            buffer.pos(1D - s, yStart, 0D + s).tex(uMax, vMin).endVertex();
            buffer.pos(0D + s, yStart, 0D + s).tex(uMin, vMin).endVertex();
            buffer.pos(0D + s, yStart + amount - s * 2D, 0D + s).tex(uMin, vMin + vHeight * amount).endVertex();
            buffer.pos(1D - s, yStart + amount - s * 2D, 0D + s).tex(uMax, vMin + vHeight * amount).endVertex();
        }

        if (!tank.isFluidConnected(Direction.SOUTH)) {
            // South
            buffer.pos(1D - s, yStart, 1D - s).tex(uMin, vMin).endVertex();
            buffer.pos(1D - s, yStart + amount - s * 2D, 1D - s).tex(uMin, vMin + vHeight * amount).endVertex();
            buffer.pos(0D + s, yStart + amount - s * 2D, 1D - s).tex(uMax, vMin + vHeight * amount).endVertex();
            buffer.pos(0D + s, yStart, 1D - s).tex(uMax, vMin).endVertex();
        }

        if (!tank.isFluidConnected(Direction.EAST)) {
            // East
            buffer.pos(1D - s, yStart, 0D + s).tex(uMin, vMin).endVertex();
            buffer.pos(1D - s, yStart + amount - s * 2D, 0D + s).tex(uMin, vMin + vHeight * amount).endVertex();
            buffer.pos(1D - s, yStart + amount - s * 2D, 1D - s).tex(uMax, vMin + vHeight * amount).endVertex();
            buffer.pos(1D - s, yStart, 1D - s).tex(uMax, vMin).endVertex();
        }

        if (!tank.isFluidConnected(Direction.WEST)) {
            // West
            buffer.pos(0D + s, yStart, 1D - s).tex(uMin, vMin).endVertex();
            buffer.pos(0D + s, yStart + amount - s * 2D, 1D - s).tex(uMin, vMin + vHeight * amount).endVertex();
            buffer.pos(0D + s, yStart + amount - s * 2D, 0D + s).tex(uMax, vMin + vHeight * amount).endVertex();
            buffer.pos(0D + s, yStart, 0D + s).tex(uMax, vMin).endVertex();
        }

        if (!tank.isFluidConnected(Direction.DOWN)) {
            // Down
            buffer.pos(1D - s, yStart, 0D + s).tex(uMax, vMin).endVertex();
            buffer.pos(1D - s, yStart, 1D - s).tex(uMin, vMin).endVertex();
            buffer.pos(0D + s, yStart, 1D - s).tex(uMin, vMax).endVertex();
            buffer.pos(0D + s, yStart, 0D + s).tex(uMax, vMax).endVertex();
        }

        if (!tank.isFluidConnected(Direction.UP)) {
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
        for (Direction facing : Direction.values()) {
            if (!te.isTankConnectedTo(facing)) {
                for (EnumDirection direction : EnumDirection.values()) {
                    if (!te.isTankConnectedTo(direction.to(facing))) {
                        drawLine(facing, direction);
                    }
                }
            }
        }
    }

    public static void drawLine(Direction side, EnumDirection line) {
        GlStateManager.pushMatrix();

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();

        buffer.begin(GL11.GL_QUADS, DefaultVertexFormats.POSITION_TEX);
        rotate(side);
        // Resize a little bit
        GlStateManager.scaled(1.01D, 1.01D, 1.01D);
        GlStateManager.translated(-0.005D, -0.005D, -0.005D);
        drawSide(line, side, buffer);
        tessellator.draw();

        GlStateManager.popMatrix();
    }

    public static void rotate(Direction facing) {
        GlStateManager.translated(0.5D, 0.5D, 0.5D);

        switch (facing) {
            case NORTH:
                GlStateManager.rotatef(0F, 0F, 1F, 0F);
                break;
            case SOUTH:
                GlStateManager.rotatef(180F, 0F, 1F, 0F);
                break;
            case EAST:
                GlStateManager.rotatef(270F, 0F, 1F, 0F);
                break;
            case WEST:
                GlStateManager.rotatef(90F, 0F, 1F, 0F);
                break;
            case UP:
                GlStateManager.rotatef(180F, 0F, 1F, 0F);
                GlStateManager.rotatef(90F, 1F, 0F, 0F);
                break;
            case DOWN:
                GlStateManager.rotatef(180F, 0F, 1F, 0F);
                GlStateManager.rotatef(270F, 1F, 0F, 0F);
                break;
        }

        GlStateManager.translated(-0.5D, -0.5D, -0.5D);
    }

    public static void drawSide(EnumDirection line, Direction side, BufferBuilder buffer) {
        switch (line) {
            case UP:
                // Top
                buffer.pos(0D, 0D, 0D).tex(1D, 1D).endVertex();
                buffer.pos(0D, 1D, 0D).tex(1D, 0D).endVertex();
                buffer.pos(1D, 1D, 0D).tex(0D, 0D).endVertex();
                buffer.pos(1D, 0D, 0D).tex(0D, 1D).endVertex();
                /*
                buffer.pos(0D, 0D, 0D).tex(1D, 1D).endVertex();
                buffer.pos(1D, 0D, 0D).tex(0D, 1D).endVertex();
                buffer.pos(1D, 1D, 0D).tex(0D, 0D).endVertex();
                buffer.pos(0D, 1D, 0D).tex(1D, 0D).endVertex();
                */
                break;
            case DOWN:
                // Bottom
                buffer.pos(0D, 0D, 0D).tex(0D, 0D).endVertex();
                buffer.pos(0D, 1D, 0D).tex(0D, 1D).endVertex();
                buffer.pos(1D, 1D, 0D).tex(1D, 1D).endVertex();
                buffer.pos(1D, 0D, 0D).tex(1D, 0D).endVertex();

                /*
                buffer.pos(0D, 0D, 0D).tex(0D, 0D).endVertex();
                buffer.pos(1D, 0D, 0D).tex(1D, 0D).endVertex();
                buffer.pos(1D, 1D, 0D).tex(1D, 1D).endVertex();
                buffer.pos(0D, 1D, 0D).tex(0D, 1D).endVertex();
                 */
                break;
            case RIGHT:
                // Right
                buffer.pos(0D, 0D, 0D).tex(1D, 0D).endVertex();
                buffer.pos(0D, 1D, 0D).tex(0D, 0D).endVertex();
                buffer.pos(1D, 1D, 0D).tex(0D, 1D).endVertex();
                buffer.pos(1D, 0D, 0D).tex(1D, 1D).endVertex();

                /*
                buffer.pos(0D, 0D, 0D).tex(1D, 0D).endVertex();
                buffer.pos(1D, 0D, 0D).tex(1D, 1D).endVertex();
                buffer.pos(1D, 1D, 0D).tex(0D, 1D).endVertex();
                buffer.pos(0D, 1D, 0D).tex(0D, 0D).endVertex();
                 */
                break;
            case LEFT:
                // Left
                buffer.pos(0D, 0D, 0D).tex(0D, 1D).endVertex();
                buffer.pos(0D, 1D, 0D).tex(1D, 1D).endVertex();
                buffer.pos(1D, 1D, 0D).tex(1D, 0D).endVertex();
                buffer.pos(1D, 0D, 0D).tex(0D, 0D).endVertex();

                /*
                buffer.pos(0D, 0D, 0D).tex(0D, 1D).endVertex();
                buffer.pos(1D, 0D, 0D).tex(0D, 0D).endVertex();
                buffer.pos(1D, 1D, 0D).tex(1D, 0D).endVertex();
                buffer.pos(0D, 1D, 0D).tex(1D, 1D).endVertex();
                 */
                break;
            default:
                break;
        }
    }

    public enum EnumDirection {
        UP, DOWN, LEFT, RIGHT;

        public Direction to(Direction facing) {
            switch (facing) {
                case NORTH:
                    switch (this) {
                        case UP:
                            return Direction.UP;
                        case DOWN:
                            return Direction.DOWN;
                        case LEFT:
                            return Direction.EAST;
                        case RIGHT:
                            return Direction.WEST;
                    }
                case SOUTH:
                    switch (this) {
                        case UP:
                            return Direction.UP;
                        case DOWN:
                            return Direction.DOWN;
                        case LEFT:
                            return Direction.WEST;
                        case RIGHT:
                            return Direction.EAST;
                    }
                case EAST:
                    switch (this) {
                        case UP:
                            return Direction.UP;
                        case DOWN:
                            return Direction.DOWN;
                        case LEFT:
                            return Direction.SOUTH;
                        case RIGHT:
                            return Direction.NORTH;
                    }
                case WEST:
                    switch (this) {
                        case UP:
                            return Direction.UP;
                        case DOWN:
                            return Direction.DOWN;
                        case LEFT:
                            return Direction.NORTH;
                        case RIGHT:
                            return Direction.SOUTH;
                    }
                case UP:
                    switch (this) {
                        case UP:
                            return Direction.NORTH;
                        case DOWN:
                            return Direction.SOUTH;
                        case LEFT:
                            return Direction.WEST;
                        case RIGHT:
                            return Direction.EAST;
                    }
                case DOWN:
                    switch (this) {
                        case UP:
                            return Direction.SOUTH;
                        case DOWN:
                            return Direction.NORTH;
                        case LEFT:
                            return Direction.WEST;
                        case RIGHT:
                            return Direction.EAST;
                    }
            }
            return Direction.UP;
        }
    }

}
