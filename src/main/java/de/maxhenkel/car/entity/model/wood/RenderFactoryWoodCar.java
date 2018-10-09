package de.maxhenkel.car.entity.model.wood;

import de.maxhenkel.car.entity.car.EntityCarWood;
import de.maxhenkel.car.entity.model.TestCarModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryWoodCar implements IRenderFactory<EntityCarWood>{

	@Override
	public Render<EntityCarWood> createRenderFor(RenderManager manager) {
		//return new RenderWoodCar(manager);
		return new TestCarModel(manager);
	}

}
