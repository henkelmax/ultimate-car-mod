package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.util.Direction;

public class TileentitySpecialRendererFuelStation extends TileEntityRenderer<TileEntityFuelStation> {

    public TileentitySpecialRendererFuelStation(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void func_225616_a_(TileEntityFuelStation target, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int i) {
        String name = target.getRenderText();

        if (name == null || name.isEmpty()) {
            return;
        }

        matrixStack.func_227860_a_();
        matrixStack.func_227861_a_(0.5D, 1D, 0.5D);
        matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180F));

        Direction dir = target.getDirection();

        matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(dir.getHorizontalAngle()));

        FontRenderer renderer = field_228858_b_.getFontRenderer();

        if (renderer != null) {
            int textWidth = renderer.getStringWidth(name);
            float textScale = 0.36F / textWidth;
            textScale = Math.min(textScale, 0.01F);

            float posX = -(textScale * textWidth) / 2F;

            matrixStack.func_227861_a_(posX, -0.815D, -0.188D);

            matrixStack.func_227862_a_(textScale, textScale, textScale);
            renderer.drawString(name, 0, 0, 0);
            renderer.func_228079_a_(name, 0F, 0F, 0x0, false, matrixStack.func_227866_c_().func_227870_a_(), buffer, false, 0, light);
        }
        matrixStack.func_227865_b_();
    }

}
