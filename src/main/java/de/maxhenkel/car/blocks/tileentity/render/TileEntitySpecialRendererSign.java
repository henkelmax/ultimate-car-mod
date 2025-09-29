package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.maxhenkel.car.blocks.BlockSign;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class TileEntitySpecialRendererSign implements BlockEntityRenderer<TileEntitySign, SignRenderState> {

    protected BlockEntityRendererProvider.Context renderer;

    public TileEntitySpecialRendererSign(BlockEntityRendererProvider.Context renderer) {
        this.renderer = renderer;
    }

    @Override
    public SignRenderState createRenderState() {
        return new SignRenderState();
    }

    @Override
    public void extractRenderState(TileEntitySign sign, SignRenderState state, float partialTicks, Vec3 pos, @Nullable ModelFeatureRenderer.CrumblingOverlay crumblingOverlay) {
        BlockEntityRenderer.super.extractRenderState(sign, state, partialTicks, pos, crumblingOverlay);
        state.direction = sign.getBlockState().getValue(BlockSign.FACING);
        for (int i = 0; i < state.text.length; i++) {
            state.text[i] = Component.literal(sign.getText(i)).getVisualOrderText();
        }
    }

    @Override
    public void submit(SignRenderState state, PoseStack stack, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        stack.pushPose();

        stack.translate(0.5D, 1D, 0.5D);
        stack.mulPose(Axis.XP.rotationDegrees(180F));
        stack.mulPose(Axis.YP.rotationDegrees(-state.direction.toYRot()));

        //----------Front-----------
        stack.pushPose();

        stack.translate(0D, 0.27D, -0.51D / 16D);
        drawText(state.text[0], state, stack, collector);
        stack.translate(0D, 0.116D, 0D);
        drawText(state.text[1], state, stack, collector);
        stack.translate(0D, 0.116D, 0D);
        drawText(state.text[2], state, stack, collector);
        stack.translate(0D, 0.116D, 0D);
        drawText(state.text[3], state, stack, collector);

        stack.popPose();

        //----------Back-----------
        stack.pushPose();

        stack.mulPose(Axis.YP.rotationDegrees(180F));
        stack.translate(0D, 0.27D, -0.51D / 16D);
        drawText(state.text[4], state, stack, collector);
        stack.translate(0D, 0.116D, 0D);
        drawText(state.text[5], state, stack, collector);
        stack.translate(0D, 0.116D, 0D);
        drawText(state.text[6], state, stack, collector);
        stack.translate(0D, 0.116D, 0D);
        drawText(state.text[7], state, stack, collector);

        stack.popPose();
        //-------------------------

        stack.popPose();
    }

    private void drawText(FormattedCharSequence text, SignRenderState state, PoseStack stack, SubmitNodeCollector collector) {
        Font font = renderer.font();
        stack.pushPose();

        int textWidth = font.width(text);
        float textScale = 0.008F;

        stack.translate(-(textScale * textWidth) / 2D, 0D, 0D);
        stack.scale(textScale, textScale, textScale);

        collector.submitText(stack, 0F, 0F, text, false, Font.DisplayMode.NORMAL, state.lightCoords, 0xFF000000, 0, 0);

        stack.popPose();
    }

}
