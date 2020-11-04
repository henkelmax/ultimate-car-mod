package de.maxhenkel.car.entity.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.client.obj.OBJEntityRenderer;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;

import java.util.List;

public class GenericCarModel extends OBJEntityRenderer<EntityGenericCar> {

    public GenericCarModel(EntityRendererManager renderManager) {
        super(renderManager);
    }

    @Override
    public List<OBJModelInstance<EntityGenericCar>> getModels(EntityGenericCar entity) {
        return entity.getModels();
    }

    @Override
    public void render(EntityGenericCar entity, float yaw, float partialTicks, MatrixStack matrixStack, IRenderTypeBuffer buffer, int packedLight) {
        super.render(entity, yaw, partialTicks, matrixStack, buffer, packedLight);
        matrixStack.push();

        String text = entity.getLicensePlate();
        if (text != null && !text.isEmpty()) {
            matrixStack.push();
            RenderSystem.color4f(1F, 1F, 1F, 1F);
            drawLicensePlate(entity, text, matrixStack, buffer, partialTicks, packedLight);
            matrixStack.pop();
        }

        matrixStack.pop();
    }

    private void drawLicensePlate(EntityGenericCar car, String txt, MatrixStack matrixStack, IRenderTypeBuffer buffer, float partialTicks, int packedLight) {
        matrixStack.push();
        matrixStack.scale(1F, -1F, 1F);

        translateLicensePlate(car, matrixStack, partialTicks);

        int textWidth = Minecraft.getInstance().fontRenderer.getStringWidth(txt);
        float textScale = 0.01F;

        matrixStack.translate(-(textScale * textWidth) / 2F, 0F, 0F);

        matrixStack.scale(textScale, textScale, textScale);

        Minecraft.getInstance().fontRenderer.renderString(txt, 0F, 0F, 0xFFFFFF, false, matrixStack.getLast().getMatrix(), buffer, false, 0, packedLight);

        matrixStack.pop();
    }

    protected void translateLicensePlate(EntityGenericCar entity, MatrixStack matrixStack, float partialTicks) {
        Vector3d offset = entity.getLicensePlateOffset();
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180F - entity.getYaw(partialTicks)));
        matrixStack.translate(offset.x, offset.y, offset.z);
    }

}