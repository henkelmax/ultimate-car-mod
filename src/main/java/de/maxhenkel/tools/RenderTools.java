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
        MatrixStack.Entry entry = matrixStack.getLast();
        builder.pos(entry.getPositionMatrix(), posX, posY, posZ)
                .color((int) (red * 255F), (int) (green * 255F), (int) (blue * 255F), 255)
                .tex(texX, texY)
                .overlay(OverlayTexture.DEFAULT_LIGHT)
                .lightmap(light)
                .normal(entry.getNormalMatrix(), norX, norY, norZ)
                .endVertex();
    }

}
