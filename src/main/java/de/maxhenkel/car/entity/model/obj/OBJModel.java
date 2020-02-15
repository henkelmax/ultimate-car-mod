package de.maxhenkel.car.entity.model.obj;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.maxhenkel.tools.RenderTools;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec2f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.opengl.GL11;

import java.util.List;

public class OBJModel {

    private ResourceLocation model;

    private OBJModelData data;

    public OBJModel(ResourceLocation model) {
        this.model = model;
    }

    @OnlyIn(Dist.CLIENT)
    private void load() {
        if (data == null) {
            data = OBJLoader.load(model);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void render(ResourceLocation texture, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {
        load();
        matrixStack.push();

        IVertexBuilder builder = buffer.getBuffer(getRenderType(texture, true));

        for (int i = 0; i < data.faces.size(); i++) {
            int[][] face = data.faces.get(i);
            RenderTools.vertex(builder, matrixStack, data.positions.get(face[0][0]), data.texCoords.get(face[0][1]), data.normals.get(face[0][2]), light, OverlayTexture.DEFAULT_LIGHT);
            RenderTools.vertex(builder, matrixStack, data.positions.get(face[1][0]), data.texCoords.get(face[1][1]), data.normals.get(face[1][2]), light, OverlayTexture.DEFAULT_LIGHT);
            RenderTools.vertex(builder, matrixStack, data.positions.get(face[2][0]), data.texCoords.get(face[2][1]), data.normals.get(face[2][2]), light, OverlayTexture.DEFAULT_LIGHT);
        }
        matrixStack.pop();
    }

    @OnlyIn(Dist.CLIENT)
    private static RenderType getRenderType(ResourceLocation resourceLocation, boolean culling) {
        RenderType.State state = RenderType.State
                .builder()
                .texture(new RenderState.TextureState(resourceLocation, false, false))
                .transparency(new RenderState.TransparencyState("no_transparency", () -> {
                    RenderSystem.disableBlend();
                }, () -> {
                }))
                .diffuseLighting(new RenderState.DiffuseLightingState(true))
                .alpha(new RenderState.AlphaState(0.003921569F))
                .lightmap(new RenderState.LightmapState(true))
                .overlay(new RenderState.OverlayState(true))
                .cull(new RenderState.CullState(culling))
                .build(true);
        return RenderType.get("entity_cutout", DefaultVertexFormats.ITEM, GL11.GL_TRIANGLES, 256, true, false, state);
    }

    static class OBJModelData {
        private List<Vector3f> positions;
        private List<Vec2f> texCoords;
        private List<Vector3f> normals;
        private List<int[][]> faces;

        public OBJModelData(List<Vector3f> positions, List<Vec2f> texCoords, List<Vector3f> normals, List<int[][]> faces) {
            this.positions = positions;
            this.texCoords = texCoords;
            this.normals = normals;
            this.faces = faces;
        }

        public List<Vector3f> getPositions() {
            return positions;
        }

        public List<Vec2f> getTexCoords() {
            return texCoords;
        }

        public List<Vector3f> getNormals() {
            return normals;
        }

        public List<int[][]> getFaces() {
            return faces;
        }
    }

}
