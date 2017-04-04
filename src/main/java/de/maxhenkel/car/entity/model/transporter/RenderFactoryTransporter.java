package de.maxhenkel.car.entity.model.transporter;

import de.maxhenkel.car.entity.car.EntityCarTransporter;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryTransporter implements IRenderFactory<EntityCarTransporter>{

	@Override
	public Render<EntityCarTransporter> createRenderFor(RenderManager manager) {
		return new RenderTransporter(manager);
	}

}
