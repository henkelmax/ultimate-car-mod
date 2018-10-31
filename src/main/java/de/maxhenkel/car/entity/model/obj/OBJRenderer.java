package de.maxhenkel.car.entity.model.obj;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.client.model.obj.OBJModel;
import org.lwjgl.opengl.GL11;
import javax.vecmath.Vector3f;
import javax.vecmath.Vector4f;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Placeholder object renderer, until I find something better.
 * Assuming the object uses only triangle polygons.
 * Using GL_TRIANGLES only renders some of the triangles.
 * GL_TRIANGLE_STRIP makes the model look more complete, but is obviously still ugly.
 */
// https://github.com/fredtargaryen/ExRollerCoaster/blob/1.12.2/src/main/java/erc/renderer/ModelRenderer.java
public class OBJRenderer {
    /**
     * Assumes a complete OBJModelOptions with triangle polygons, so it can use GL_TRIANGLES to render
     * Necessary transforms and texture binding must be done before calling this.
     *
     * @param model
     */
    public static void renderObj(OBJModel model) {
        //Prepare to draw
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexBuffer = tessellator.getBuffer();
        //Yes it's deprecated but I don't know any alternatives
        Collection<OBJModel.Group> groups = model.getMatLib().getGroups().values();
        Iterator<OBJModel.Group> iterGroup = groups.iterator();
        renderGroups(vertexBuffer, iterGroup);
        tessellator.draw();
    }

    private static void renderGroups(BufferBuilder vertexbuffer, Iterator<OBJModel.Group> iterGroup) {
        vertexbuffer.begin(GL11.GL_TRIANGLES, DefaultVertexFormats.POSITION_TEX_NORMAL);
        while (iterGroup.hasNext()) {
            OBJModel.Group g = iterGroup.next();
            Set<OBJModel.Face> faces = g.getFaces();
            Iterator<OBJModel.Face> iterFace = faces.iterator();
            while (iterFace.hasNext()) {
                OBJModel.Face f = iterFace.next();
                OBJModel.Vertex[] vertices = f.getVertices();
                float offset = 0.0005F;
                Vector3f faceNormal = calculateFaceNormal(vertices[0], vertices[1], vertices[2]);

                OBJModel.TextureCoordinate tc0 = vertices[0].getTextureCoordinate();
                OBJModel.TextureCoordinate tc1 = vertices[1].getTextureCoordinate();
                OBJModel.TextureCoordinate tc2 = vertices[2].getTextureCoordinate();
                float averageU = (tc0.u + tc1.u + tc2.u) / 3.0F;
                float averageV = (tc0.v + tc1.v + tc2.v) / 3.0F;

                float offsetU = offset;
                float offsetV = offset;
                Vector4f pos = vertices[0].getPos();
                if (tc0.u > averageU) offsetU = -offsetU;
                if (tc0.v > averageV) offsetV = -offsetV;
                vertexbuffer.pos(pos.x, pos.y, pos.z).tex(tc0.u + offsetU, 1.0F - (tc0.v + offsetV)).normal(faceNormal.x, faceNormal.y, faceNormal.z).endVertex();
                offsetU = offset;
                offsetV = offset;
                pos = vertices[1].getPos();
                if (tc1.u > averageU) offsetU = -offsetU;
                if (tc1.v > averageV) offsetV = -offsetV;
                vertexbuffer.pos(pos.x, pos.y, pos.z).tex(tc1.u + offsetU, 1.0F - (tc1.v + offsetV)).normal(faceNormal.x, faceNormal.y, faceNormal.z).endVertex();
                offsetU = offset;
                offsetV = offset;
                pos = vertices[2].getPos();
                if (tc2.u > averageU) offsetU = -offsetU;
                if (tc2.v > averageV) offsetV = -offsetV;
                vertexbuffer.pos(pos.x, pos.y, pos.z).tex(tc2.u + offsetU, 1.0F - (tc2.v + offsetV)).normal(faceNormal.x, faceNormal.y, faceNormal.z).endVertex();
            }
        }
    }

    private static Vector3f calculateFaceNormal(OBJModel.Vertex v0, OBJModel.Vertex v1, OBJModel.Vertex v2) {
        Vector4f pos0 = v0.getPos();
        Vector4f pos1 = v1.getPos();
        Vector4f pos2 = v2.getPos();
        Vec3d vec1 = new Vec3d(pos1.x - pos0.x, pos1.y - pos0.y, pos1.z - pos0.z);
        Vec3d vec2 = new Vec3d(pos2.x - pos0.x, pos2.y - pos0.y, pos2.z - pos0.z);
        Vec3d normalVector = vec1.crossProduct(vec2).normalize();

        return new Vector3f((float) normalVector.x, (float) normalVector.y, (float) normalVector.z);
    }
}
