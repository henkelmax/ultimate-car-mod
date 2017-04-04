package de.maxhenkel.car.entity.model;

import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public abstract class RenderCarBase<T extends EntityVehicleBase> extends Render<T> {

	protected ModelBase model;

	public RenderCarBase(RenderManager renderManagerIn) {
		super(renderManagerIn);
	}

	public void doRender(T entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		this.setupTranslation(x, y, z);
		this.setupRotation(entity, entityYaw, partialTicks);
		this.bindEntityTexture(entity);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		model.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public void setupRotation(T entity, float f1, float f2) {
		GlStateManager.rotate(180.0F - f1, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
	}

	public void setupTranslation(double x, double y, double z) {
		GlStateManager.translate((float) x, (float) y + getHeightOffset(), (float) z);
	}

	public boolean isMultipass() {
		return false;
	}
	
	public abstract float getHeightOffset();

	@Override
	protected abstract ResourceLocation getEntityTexture(T entity);
}