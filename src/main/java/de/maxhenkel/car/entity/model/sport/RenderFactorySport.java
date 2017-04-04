package de.maxhenkel.car.entity.model.sport;

import de.maxhenkel.car.entity.car.EntityCarSport;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactorySport implements IRenderFactory<EntityCarSport>{

	@Override
	public Render<EntityCarSport> createRenderFor(RenderManager manager) {
		return new RenderSport(manager);
	}

}
