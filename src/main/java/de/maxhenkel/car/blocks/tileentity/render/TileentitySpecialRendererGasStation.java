package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.phys.AABB;

public class TileentitySpecialRendererGasStation implements BlockEntityRenderer<TileEntityGasStation> {

    private Minecraft minecraft;
    protected BlockEntityRendererProvider.Context renderer;

    public TileentitySpecialRendererGasStation(BlockEntityRendererProvider.Context renderer) {
        this.renderer = renderer;
        minecraft = Minecraft.getInstance();
    }

    @Override
    public void render(TileEntityGasStation target, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        if (!target.hasLevel()) {
            return;
        }

        String name = target.getRenderText();

        if (name == null || name.isEmpty()) {
            return;
        }

        matrixStack.pushPose();
        matrixStack.translate(0.5D, 1D, 0.5D);
        matrixStack.mulPose(Axis.XP.rotationDegrees(180F));

        Direction dir = target.getDirection();

        matrixStack.mulPose(Axis.YP.rotationDegrees(dir.toYRot()));

        Font font = renderer.getFont();

        int textWidth = font.width(name);
        float textScale = 0.36F / textWidth;
        textScale = Math.min(textScale, 0.01F);

        float posX = -(textScale * textWidth) / 2F;

        matrixStack.translate(posX, -0.815D, -0.188D);

        matrixStack.scale(textScale, textScale, textScale);

        font.drawInBatch(name, 0F, 0F, 0x0, false, matrixStack.last().pose(), buffer, false, 0, combinedLightIn);
        matrixStack.popPose();

        if (minecraft.getEntityRenderDispatcher().shouldRenderHitBoxes() && !Minecraft.getInstance().showOnlyReducedInfo()) {
            matrixStack.pushPose();
            renderBoundingBox(target, partialTicks, matrixStack, buffer, combinedLightIn, combinedOverlayIn);
            matrixStack.popPose();
        }
    }

    public void renderBoundingBox(TileEntityGasStation target, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int combinedLightIn, int combinedOverlayIn) {
        AABB axisalignedbb = target.getDetectionBox().move(-target.getBlockPos().getX(), -target.getBlockPos().getY(), -target.getBlockPos().getZ());
        LevelRenderer.renderLineBox(matrixStack, buffer.getBuffer(RenderType.lines()), axisalignedbb, 0F, 0F, 1F, 1F);
    }

}
