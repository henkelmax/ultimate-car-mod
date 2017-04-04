package de.maxhenkel.car.entity.model.sport;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.EntityCarSport;
import de.maxhenkel.car.entity.model.RenderCarBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderSport extends RenderCarBase<EntityCarSport> {

	private ModelBase model;
	
	public RenderSport(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
		this.model=new ModelSport();
	}
	
	@Override
	public ModelBase getModel(EntityCarSport entity) {
		return model;
	}

	@Override
	public float getHeightOffset() {
		return 0.035F;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCarSport entity) {
		return new ResourceLocation(Main.MODID, "textures/entity/car_sport_" +entity.getType().getName() +".png");
	}
}
