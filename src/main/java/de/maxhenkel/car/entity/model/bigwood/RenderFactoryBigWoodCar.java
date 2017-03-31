package de.maxhenkel.car.entity.model.bigwood;

import de.maxhenkel.car.entity.car.EntityCarBigWood;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryBigWoodCar implements IRenderFactory<EntityCarBigWood>{

	@Override
	public Render<EntityCarBigWood> createRenderFor(RenderManager manager) {
		return new RenderBigWoodCar(manager);
	}

}
