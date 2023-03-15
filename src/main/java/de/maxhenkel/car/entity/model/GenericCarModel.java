package de.maxhenkel.car.entity.model;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.client.obj.OBJEntityRenderer;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import org.joml.Vector3d;

import java.util.List;

public class GenericCarModel extends OBJEntityRenderer<EntityGenericCar> {

    public GenericCarModel(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public List<OBJModelInstance<EntityGenericCar>> getModels(EntityGenericCar entity) {
        return entity.getModels();
    }

    @Override
    public void render(EntityGenericCar entity, float yaw, float partialTicks, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, yaw, partialTicks, matrixStack, buffer, packedLight);
        matrixStack.pushPose();

        String text = entity.getLicensePlate();
        if (text != null && !text.isEmpty()) {
            matrixStack.pushPose();
            RenderSystem.setShaderColor(1F, 1F, 1F, 1F);
            drawLicensePlate(entity, text, matrixStack, buffer, partialTicks, packedLight);
            matrixStack.popPose();
        }

        matrixStack.popPose();
    }

    private void drawLicensePlate(EntityGenericCar car, String txt, PoseStack matrixStack, MultiBufferSource buffer, float partialTicks, int packedLight) {
        matrixStack.pushPose();
        matrixStack.scale(1F, -1F, 1F);

        translateLicensePlate(car, matrixStack, partialTicks);

        int textWidth = Minecraft.getInstance().font.width(txt);
        float textScale = 0.01F;

        matrixStack.translate(-(textScale * textWidth) / 2F, 0F, 0F);

        matrixStack.scale(textScale, textScale, textScale);

        Minecraft.getInstance().font.drawInBatch(txt, 0F, 0F, 0xFFFFFF, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, packedLight);

        matrixStack.popPose();
    }

    protected void translateLicensePlate(EntityGenericCar entity, PoseStack matrixStack, float partialTicks) {
        Vector3d offset = entity.getLicensePlateOffset();
        matrixStack.mulPose(Axis.YP.rotationDegrees(180F - entity.getViewYRot(partialTicks)));
        matrixStack.translate(offset.x, offset.y, offset.z);
    }

}