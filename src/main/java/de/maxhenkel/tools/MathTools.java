package de.maxhenkel.tools;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MathTools {

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

	public static TargetPoint getTileEntityTargetPoint(TileEntity te) {
		return new TargetPoint(te.getWorld().provider.getDimension(), te.getPos().getX(), te.getPos().getY(),
				te.getPos().getZ(), 64);
	}

	public static long intToLong(int i1, int i2) {
		return (((long) i1) << 32) | (i2 & 0xffffffffL);
	}

	public static void drawCarOnScreen(int posX, int posY, int scale, float rotation,
			EntityCarBase ent) {
		GlStateManager.enableColorMaterial();
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) posX, (float) posY, 50.0F);
		GlStateManager.scale((float) (-scale), (float) scale, (float) scale);
		GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
		GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GlStateManager.rotate(rotation, 0.0F, 1.0F, 0.0F);
		//GlStateManager.rotate(-((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
		//ent.rotationYaw = rotationYaw;
		//ent.rotationPitch = -((float) Math.atan((double) (mouseY / 40.0F))) * 20.0F;
		GlStateManager.translate(0.0F, 0.0F, 0.0F);
		RenderManager rendermanager = Minecraft.getMinecraft().getRenderManager();
		rendermanager.setPlayerViewY(180.0F);
		rendermanager.setRenderShadow(false);
		rendermanager.doRenderEntity(ent, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
		rendermanager.setRenderShadow(true);
		GlStateManager.popMatrix();
		RenderHelper.disableStandardItemLighting();
		GlStateManager.disableRescaleNormal();
		GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
		GlStateManager.disableTexture2D();
		GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
	}

}
