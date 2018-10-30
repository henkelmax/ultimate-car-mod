package de.maxhenkel.car.entity.model.obj;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.car.entity.car.base.EntityCarLicensePlateBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import java.util.List;

// https://github.com/2piradians/Minewatch/tree/1.12.1/src/main/java/twopiradians/minewatch/client
public abstract class OBJModelRenderer<T extends EntityGenericCar> extends Render<T> {

    protected OBJModelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    public abstract List<OBJModelInstance> getModels(T entity);

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        List<OBJModelInstance> models= getModels(entity);

        GlStateManager.pushMatrix();
        setupTranslation(x, y, z);

        setupRotation(entity, entityYaw);

        GlStateManager.pushMatrix();

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        entity.updateWheelRotation(partialTicks);
        //Render parts
        for (int i = 0; i < models.size(); i++) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(models.get(i).getModel().getTexture());
            GlStateManager.pushMatrix();
            if (models.get(i).getModel().hasCulling()) {
                GlStateManager.enableCull();
            } else {
                GlStateManager.disableCull();
            }

            GlStateManager.translate(models.get(i).getOptions().getOffset().x, models.get(i).getOptions().getOffset().y, models.get(i).getOptions().getOffset().z);
            GlStateManager.rotate(-90F, 1F, 0F, 0F);

            if (models.get(i).getOptions().getRotation() != null) {
                GlStateManager.rotate(models.get(i).getOptions().getRotation().getQuaternion());
            }

            if (models.get(i).getOptions().getSpeedRotationFactor() > 0F) {
                GlStateManager.rotate(-entity.getWheelRotation(models.get(i).getOptions().getSpeedRotationFactor()), 1F, 0F, 0F);
            }

            OBJRenderer.renderObj(models.get(i).getModel().getModel());
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();

        if (entity instanceof EntityCarLicensePlateBase) {
            String text = entity.getLicensePlate();
            if (text != null && !text.isEmpty()) {
                GlStateManager.enableNormalize();
                drawNumberPlate(entity, text);
                GlStateManager.disableNormalize();
            }
        }

        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void drawNumberPlate(EntityGenericCar car, String txt) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);

        translateNumberPlate(car);

        int textWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(txt);
        float textScale = 0.01F;

        GlStateManager.translate(-(textScale * textWidth) / 2.0F, 0F, 0F);

        GlStateManager.scale(textScale, textScale, textScale);

        Minecraft.getMinecraft().fontRenderer.drawString(ChatFormatting.WHITE + txt, 0, 0, 0);

        GlStateManager.popMatrix();
    }

    public abstract void translateNumberPlate(EntityGenericCar car);

    public void setupRotation(T entity, float yaw) {
        GlStateManager.rotate(180.0F - yaw, 0.0F, 1.0F, 0.0F);
    }

    public void setupTranslation(double x, double y, double z) {
        GlStateManager.translate(x, y, z);
    }

    @Override
    public boolean isMultipass() {
        return false;
    }


}