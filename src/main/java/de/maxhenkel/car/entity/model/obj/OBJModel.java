package de.maxhenkel.car.entity.model.obj;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import de.maxhenkel.tools.RenderTools;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.Vector3f;
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
        matrixStack.func_227860_a_();

        IVertexBuilder builder = buffer.getBuffer(getRenderType(texture, true));

        for (int i = 0; i < data.faces.size(); i++) {
            int[][] face = data.faces.get(i);
            RenderTools.vertex(builder, matrixStack, data.positions.get(face[0][0]), data.texCoords.get(face[0][1]), data.normals.get(face[0][2]), light);
            RenderTools.vertex(builder, matrixStack, data.positions.get(face[1][0]), data.texCoords.get(face[1][1]), data.normals.get(face[1][2]), light);
            RenderTools.vertex(builder, matrixStack, data.positions.get(face[2][0]), data.texCoords.get(face[2][1]), data.normals.get(face[2][2]), light);
        }
        matrixStack.func_227865_b_();
    }

    @OnlyIn(Dist.CLIENT)
    private static RenderType getRenderType(ResourceLocation resourceLocation, boolean culling) {
        RenderType.State state = RenderType.State
                .func_228694_a_()
                .func_228724_a_(new RenderState.TextureState(resourceLocation, false, false))
                .func_228726_a_(new RenderState.TransparencyState("no_transparency", () -> {
                    RenderSystem.disableBlend();
                }, () -> {
                }))
                .func_228716_a_(new RenderState.DiffuseLightingState(true))
                .func_228713_a_(new RenderState.AlphaState(0.003921569F))
                .func_228719_a_(new RenderState.LightmapState(true))
                .func_228722_a_(new RenderState.OverlayState(true))
                .func_228714_a_(new RenderState.CullState(culling))
                .func_228728_a_(true);
        return RenderType.func_228633_a_("entity_cutout", DefaultVertexFormats.field_227849_i_, GL11.GL_TRIANGLES, 256, true, false, state);
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
