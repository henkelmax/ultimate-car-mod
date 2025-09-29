package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import de.maxhenkel.car.blocks.tileentity.TileEntitySplitTank;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.corelib.client.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.data.AtlasIds;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.jetbrains.annotations.Nullable;

public class TileEntitySpecialRendererSplitTank implements BlockEntityRenderer<TileEntitySplitTank, SplitTankRenderState> {

    private static final FluidStack BIO_DIESEL_STACK = new FluidStack(ModFluids.BIO_DIESEL.get(), 1);
    private static final FluidStack GLYCERIN_STACK = new FluidStack(ModFluids.GLYCERIN.get(), 1);

    protected BlockEntityRendererProvider.Context renderer;

    public TileEntitySpecialRendererSplitTank(BlockEntityRendererProvider.Context renderer) {
        this.renderer = renderer;
    }

    @Override
    public SplitTankRenderState createRenderState() {
        return new SplitTankRenderState();
    }

    @Override
    public void extractRenderState(TileEntitySplitTank tank, SplitTankRenderState state, float partialTicks, Vec3 pos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(tank, state, partialTicks, pos, crumblingOverlay);
        state.bioDiesel = tank.getBioDieselPerc() / 2F;
        state.glycerin = tank.getGlycerinPerc() / 2F;
    }

    @Override
    public void submit(SplitTankRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        stack.pushPose();

        if (state.bioDiesel > 0) {
            renderFluid(BIO_DIESEL_STACK, state.bioDiesel, 1F / 16F, stack, state.lightCoords, OverlayTexture.NO_OVERLAY, collector);
        }

        if (state.glycerin > 0) {
            renderFluid(GLYCERIN_STACK, state.glycerin, state.bioDiesel + 1F / 16F, stack, state.lightCoords, OverlayTexture.NO_OVERLAY, collector);
        }
        stack.popPose();
    }

    public void renderFluid(FluidStack fluid, float amount, float yStart, PoseStack stack, int light, int overlay, SubmitNodeCollector collector) {
        stack.pushPose();
        stack.scale(0.98F, 0.98F, 0.98F);
        stack.translate(0.01F, 0.01F, 0.01F);

        IClientFluidTypeExtensions type = IClientFluidTypeExtensions.of(fluid.getFluid());
        TextureAtlasSprite texture = Minecraft.getInstance().getAtlasManager().getAtlasOrThrow(AtlasIds.BLOCKS).getSprite(type.getStillTexture(fluid));

        collector.submitCustomGeometry(stack, RenderType.entityTranslucent(TextureAtlas.LOCATION_BLOCKS), (pose, vertexConsumer) -> {
            float uMin = texture.getU0();
            float uMax = texture.getU1();
            float vMin = texture.getV0();
            float vMax = texture.getV1();

            float vHeight = vMax - vMin;

            float s = 0F;

            // North
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 0F + s, uMax, vMin, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 0F + s, uMin, vMin, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, light, overlay);

            // South
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 1F - s, uMin, vMin, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 1F - s, uMax, vMin, light, overlay);

            // East
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 0F + s, uMin, vMin, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 0F + s, uMin, vMin + vHeight * amount, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 1F - s, uMax, vMin + vHeight * amount, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart, 1F - s, uMax, vMin, light, overlay);

            // West
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 1F - s, uMin, vMin, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMin + vHeight * amount, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMin + vHeight * amount, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart, 0F + s, uMax, vMin, light, overlay);

            // Up
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 0F + s, uMax, vMax, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 0F + s, yStart + amount - s * 2F, 1F - s, uMin, vMax, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 1F - s, uMin, vMin, light, overlay);
            RenderUtils.vertex(vertexConsumer, pose, 1F - s, yStart + amount - s * 2F, 0F + s, uMax, vMin, light, overlay);
        });

        stack.popPose();
    }

}
