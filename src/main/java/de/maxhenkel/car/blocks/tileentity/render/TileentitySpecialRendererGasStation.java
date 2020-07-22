package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.vector.Vector3f;

public class TileentitySpecialRendererGasStation extends TileEntityRenderer<TileEntityGasStation> {

    private Minecraft minecraft;

    public TileentitySpecialRendererGasStation(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
        minecraft = Minecraft.getInstance();
    }

    @Override
    public void render(TileEntityGasStation target, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        String name = target.getRenderText();

        if (name == null || name.isEmpty()) {
            return;
        }

        matrixStack.push();
        matrixStack.translate(0.5D, 1D, 0.5D);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(180F));

        Direction dir = target.getDirection();

        matrixStack.rotate(Vector3f.YP.rotationDegrees(dir.getHorizontalAngle()));

        FontRenderer renderer = renderDispatcher.getFontRenderer();

        int textWidth = renderer.getStringWidth(name);
        float textScale = 0.36F / textWidth;
        textScale = Math.min(textScale, 0.01F);

        float posX = -(textScale * textWidth) / 2F;

        matrixStack.translate(posX, -0.815D, -0.188D);

        matrixStack.scale(textScale, textScale, textScale);

        renderer.renderString(name, 0F, 0F, 0x0, false, matrixStack.getLast().getMatrix(), buffer, false, 0, combinedLightIn);
        matrixStack.pop();

        if (minecraft.getRenderManager().isDebugBoundingBox() && !Minecraft.getInstance().isReducedDebug()) {
            matrixStack.push();
            renderBoundingBox(target, partialTicks, matrixStack, buffer, combinedLightIn, combinedOverlayIn);
            matrixStack.pop();
        }
    }

    public void renderBoundingBox(TileEntityGasStation target, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int combinedLightIn, int combinedOverlayIn) {
        AxisAlignedBB axisalignedbb = target.getDetectionBox().offset(-target.getPos().getX(), -target.getPos().getY(), -target.getPos().getZ());
        WorldRenderer.drawBoundingBox(matrixStack, buffer.getBuffer(RenderType.getLines()), axisalignedbb, 0F, 0F, 1F, 1F);
    }

}
