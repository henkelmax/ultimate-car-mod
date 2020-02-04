package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.tools.RenderTools;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;

public class TileEntitySpecialRendererTank extends TileEntityRenderer<TileEntityTank> {

    public static final ResourceLocation LOCATION_TANK = new ResourceLocation(Main.MODID, "textures/block/tank_line.png");

    public TileEntitySpecialRendererTank(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(TileEntityTank te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int i) {
        matrixStack.push();
        float amount = te.getFillPercent();
        FluidStack stack = te.getFluid();
        if (amount > 0 && stack != null) {
            renderFluid(te, stack, amount, 0.0F, matrixStack, buffer, light);
        }
        renderLines(te, matrixStack, buffer, light);
        matrixStack.pop();
    }

    public void renderFluid(TileEntityTank tank, FluidStack fluid, float amount, float yStart, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {
        matrixStack.push();
        IVertexBuilder builder = buffer.getBuffer(Atlases.getTranslucentBlockType());

        TextureAtlasSprite texture = Minecraft.getInstance().getModelManager().getAtlasTexture(PlayerContainer.LOCATION_BLOCKS_TEXTURE).getSprite(fluid.getFluid().getAttributes().getStillTexture());

        float uMin = texture.getMinU();
        float uMax = texture.getMaxU();
        float vMin = texture.getMinV();
        float vMax = texture.getMaxV();

        float vHeight = vMax - vMin;

        int i = 0xFFFFFF;
        if (tank.hasWorld()) {
            i = fluid.getFluid().getAttributes().getColor(tank.getWorld(), tank.getPos());
        }

        float red = (float) (i >> 16 & 255) / 255F;
        float green = (float) (i >> 8 & 255) / 255F;
        float blue = (float) (i & 255) / 255F;

        float s = 0.0F;

        if (!tank.isFluidConnected(Direction.NORTH)) {
            // North
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart, 0F + s, uMax, vMin, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F - s, yStart, 0F + s, uMin, vMin, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, red, green, blue, light);
        }

        if (!tank.isFluidConnected(Direction.SOUTH)) {
            // South
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart, 1F - s, uMin, vMin, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart, 1F - s, uMax, vMin, red, green, blue, light);
        }

        if (!tank.isFluidConnected(Direction.EAST)) {
            // East
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart, 0F + s, uMin, vMin, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart, 1F - s, uMax, vMin, red, green, blue, light);
        }

        if (!tank.isFluidConnected(Direction.WEST)) {
            // West
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart, 1F - s, uMin, vMin, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart, 0F + s, uMax, vMin, red, green, blue, light);
        }

        if (!tank.isFluidConnected(Direction.DOWN)) {
            // Down
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart, 0F + s, uMax, vMin, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart, 1F - s, uMin, vMin, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart, 1F - s, uMin, vMax, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart, 0F + s, uMax, vMax, red, green, blue, light);
        }

        if (!tank.isFluidConnected(Direction.UP)) {
            // Up
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMax, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMax, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin, red, green, blue, light);
            RenderTools.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin, red, green, blue, light);
        }

        matrixStack.pop();
    }

    public static void renderLines(TileEntityTank te, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {
        IVertexBuilder builder = buffer.getBuffer(RenderType.entityCutout(LOCATION_TANK));
        for (Direction facing : Direction.values()) {
            if (!te.isTankConnectedTo(facing)) {
                for (EnumDirection direction : EnumDirection.values()) {
                    if (!te.isTankConnectedTo(direction.to(facing))) {
                        drawLine(facing, direction, matrixStack, buffer, builder, light);
                    }
                }
            }
        }
    }

    public static void drawLine(Direction side, EnumDirection line, MatrixStack matrixStack, IRenderTypeBuffer buffer, IVertexBuilder builder, int light) {
        matrixStack.push();

        rotate(side, matrixStack);

        matrixStack.translate(-0.00025D, -0.00025D, -0.00025D);

        drawSide(line, side, matrixStack, buffer, builder, light);

        matrixStack.pop();
    }

    public static void rotate(Direction facing, MatrixStack matrixStack) {
        matrixStack.translate(0.5D, 0.5D, 0.5D);

        switch (facing) {
            case SOUTH:
                matrixStack.rotate(Vector3f.YP.rotationDegrees(180F));
                break;
            case EAST:
                matrixStack.rotate(Vector3f.YP.rotationDegrees(270F));
                break;
            case WEST:
                matrixStack.rotate(Vector3f.YP.rotationDegrees(90F));
                break;
            case UP:
                matrixStack.rotate(Vector3f.YP.rotationDegrees(180F));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(90F));
                break;
            case DOWN:
                matrixStack.rotate(Vector3f.YP.rotationDegrees(180F));
                matrixStack.rotate(Vector3f.XP.rotationDegrees(270F));
                break;
            case NORTH:
            default:
                break;
        }
        matrixStack.translate(-0.5D, -0.5D, -0.5D);
    }

    public static void drawSide(EnumDirection line, Direction side, MatrixStack matrixStack, IRenderTypeBuffer buffer, IVertexBuilder builder, int light) {
        switch (line) {
            case UP:
                // Top
                RenderTools.vertex(builder, matrixStack, 0F, 0F, 0F, 1F, 1F, light);
                RenderTools.vertex(builder, matrixStack, 0F, 1F, 0F, 1F, 0F, light);
                RenderTools.vertex(builder, matrixStack, 1F, 1F, 0F, 0F, 0F, light);
                RenderTools.vertex(builder, matrixStack, 1F, 0F, 0F, 0F, 1F, light);
                break;
            case DOWN:
                // Bottom
                RenderTools.vertex(builder, matrixStack, 0F, 0F, 0F, 0F, 0F, light);
                RenderTools.vertex(builder, matrixStack, 0F, 1F, 0F, 0F, 1F, light);
                RenderTools.vertex(builder, matrixStack, 1F, 1F, 0F, 1F, 1F, light);
                RenderTools.vertex(builder, matrixStack, 1F, 0F, 0F, 1F, 0F, light);
                break;
            case RIGHT:
                // Right
                RenderTools.vertex(builder, matrixStack, 0F, 0F, 0F, 1F, 0F, light);
                RenderTools.vertex(builder, matrixStack, 0F, 1F, 0F, 0F, 0F, light);
                RenderTools.vertex(builder, matrixStack, 1F, 1F, 0F, 0F, 1F, light);
                RenderTools.vertex(builder, matrixStack, 1F, 0F, 0F, 1F, 1F, light);
                break;
            case LEFT:
                // Left
                RenderTools.vertex(builder, matrixStack, 0F, 0F, 0F, 0F, 1F, light);
                RenderTools.vertex(builder, matrixStack, 0F, 1F, 0F, 1F, 1F, light);
                RenderTools.vertex(builder, matrixStack, 1F, 1F, 0F, 1F, 0F, light);
                RenderTools.vertex(builder, matrixStack, 1F, 0F, 0F, 0F, 0F, light);
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
