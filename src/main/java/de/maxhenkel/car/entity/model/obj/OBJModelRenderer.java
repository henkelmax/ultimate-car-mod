package de.maxhenkel.car.entity.model.obj;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import de.maxhenkel.car.entity.car.base.EntityCarLicensePlateBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;

import java.util.List;

// https://github.com/2piradians/Minewatch/tree/1.12.1/src/main/java/twopiradians/minewatch/client
public abstract class OBJModelRenderer<T extends EntityGenericCar> extends EntityRenderer<T> {

    protected OBJModelRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    public abstract List<OBJModelInstance> getModels(T entity);

    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    @Override
    public void render(T entity, float yaw, float f1, MatrixStack matrixStack, IRenderTypeBuffer buffer, int light) {
        List<OBJModelInstance> models = getModels(entity);

        matrixStack.push();

        setupRotation(yaw, matrixStack);

        for (int i = 0; i < models.size(); i++) {
            matrixStack.push();

            matrixStack.translate(models.get(i).getOptions().getOffset().x, models.get(i).getOptions().getOffset().y, models.get(i).getOptions().getOffset().z);
            matrixStack.rotate(Vector3f.XP.rotationDegrees(-90F));

            if (models.get(i).getOptions().getRotation() != null) {
                models.get(i).getOptions().getRotation().applyRotation(matrixStack);
            }

            if (models.get(i).getOptions().getOnRender() != null) {
                models.get(i).getOptions().getOnRender().onRender(matrixStack, f1);
            }

            models.get(i).getModel().render(models.get(i).getOptions().getTexture(), matrixStack, buffer, light);
            matrixStack.pop();
        }

        if (entity instanceof EntityCarLicensePlateBase) {
            String text = entity.getLicensePlate();
            if (text != null && !text.isEmpty()) {
                matrixStack.push();
                RenderSystem.color4f(1F, 1F, 1F, 1F);
                drawLicensePlate(entity, text, matrixStack, buffer, light);
                matrixStack.pop();
            }
        }

        matrixStack.pop();

        super.render(entity, yaw, f1, matrixStack, buffer, light);
    }

    private void drawLicensePlate(EntityGenericCar car, String txt, MatrixStack matrixStack, IRenderTypeBuffer buffer, int i) {
        matrixStack.push();
        matrixStack.scale(1.0F, -1.0F, 1.0F);

        translateLicensePlate(car, matrixStack);

        int textWidth = Minecraft.getInstance().fontRenderer.getStringWidth(txt);
        float textScale = 0.01F;

        matrixStack.translate(-(textScale * textWidth) / 2.0F, 0F, 0F);

        matrixStack.scale(textScale, textScale, textScale);

        Minecraft.getInstance().fontRenderer.renderString(txt, 0F, 0F, 0xFFFFFF, false, matrixStack.getLast().getMatrix(), buffer, false, 0, i);


        matrixStack.pop();
    }

    public abstract void translateLicensePlate(EntityGenericCar car, MatrixStack matrixStack);

    public void setupRotation(float yaw, MatrixStack matrixStack) {
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180F - yaw));
    }

}