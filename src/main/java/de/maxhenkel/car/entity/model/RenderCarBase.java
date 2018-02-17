package de.maxhenkel.car.entity.model;

import com.mojang.realmsclient.gui.ChatFormatting;
import de.maxhenkel.car.entity.car.base.EntityCarNumberPlateBase;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.MinecraftForgeClient;
import org.lwjgl.opengl.GL11;

public abstract class RenderCarBase<T extends EntityVehicleBase> extends Render<T> {

    public RenderCarBase(RenderManager renderManagerIn) {
        super(renderManagerIn);
    }

    public abstract ModelBase getModel(T entity);

    public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
        GlStateManager.pushMatrix();
        this.setupTranslation(x, y, z);

        this.setupRotation(entity, entityYaw);

        GlStateManager.pushMatrix();
        this.bindEntityTexture(entity);

        if (this.renderOutlines) {
            GlStateManager.enableColorMaterial();
            GlStateManager.enableOutlineMode(this.getTeamColor(entity));
        }

        GlStateManager.translate(0F,  getHeightOffset(), 0F);

        getModel(entity).render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

        if (this.renderOutlines) {
            GlStateManager.disableOutlineMode();
            GlStateManager.disableColorMaterial();
        }

        GlStateManager.popMatrix();

        if(entity instanceof EntityCarNumberPlateBase){
            String text=((EntityCarNumberPlateBase)entity).getNumberPlate();
            if(text!=null&&!text.isEmpty()){
                GlStateManager.enableNormalize();
                drawText(text);
                GlStateManager.disableNormalize();
            }
        }

        GlStateManager.popMatrix();

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    private void drawText(String txt) {
        GlStateManager.pushMatrix();
        //GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
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
        GlStateManager.translate((float) x, (float) y, (float) z);
    }

    public boolean isMultipass() {
        return false;
    }

    public abstract float getHeightOffset();

    @Override
    protected abstract ResourceLocation getEntityTexture(T entity);
}