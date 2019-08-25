package de.maxhenkel.car.entity.model.obj;

import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.entity.car.base.EntityCarLicensePlateBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

// https://github.com/2piradians/Minewatch/tree/1.12.1/src/main/java/twopiradians/minewatch/client
public abstract class OBJModelRenderer<T extends EntityGenericCar> extends EntityRenderer<T> {

    protected OBJModelRenderer(EntityRendererManager renderManager) {
        super(renderManager);
    }

    public abstract List<OBJModelInstance> getModels(T entity);

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        List<OBJModelInstance> models = getModels(entity);

        GlStateManager.pushMatrix();
        setupTranslation(x, y, z);

        setupRotation(entity, entityYaw);

        GlStateManager.pushMatrix();
        GlStateManager.disableLighting();

        //Render parts
        for (int i = 0; i < models.size(); i++) {
            Minecraft.getInstance().getTextureManager().bindTexture(models.get(i).getModel().getTexture());
            GlStateManager.pushMatrix();
            if (models.get(i).getModel().hasCulling()) {
                GlStateManager.enableCull();
            } else {
                GlStateManager.disableCull();
            }

            GlStateManager.translated(models.get(i).getOptions().getOffset().x, models.get(i).getOptions().getOffset().y, models.get(i).getOptions().getOffset().z);
            GlStateManager.rotatef(-90F, 1F, 0F, 0F);

            if (models.get(i).getOptions().getRotation() != null) {
                models.get(i).getOptions().getRotation().applyGLRotation();
            }

            if (models.get(i).getOptions().getOnRender() != null) {
                models.get(i).getOptions().getOnRender().onRender(partialTicks);
            }

            OBJRenderer.renderObj(models.get(i).getModel().getModel());
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();

        if (entity instanceof EntityCarLicensePlateBase) {
            String text = entity.getLicensePlate();
            if (text != null && !text.isEmpty()) {
                GlStateManager.pushMatrix();
                GlStateManager.enableNormalize();
                GlStateManager.disableLighting();
                GlStateManager.color4f(1F, 1F, 1F, 1F);
                drawNumberPlate(entity, text);
                GlStateManager.enableLighting();
                GlStateManager.disableNormalize();
                GlStateManager.popMatrix();
            }
        }

        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void drawNumberPlate(EntityGenericCar car, String txt) {
        GlStateManager.pushMatrix();
        GlStateManager.rotatef(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scalef(-1.0F, -1.0F, 1.0F);

        translateNumberPlate(car);

        int textWidth = Minecraft.getInstance().fontRenderer.getStringWidth(txt);
        float textScale = 0.01F;

        GlStateManager.translatef(-(textScale * textWidth) / 2.0F, 0F, 0F);

        GlStateManager.scalef(textScale, textScale, textScale);

        Minecraft.getInstance().fontRenderer.drawString(TextFormatting.WHITE + txt, 0, 0, 0);

        GlStateManager.popMatrix();
    }

    public abstract void translateNumberPlate(EntityGenericCar car);

    public void setupRotation(T entity, float yaw) {
        GlStateManager.rotatef(180.0F - yaw, 0.0F, 1.0F, 0.0F);
    }

    public void setupTranslation(double x, double y, double z) {
        GlStateManager.translated(x, y, z);
    }

    @Override
    public boolean isMultipass() {
        return false;
    }


}