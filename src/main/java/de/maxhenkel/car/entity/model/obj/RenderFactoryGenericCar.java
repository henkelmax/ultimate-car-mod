package de.maxhenkel.car.entity.model.obj;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.model.GenericCarModel;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;

public class RenderFactoryGenericCar  implements IRenderFactory<EntityGenericCar> {
    @Override
    public Render<? super EntityGenericCar> createRenderFor(RenderManager renderManager) {
        return new GenericCarModel(renderManager);
    }
}
