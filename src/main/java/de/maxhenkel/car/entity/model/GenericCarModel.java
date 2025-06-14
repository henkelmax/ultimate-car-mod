package de.maxhenkel.car.entity.model;

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

public class GenericCarModel extends OBJEntityRenderer<EntityGenericCar, CarRenderState> {

    public GenericCarModel(EntityRendererProvider.Context renderManager) {
        super(renderManager);
    }

    @Override
    public CarRenderState createRenderState() {
        return new CarRenderState();
    }

    @Override
    public void extractRenderState(EntityGenericCar car, CarRenderState state, float partialTicks) {
        super.extractRenderState(car, state, partialTicks);
        state.models = car.getModels();
        state.licensePlate = car.getLicensePlate();
        state.licensePlateOffset = car.getLicensePlateOffset();
        state.yRot = car.getViewYRot(partialTicks);
    }

    @Override
    public List<OBJModelInstance<CarRenderState>> getModels(CarRenderState state) {
        return state.models;
    }

    @Override
    public void render(CarRenderState state, PoseStack pose, MultiBufferSource source, int packedLight) {
        super.render(state, pose, source, packedLight);
        pose.pushPose();

        String text = state.licensePlate;
        if (text != null && !text.isEmpty()) {
            pose.pushPose();
            drawLicensePlate(state, pose, source, packedLight);
            pose.popPose();
        }

        pose.popPose();
    }

    @Override
    protected void setupYaw(CarRenderState state, PoseStack pose) {
        pose.mulPose(Axis.YP.rotationDegrees(180F - state.yRot));
    }

    private void drawLicensePlate(CarRenderState state, PoseStack matrixStack, MultiBufferSource buffer, int packedLight) {
        matrixStack.pushPose();
        matrixStack.scale(1F, -1F, 1F);

        translateLicensePlate(state, matrixStack);

        int textWidth = Minecraft.getInstance().font.width(state.licensePlate);
        float textScale = 0.01F;

        matrixStack.translate(-(textScale * textWidth) / 2F, 0F, 0F);

        matrixStack.scale(textScale, textScale, textScale);

        Minecraft.getInstance().font.drawInBatch(state.licensePlate, 0F, 0F, 0xFFFFFF, false, matrixStack.last().pose(), buffer, Font.DisplayMode.NORMAL, 0, packedLight);

        matrixStack.popPose();
    }

    protected void translateLicensePlate(CarRenderState state, PoseStack matrixStack) {
        Vector3d offset = state.licensePlateOffset;
        matrixStack.mulPose(Axis.YP.rotationDegrees(180F - state.yRot));
        matrixStack.translate(offset.x, offset.y, offset.z);
    }

}