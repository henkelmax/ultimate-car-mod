package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;

public class TileentitySpecialRendererGasStation extends TileEntityRenderer<TileEntityGasStation> {

    public TileentitySpecialRendererGasStation(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(TileEntityGasStation target, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int i) {
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

        if (renderer != null) {
            int textWidth = renderer.getStringWidth(name);
            float textScale = 0.36F / textWidth;
            textScale = Math.min(textScale, 0.01F);

            float posX = -(textScale * textWidth) / 2F;

            matrixStack.translate(posX, -0.815D, -0.188D);

            matrixStack.scale(textScale, textScale, textScale);
            renderer.drawString(name, 0, 0, 0);
            renderer.renderString(name, 0F, 0F, 0x0, false, matrixStack.getLast().getMatrix(), buffer, false, 0, light);
        }
        matrixStack.pop();
    }

}
