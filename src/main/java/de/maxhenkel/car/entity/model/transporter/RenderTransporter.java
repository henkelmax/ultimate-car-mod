package de.maxhenkel.car.entity.model.transporter;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.EntityCarTransporter;
import de.maxhenkel.car.entity.model.RenderCarBase;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;

public class RenderTransporter extends RenderCarBase<EntityCarTransporter> {

	private ModelBase modelTransporter;
	private ModelBase modelTransporterContainer;
	
	public RenderTransporter(RenderManager renderManagerIn) {
		super(renderManagerIn);
		this.shadowSize = 0.5F;
		this.modelTransporter=new ModelTransporter(false);
		this.modelTransporterContainer=new ModelTransporter(true);
	}
	
	@Override
	public ModelBase getModel(EntityCarTransporter entity) {
		if(entity.getHasContainer()){
			return modelTransporterContainer;
		}else{
			return modelTransporter;
		}
	}

	@Override
	public float getHeightOffset() {
		return 1.35F;
	}

	@Override
	public void translateNumberPlate() {
		GlStateManager.translate(0F, -0.45F, -1.08F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCarTransporter entity) {
		return new ResourceLocation(Main.MODID, "textures/entity/car_transporter_" +entity.getType().getName() +".png");
	}
}
