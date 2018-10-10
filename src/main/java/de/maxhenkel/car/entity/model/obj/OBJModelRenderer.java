package de.maxhenkel.car.entity.model.obj;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarNumberPlateBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

// https://github.com/2piradians/Minewatch/tree/1.12.1/src/main/java/twopiradians/minewatch/client
public abstract class OBJModelRenderer<T extends EntityGenericCar> extends Render<T> {

    protected OBJModelRenderer(RenderManager renderManager) {
        super(renderManager);
    }

    public abstract OBJModelPart[] getModels(T entity);

    @Override
    protected ResourceLocation getEntityTexture(T entity) {
        return null;
    }

    @Override
    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        OBJModelPart[] models= getModels(entity);

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
        for (int i = 0; i < models.length; i++) {
            Minecraft.getMinecraft().getTextureManager().bindTexture(models[i].getTexture());
            GlStateManager.pushMatrix();
            if (models[i].hasCulling()) {
                GlStateManager.enableCull();
            } else {
                GlStateManager.disableCull();
            }

            GlStateManager.translate(models[i].getOffset().x, models[i].getOffset().y, models[i].getOffset().z);
            GlStateManager.rotate(-90F, 1F, 0F, 0F);

            if (models[i].getRotation() != null) {
                GlStateManager.rotate(models[i].getRotation());
            }

            if (models[i].getSpeedRotationFactor() > 0F) {
                GlStateManager.rotate(-entity.getWheelRotation(models[i].getSpeedRotationFactor()), 1F, 0F, 0F);
            }

            OBJRenderer.renderObj(models[i].getModel());
            GlStateManager.enableCull();
            GlStateManager.popMatrix();
        }

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();

        if (entity instanceof EntityCarNumberPlateBase) {
            String text = ((EntityCarNumberPlateBase) entity).getNumberPlate();
            if (text != null && !text.isEmpty()) {
                GlStateManager.enableNormalize();
                drawNumberPlate(text);
                GlStateManager.disableNormalize();
            }
        }

        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void drawNumberPlate(String txt) {
        GlStateManager.pushMatrix();
        GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);

        translateNumberPlate();

        int textWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(txt);
        float textScale = 0.01F;

        GlStateManager.translate(-(textScale * textWidth) / 2.0F, 0F, 0F);

        GlStateManager.scale(textScale, textScale, textScale);

        Minecraft.getMinecraft().fontRenderer.drawString(ChatFormatting.WHITE + txt, 0, 0, 0);

        GlStateManager.popMatrix();
    }

    public abstract void translateNumberPlate();

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