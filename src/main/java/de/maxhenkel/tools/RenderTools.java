package de.maxhenkel.tools;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.math.Vec2f;

public class RenderTools {

    public static void vertex(IVertexBuilder builder, MatrixStack matrixStack, Vector3f position, Vec2f texCoord, Vector3f normal, int light) {
        vertex(builder, matrixStack, position.getX(), position.getY(), position.getZ(), texCoord.x, texCoord.y, normal.getX(), normal.getY(), normal.getZ(), 1F, 1F, 1F, light);
    }

    public static void vertex(IVertexBuilder builder, MatrixStack matrixStack, float posX, float posY, float posZ, float texX, float texY, float red, float green, float blue, int light) {
        vertex(builder, matrixStack, posX, posY, posZ, texX, texY, 0F, 0F, -1F, red, green, blue, light);
    }

    public static void vertex(IVertexBuilder builder, MatrixStack matrixStack, float posX, float posY, float posZ, float texX, float texY, int light) {
        vertex(builder, matrixStack, posX, posY, posZ, texX, texY, 0F, 0F, -1F, 1F, 1F, 1F, light);
    }

    public static void vertex(IVertexBuilder builder, MatrixStack matrixStack, float posX, float posY, float posZ, float texX, float texY, float norX, float norY, float norZ, float red, float green, float blue, int light) {
        MatrixStack.Entry entry = matrixStack.func_227866_c_();
        builder.func_227888_a_(entry.func_227870_a_(), posX, posY, posZ) // Matrix and position?
                .func_225586_a_((int) (red * 255F), (int) (green * 255F), (int) (blue * 255F), 255) // Color
                .func_225583_a_(texX, texY) // U V
                .func_227891_b_(OverlayTexture.field_229196_a_) //Overlay Texture
                .func_227886_a_(light) // Light
                .func_227887_a_(entry.func_227872_b_(), norX, norY, norZ) // normal
                .endVertex();
    }

}
