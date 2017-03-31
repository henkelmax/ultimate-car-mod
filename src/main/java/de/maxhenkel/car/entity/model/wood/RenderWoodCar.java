package de.maxhenkel.car.entity.model.wood;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.EntityCarWood;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderWoodCar extends Render<EntityCarWood> {

	protected ModelBase modelCar = new ModelWoodCar();

	public RenderWoodCar(RenderManager renderManagerIn) {
		super(renderManagerIn);
		//this.shadowSize = 0.5F;
	}

	public void doRender(EntityCarWood entity, double x, double y, double z, float entityYaw, float partialTicks) {
		GlStateManager.pushMatrix();
		this.setupTranslation(x, y, z);
		this.setupRotation(entity, entityYaw, partialTicks);
		this.bindEntityTexture(entity);

		if (this.renderOutlines) {
			GlStateManager.enableColorMaterial();
			GlStateManager.enableOutlineMode(this.getTeamColor(entity));
		}

		this.modelCar.render(entity, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F);

		if (this.renderOutlines) {
			GlStateManager.disableOutlineMode();
			GlStateManager.disableColorMaterial();
		}

		GlStateManager.popMatrix();
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	public void setupRotation(EntityCarWood entity, float p_188311_2_, float p_188311_3_) {
		GlStateManager.rotate(180.0F - p_188311_2_, 0.0F, 1.0F, 0.0F);
		GlStateManager.scale(-1.0F, -1.0F, 1.0F);
	}

	public void setupTranslation(double x, double y, double z) {
		GlStateManager.translate((float) x, (float) y + 1.48F, (float) z);
	}

	public boolean isMultipass() {
		return false;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityCarWood entity) {
		switch(entity.getType()){
		case OAK:
			return new ResourceLocation(Main.MODID, "textures/entity/car_wood_oak.png");
		case ACACIA:
			return new ResourceLocation(Main.MODID, "textures/entity/car_wood_acacia.png");
		case BIRCH:
			return new ResourceLocation(Main.MODID, "textures/entity/car_wood_birch.png");//
		case DARK_OAK:
			return new ResourceLocation(Main.MODID, "textures/entity/car_wood_dark_oak.png");
		case JUNGLE:
			return new ResourceLocation(Main.MODID, "textures/entity/car_wood_jungle.png");//
		case SPRUCE:
			return new ResourceLocation(Main.MODID, "textures/entity/car_wood_spruce.png");//
		}
		return null;
	}
}
