package de.maxhenkel.tools;

import com.mojang.blaze3d.platform.GLX;
import com.mojang.blaze3d.platform.GlStateManager;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.network.PacketDistributor;

public class MathTools {

    public static boolean isInBounds(float number, float bound, float tolerance) {
        if (number > bound - tolerance && number < bound + tolerance) {
            return true;
        }
        return false;
    }

    public static float subtractToZero(float num, float sub) {
        float erg;
        if (num < 0) {
            erg = num + sub;
            if (erg > 0) {
                erg = 0;
            }
        } else {
            erg = num - sub;
            if (erg < 0) {
                erg = 0;
            }
        }

        return erg;
    }

    public static double round(double value, int scale) {
        return Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale);
    }

    public static float round(float value, int scale) {
        return (float) (Math.round(value * Math.pow(10, scale)) / Math.pow(10, scale));
    }

    public static PacketDistributor.TargetPoint getTileEntityTargetPoint(TileEntity te) {
        return new PacketDistributor.TargetPoint(te.getPos().getX(), te.getPos().getY(),
                te.getPos().getZ(), 64, te.getWorld().dimension.getType());
    }

    public static PacketDistributor.TargetPoint getEntityTargetPoint(Entity e) {
        return new PacketDistributor.TargetPoint(e.posX, e.posY,
                e.posZ, 64, e.world.dimension.getType());
    }

    public static long intToLong(int i1, int i2) {
        return (((long) i1) << 32) | (i2 & 0xffffffffL);
    }

    public static void drawCarOnScreen(int posX, int posY, int scale, float rotation,
                                       EntityCarBase ent) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translatef((float) posX, (float) posY, 50.0F);
        GlStateManager.scalef((float) (-scale), (float) scale, (float) scale);
        GlStateManager.rotatef(180.0F, 0.0F, 0.0F, 1.0F);
        GlStateManager.rotatef(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotatef(rotation, 0.0F, 1.0F, 0.0F);
        //GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        //ent.rotationYaw = rotationYaw;
        //ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
        GlStateManager.translatef(0.0F, 0.0F, 0.0F);
        EntityRendererManager rendermanager = Minecraft.getInstance().getRenderManager();
        rendermanager.setPlayerViewY(180.0F);
        rendermanager.setRenderShadow(false);
        rendermanager.renderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        rendermanager.setRenderShadow(true);
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.activeTexture(GLX.GL_TEXTURE1);
        GlStateManager.disableTexture();
        GlStateManager.activeTexture(GLX.GL_TEXTURE0);
    }

}
