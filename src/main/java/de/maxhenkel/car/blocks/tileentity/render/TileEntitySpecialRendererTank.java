package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.corelib.client.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.data.AtlasIds;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import org.jetbrains.annotations.Nullable;

public class TileEntitySpecialRendererTank implements BlockEntityRenderer<TileEntityTank, TankRenderState> {

    public static final ResourceLocation LOCATION_TANK = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "textures/block/tank_line.png");

    protected EntityModelSet entityModelSet;

    public TileEntitySpecialRendererTank(EntityModelSet entityModelSet) {
        this.entityModelSet = entityModelSet;
    }

    @Override
    public TankRenderState createRenderState() {
        return new TankRenderState();
    }

    @Override
    public void extractRenderState(TileEntityTank tank, TankRenderState state, float partialTicks, Vec3 pos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(tank, state, partialTicks, pos, crumblingOverlay);
        state.amount = tank.getFillPercent();
        state.fluid = tank.getFluid();
        for (Direction direction : Direction.values()) {
            state.fluidConnections[direction.get3DDataValue()] = tank.isFluidConnected(direction);
            state.tankConnections[direction.get3DDataValue()] = tank.isTankConnectedTo(direction);
        }

        if (!state.fluid.isEmpty()) {
            IClientFluidTypeExtensions type = IClientFluidTypeExtensions.of(state.fluid.getFluid());
            ResourceLocation stillTexture;
            if (tank.hasLevel()) {
                state.tint = type.getTintColor(state.fluid.getFluid().defaultFluidState(), tank.getLevel(), tank.getBlockPos());
                stillTexture = type.getStillTexture(state.fluid.getFluid().defaultFluidState(), tank.getLevel(), tank.getBlockPos());
            } else {
                state.tint = type.getTintColor(state.fluid);
                stillTexture = type.getStillTexture(state.fluid);
            }
            state.texture = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.BLOCKS).getSprite(stillTexture);
        } else {
            state.tint = 0;
            state.texture = null;
        }
    }

    @Override
    public void submit(TankRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        stack.pushPose();
        if (state.amount > 0F && state.texture != null) {
            renderFluid(stack, state, collector);
        }
        renderLines(stack, state, collector);
        stack.popPose();
    }

    public void renderFluid(PoseStack stack, TankRenderState state, SubmitNodeCollector collector) {
        stack.pushPose();

        boolean[] connections = state.fluidConnections;
        TextureAtlasSprite texture = state.texture;
        int tint = state.tint;
        float amount = state.amount;
        int light = state.lightCoords;
        float yStart = 0F;
        int overlay = OverlayTexture.NO_OVERLAY;
        collector.submitCustomGeometry(stack, RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS), (pose, vertexConsumer) -> {
            float uMin = texture.getU0();
            float uMax = texture.getU1();
            float vMin = texture.getV0();
            float vMax = texture.getV1();

            float vHeight = vMax - vMin;

            int red = tint >> 16 & 255;
            int green = tint >> 8 & 255;
            int blue = tint & 255;

            float s = 0F;

            if (!connections[Direction.NORTH.get3DDataValue()]) {
                // North
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 0F + s, uMax, vMin, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F - s, yStart, 0F + s, uMin, vMin, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, red, green, blue, light, overlay);
            }

            if (!connections[Direction.SOUTH.get3DDataValue()]) {
                // South
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 1F - s, uMin, vMin, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 1F - s, uMax, vMin, red, green, blue, light, overlay);
            }

            if (!connections[Direction.EAST.get3DDataValue()]) {
                // East
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 0F + s, uMin, vMin, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 1F - s, uMax, vMin, red, green, blue, light, overlay);
            }

            if (!connections[Direction.WEST.get3DDataValue()]) {
                // West
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 1F - s, uMin, vMin, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 0F + s, uMax, vMin, red, green, blue, light, overlay);
            }

            if (!connections[Direction.DOWN.get3DDataValue()]) {
                // Down
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 0F + s, uMax, vMin, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 1F - s, uMin, vMin, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 1F - s, uMin, vMax, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 0F + s, uMax, vMax, red, green, blue, light, overlay);
            }

            if (!connections[Direction.UP.get3DDataValue()]) {
                // Up
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMax, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMax, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin, red, green, blue, light, overlay);
                RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin, red, green, blue, light, overlay);
            }

            stack.popPose();
        });
    }

    public static void renderLines(PoseStack stack, TankRenderState state, SubmitNodeCollector collector) {
        boolean[] connections = state.tankConnections;
        for (Direction facing : Direction.values()) {
            if (!connections[facing.ordinal()]) {
                for (EnumDirection direction : EnumDirection.values()) {
                    if (!connections[direction.to(facing).ordinal()]) {
                        drawLine(facing, direction, stack, collector, state.lightCoords, OverlayTexture.NO_OVERLAY);
                    }
                }
            }
        }
    }

    public static void drawLine(Direction side, EnumDirection line, PoseStack stack, SubmitNodeCollector collector, int light, int overlay) {
        stack.pushPose();
        rotate(side, stack);
        stack.translate(-0.00025D, -0.00025D, -0.00025D);
        drawSide(line, stack, collector, light, overlay);
        stack.popPose();
    }

    public static void rotate(Direction facing, PoseStack matrixStack) {
        matrixStack.translate(0.5D, 0.5D, 0.5D);

        switch (facing) {
            case SOUTH:
                matrixStack.mulPose(Axis.YP.rotationDegrees(180F));
                break;
            case EAST:
                matrixStack.mulPose(Axis.YP.rotationDegrees(270F));
                break;
            case WEST:
                matrixStack.mulPose(Axis.YP.rotationDegrees(90F));
                break;
            case UP:
                matrixStack.mulPose(Axis.YP.rotationDegrees(180F));
                matrixStack.mulPose(Axis.XP.rotationDegrees(90F));
                break;
            case DOWN:
                matrixStack.mulPose(Axis.YP.rotationDegrees(180F));
                matrixStack.mulPose(Axis.XP.rotationDegrees(270F));
                break;
            case NORTH:
            default:
                break;
        }
        matrixStack.translate(-0.5D, -0.5D, -0.5D);
    }

    public static void drawSide(EnumDirection line, PoseStack stack, SubmitNodeCollector collector, int light, int overlay) {
        collector.submitCustomGeometry(stack, RenderType.entityCutout(LOCATION_TANK), (pose, c) -> {
            switch (line) {
                case UP:
                    // Top
                    RenderUtils.vertex(c, pose, 0F, 0F, 0F, 1F, 1F, light, overlay);
                    RenderUtils.vertex(c, pose, 0F, 1F, 0F, 1F, 0F, light, overlay);
                    RenderUtils.vertex(c, pose, 1F, 1F, 0F, 0F, 0F, light, overlay);
                    RenderUtils.vertex(c, pose, 1F, 0F, 0F, 0F, 1F, light, overlay);
                    break;
                case DOWN:
                    // Bottom
                    RenderUtils.vertex(c, pose, 0F, 0F, 0F, 0F, 0F, light, overlay);
                    RenderUtils.vertex(c, pose, 0F, 1F, 0F, 0F, 1F, light, overlay);
                    RenderUtils.vertex(c, pose, 1F, 1F, 0F, 1F, 1F, light, overlay);
                    RenderUtils.vertex(c, pose, 1F, 0F, 0F, 1F, 0F, light, overlay);
                    break;
                case RIGHT:
                    // Right
                    RenderUtils.vertex(c, pose, 0F, 0F, 0F, 1F, 0F, light, overlay);
                    RenderUtils.vertex(c, pose, 0F, 1F, 0F, 0F, 0F, light, overlay);
                    RenderUtils.vertex(c, pose, 1F, 1F, 0F, 0F, 1F, light, overlay);
                    RenderUtils.vertex(c, pose, 1F, 0F, 0F, 1F, 1F, light, overlay);
                    break;
                case LEFT:
                    // Left
                    RenderUtils.vertex(c, pose, 0F, 0F, 0F, 0F, 1F, light, overlay);
                    RenderUtils.vertex(c, pose, 0F, 1F, 0F, 1F, 1F, light, overlay);
                    RenderUtils.vertex(c, pose, 1F, 1F, 0F, 1F, 0F, light, overlay);
                    RenderUtils.vertex(c, pose, 1F, 0F, 0F, 0F, 0F, light, overlay);
                    break;
                default:
                    break;
            }
        });
    }

    public enum EnumDirection {
        UP, DOWN, LEFT, RIGHT;

        public Direction to(Direction facing) {
            return switch (facing) {
                case NORTH -> switch (this) {
                    case UP -> Direction.UP;
                    case DOWN -> Direction.DOWN;
                    case LEFT -> Direction.EAST;
                    case RIGHT -> Direction.WEST;
                };
                case SOUTH -> switch (this) {
                    case UP -> Direction.UP;
                    case DOWN -> Direction.DOWN;
                    case LEFT -> Direction.WEST;
                    case RIGHT -> Direction.EAST;
                };
                case EAST -> switch (this) {
                    case UP -> Direction.UP;
                    case DOWN -> Direction.DOWN;
                    case LEFT -> Direction.SOUTH;
                    case RIGHT -> Direction.NORTH;
                };
                case WEST -> switch (this) {
                    case UP -> Direction.UP;
                    case DOWN -> Direction.DOWN;
                    case LEFT -> Direction.NORTH;
                    case RIGHT -> Direction.SOUTH;
                };
                case UP -> switch (this) {
                    case UP -> Direction.NORTH;
                    case DOWN -> Direction.SOUTH;
                    case LEFT -> Direction.WEST;
                    case RIGHT -> Direction.EAST;
                };
                case DOWN -> switch (this) {
                    case UP -> Direction.SOUTH;
                    case DOWN -> Direction.NORTH;
                    case LEFT -> Direction.WEST;
                    case RIGHT -> Direction.EAST;
                };
            };
        }
    }

}
