package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.debug.DebugScreenEntries;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShapeRenderer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TileentitySpecialRendererGasStation implements BlockEntityRenderer<TileEntityGasStation, GasStationRenderState> {

    private Minecraft minecraft;
    protected BlockEntityRendererProvider.Context renderer;

    public TileentitySpecialRendererGasStation(BlockEntityRendererProvider.Context renderer) {
        this.renderer = renderer;
        minecraft = Minecraft.getInstance();
    }

    @Override
    public GasStationRenderState createRenderState() {
        return new GasStationRenderState();
    }

    @Override
    public void extractRenderState(TileEntityGasStation gasStation, GasStationRenderState state, float partialTicks, Vec3 pos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(gasStation, state, partialTicks, pos, crumblingOverlay);
        if (gasStation.hasLevel()) {
            state.text = gasStation.getRenderText().getVisualOrderText();
        } else {
            state.text = null;
        }
        state.direction = gasStation.getDirection();

        if (minecraft.debugEntries.isCurrentlyEnabled(DebugScreenEntries.ENTITY_HITBOXES)) {
            state.hitbox = gasStation.getDetectionBox().move(-gasStation.getBlockPos().getX(), -gasStation.getBlockPos().getY(), -gasStation.getBlockPos().getZ());
        } else {
            state.hitbox = null;
        }
    }

    @Override
    public void submit(GasStationRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        if (state.text == null) {
            return;
        }

        stack.pushPose();
        stack.translate(0.5D, 1D, 0.5D);
        stack.mulPose(Axis.XP.rotationDegrees(180F));

        stack.mulPose(Axis.YP.rotationDegrees(state.direction.toYRot()));

        Font font = renderer.font();

        int textWidth = font.width(state.text);
        float textScale = 0.36F / textWidth;
        textScale = Math.min(textScale, 0.01F);

        float posX = -(textScale * textWidth) / 2F;

        stack.translate(posX, -0.815D, -0.188D);

        stack.scale(textScale, textScale, textScale);

        collector.submitText(stack, 0F, 0F, state.text, false, Font.DisplayMode.NORMAL, state.lightCoords, 0xFF000000, 0, 0);
        stack.popPose();

        if (state.hitbox != null) {
            AABB hitbox = state.hitbox;
            collector.submitCustomGeometry(stack, RenderType.lines(), (pose, vertexConsumer) -> {
                ShapeRenderer.renderLineBox(pose, vertexConsumer, hitbox, 0F, 0F, 1F, 1F);
            });
        }
    }

    @Override
    public AABB getRenderBoundingBox(TileEntityGasStation blockEntity) {
        return blockEntity.getRenderBoundingBox();
    }

}
