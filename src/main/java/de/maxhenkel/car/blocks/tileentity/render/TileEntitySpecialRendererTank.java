package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.corelib.client.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.inventory.container.PlayerContainer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.fluids.FluidStack;

public class TileEntitySpecialRendererTank extends TileEntityRenderer<TileEntityTank> {

    public static final ResourceLocation LOCATION_TANK = new ResourceLocation(Main.MODID, "textures/block/tank_line.png");

    public TileEntitySpecialRendererTank(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(TileEntityTank te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int overlay) {
        matrixStack.pushPose();
        float amount = te.getFillPercent();
        FluidStack stack = te.getFluid();
        if (amount > 0 && stack != null) {
            renderFluid(te, stack, amount, 0.0F, matrixStack, buffer, light, overlay);
        }
        renderLines(te, matrixStack, buffer, light, overlay);
        matrixStack.popPose();
    }

    public void renderFluid(TileEntityTank tank, FluidStack fluid, float amount, float yStart, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int overlay) {
        matrixStack.pushPose();
        IVertexBuilder builder = buffer.getBuffer(Atlases.translucentCullBlockSheet());

        TextureAtlasSprite texture = Minecraft.getInstance().getModelManager().getAtlas(PlayerContainer.BLOCK_ATLAS).getSprite(fluid.getFluid().getAttributes().getStillTexture());

        float uMin = texture.getU0();
        float uMax = texture.getU1();
        float vMin = texture.getV0();
        float vMax = texture.getV1();

        float vHeight = vMax - vMin;

        int i;
        if (tank.hasLevel()) {
            i = fluid.getFluid().getAttributes().getColor(tank.getLevel(), tank.getBlockPos());
        } else {
            i = fluid.getFluid().getAttributes().getColor();
        }

        int red = i >> 16 & 255;
        int green = i >> 8 & 255;
        int blue = i & 255;

        float s = 0.0F;

        if (!tank.isFluidConnected(Direction.NORTH)) {
            // North
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 0F + s, uMax, vMin, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F - s, yStart, 0F + s, uMin, vMin, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, red, green, blue, light, overlay);
        }

        if (!tank.isFluidConnected(Direction.SOUTH)) {
            // South
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 1F - s, uMin, vMin, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 1F - s, uMax, vMin, red, green, blue, light, overlay);
        }

        if (!tank.isFluidConnected(Direction.EAST)) {
            // East
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 0F + s, uMin, vMin, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 1F - s, uMax, vMin, red, green, blue, light, overlay);
        }

        if (!tank.isFluidConnected(Direction.WEST)) {
            // West
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 1F - s, uMin, vMin, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 0F + s, uMax, vMin, red, green, blue, light, overlay);
        }

        if (!tank.isFluidConnected(Direction.DOWN)) {
            // Down
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 0F + s, uMax, vMin, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart, 1F - s, uMin, vMin, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 1F - s, uMin, vMax, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart, 0F + s, uMax, vMax, red, green, blue, light, overlay);
        }

        if (!tank.isFluidConnected(Direction.UP)) {
            // Up
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMax, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMax, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin, red, green, blue, light, overlay);
            RenderUtils.vertex(builder, matrixStack, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin, red, green, blue, light, overlay);
        }

        matrixStack.popPose();
    }

    public static void renderLines(TileEntityTank te, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int overlay) {
        IVertexBuilder builder = buffer.getBuffer(RenderType.entityCutout(LOCATION_TANK));
        for (Direction facing : Direction.values()) {
            if (!te.isTankConnectedTo(facing)) {
                for (EnumDirection direction : EnumDirection.values()) {
                    if (!te.isTankConnectedTo(direction.to(facing))) {
                        drawLine(facing, direction, matrixStack, buffer, builder, light, overlay);
                    }
                }
            }
        }
    }

    public static void drawLine(Direction side, EnumDirection line, MatrixStack matrixStack, IRenderTypeBuffer buffer, IVertexBuilder builder, int light, int overlay) {
        matrixStack.pushPose();

        rotate(side, matrixStack);

        matrixStack.translate(-0.00025D, -0.00025D, -0.00025D);

        drawSide(line, side, matrixStack, buffer, builder, light, overlay);

        matrixStack.popPose();
    }

    public static void rotate(Direction facing, MatrixStack matrixStack) {
        matrixStack.translate(0.5D, 0.5D, 0.5D);

        switch (facing) {
            case SOUTH:
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
                break;
            case EAST:
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(270F));
                break;
            case WEST:
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(90F));
                break;
            case UP:
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(90F));
                break;
            case DOWN:
                matrixStack.mulPose(Vector3f.YP.rotationDegrees(180F));
                matrixStack.mulPose(Vector3f.XP.rotationDegrees(270F));
                break;
            case NORTH:
            default:
                break;
        }
        matrixStack.translate(-0.5D, -0.5D, -0.5D);
    }

    public static void drawSide(EnumDirection line, Direction side, MatrixStack matrixStack, IRenderTypeBuffer buffer, IVertexBuilder builder, int light, int overlay) {
        switch (line) {
            case UP:
                // Top
                RenderUtils.vertex(builder, matrixStack, 0F, 0F, 0F, 1F, 1F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 0F, 1F, 0F, 1F, 0F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 1F, 1F, 0F, 0F, 0F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 1F, 0F, 0F, 0F, 1F, light, overlay);
                break;
            case DOWN:
                // Bottom
                RenderUtils.vertex(builder, matrixStack, 0F, 0F, 0F, 0F, 0F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 0F, 1F, 0F, 0F, 1F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 1F, 1F, 0F, 1F, 1F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 1F, 0F, 0F, 1F, 0F, light, overlay);
                break;
            case RIGHT:
                // Right
                RenderUtils.vertex(builder, matrixStack, 0F, 0F, 0F, 1F, 0F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 0F, 1F, 0F, 0F, 0F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 1F, 1F, 0F, 0F, 1F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 1F, 0F, 0F, 1F, 1F, light, overlay);
                break;
            case LEFT:
                // Left
                RenderUtils.vertex(builder, matrixStack, 0F, 0F, 0F, 0F, 1F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 0F, 1F, 0F, 1F, 1F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 1F, 1F, 0F, 1F, 0F, light, overlay);
                RenderUtils.vertex(builder, matrixStack, 1F, 0F, 0F, 0F, 0F, light, overlay);
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
