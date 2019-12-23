package de.maxhenkel.car.blocks.tileentity.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import de.maxhenkel.car.blocks.BlockSign;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;

public class TileEntitySpecialRendererSign extends TileEntityRenderer<TileEntitySign> {

    public TileEntitySpecialRendererSign(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void func_225616_a_(TileEntitySign te, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light, int i) {
        matrixStack.func_227860_a_();

        matrixStack.func_227861_a_(0.5D, 1D, 0.5D);
        matrixStack.func_227863_a_(Vector3f.field_229179_b_.func_229187_a_(180F));
        matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(-te.getBlockState().get(BlockSign.FACING).getHorizontalAngle()));

        //----------Front-----------
        matrixStack.func_227860_a_();

        matrixStack.func_227861_a_(0D, 0.27D, -0.51D / 16D);
        drawText(te.getText(0), matrixStack, buffer, light);
        matrixStack.func_227861_a_(0D, 0.116D, 0D);
        drawText(te.getText(1), matrixStack, buffer, light);
        matrixStack.func_227861_a_(0D, 0.116D, 0D);
        drawText(te.getText(2), matrixStack, buffer, light);
        matrixStack.func_227861_a_(0D, 0.116D, 0D);
        drawText(te.getText(3), matrixStack, buffer, light);

        matrixStack.func_227865_b_();

        //----------Back-----------
        matrixStack.func_227860_a_();

        matrixStack.func_227863_a_(Vector3f.field_229181_d_.func_229187_a_(180F));
        matrixStack.func_227861_a_(0D, 0.27D, -0.51D / 16D);
        drawText(te.getText(4), matrixStack, buffer, light);
        matrixStack.func_227861_a_(0D, 0.116D, 0D);
        drawText(te.getText(5), matrixStack, buffer, light);
        matrixStack.func_227861_a_(0D, 0.116D, 0D);
        drawText(te.getText(6), matrixStack, buffer, light);
        matrixStack.func_227861_a_(0D, 0.116D, 0D);
        drawText(te.getText(7), matrixStack, buffer, light);

        matrixStack.func_227865_b_();
        //-------------------------

        matrixStack.func_227865_b_();
    }

    private void drawText(String txt, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {
        FontRenderer renderer = field_228858_b_.getFontRenderer();
        matrixStack.func_227860_a_();

        int textWidth = renderer.getStringWidth(txt);
        float textScale = 0.008F;

        matrixStack.func_227861_a_(-(textScale * textWidth) / 2D, 0D, 0D);
        matrixStack.func_227862_a_(textScale, textScale, textScale);

        renderer.func_228079_a_(txt, 0F, 0F, 0x0, false, matrixStack.func_227866_c_().func_227870_a_(), buffer, false, 0, light);

        matrixStack.func_227865_b_();
    }

}
