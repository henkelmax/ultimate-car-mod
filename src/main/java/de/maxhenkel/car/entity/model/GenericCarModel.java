package de.maxhenkel.car.entity.model;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.client.obj.OBJEntityRenderer;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.network.chat.Component;
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
        String licensePlate = car.getLicensePlate();
        state.licensePlate = licensePlate == null ? null : Component.literal(licensePlate).getVisualOrderText();
        state.licensePlateOffset = car.getLicensePlateOffset();
        state.yRot = car.getViewYRot(partialTicks);
    }

    @Override
    public List<OBJModelInstance<CarRenderState>> getModels(CarRenderState state) {
        return state.models;
    }

    @Override
    public void submit(CarRenderState state, PoseStack pose, SubmitNodeCollector collector, CameraRenderState cameraRenderState) {
        super.submit(state, pose, collector, cameraRenderState);
        if (state.licensePlate != null) {
            drawLicensePlate(state, pose, collector);
        }
    }

    @Override
    protected void setupYaw(CarRenderState state, PoseStack pose) {
        pose.mulPose(Axis.YP.rotationDegrees(180F - state.yRot));
    }

    private void drawLicensePlate(CarRenderState state, PoseStack stack, SubmitNodeCollector collector) {
        stack.pushPose();
        stack.scale(1F, -1F, 1F);

        translateLicensePlate(state, stack);

        int textWidth = Minecraft.getInstance().font.width(state.licensePlate);
        float textScale = 0.01F;

        stack.translate(-(textScale * textWidth) / 2F, 0F, 0F);

        stack.scale(textScale, textScale, textScale);

        collector.submitText(stack, 0F, 0F, state.licensePlate, false, Font.DisplayMode.NORMAL, state.lightCoords, 0xFFFFFFFF, 0, 0);

        stack.popPose();
    }

    protected void translateLicensePlate(CarRenderState state, PoseStack matrixStack) {
        Vector3d offset = state.licensePlateOffset;
        matrixStack.mulPose(Axis.YP.rotationDegrees(180F - state.yRot));
        matrixStack.translate(offset.x, offset.y, offset.z);
    }

}