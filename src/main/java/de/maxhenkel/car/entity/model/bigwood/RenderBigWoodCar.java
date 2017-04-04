package de.maxhenkel.car.entity.model.bigwood;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.EntityCarBigWood;
import de.maxhenkel.car.entity.model.RenderCarBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderBigWoodCar extends RenderCarBase<EntityCarBigWood> {

	public RenderBigWoodCar(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
		this.model=new ModelBigWoodCar();
	}

	@Override
	public float getHeightOffset() {
		return 1.48F;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCarBigWood entity) {
		switch(entity.getType()){
		case OAK:
			return new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_oak.png");
		case ACACIA:
			return new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_acacia.png");
		case BIRCH:
			return new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_birch.png");
		case DARK_OAK:
			return new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_dark_oak.png");
		case JUNGLE:
			return new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_jungle.png");
		case SPRUCE:
			return new ResourceLocation(Main.MODID, "textures/entity/car_big_wood_spruce.png");
		}
		return null;
	}
}
