package de.maxhenkel.car.entity.car;

import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.reciepe.CarCraftingManager;
import de.maxhenkel.car.reciepe.ICarRecipe;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class EntityCarTransporter extends EntityCarInventoryBase{
	
	public EntityCarTransporter(World worldIn) {
		super(worldIn);
		setSize(1.5F, 1.6F);
		fuelTick=25;
		maxFuel=2000;
		maxSpeed=0.4F;
	}
	
	@Override
	public int getExternalInventorySize() {
		return 54;
	}

	@Override
	public ITextComponent getCarName() {
		return new TextComponentTranslation("entity.car_transporter.name");
	}
	
	@Override
	public boolean isValidFuel(Fluid fluid) {
		return fluid.equals(ModFluids.BIO_DIESEL);
	}

	@Override
	public ICarRecipe getRecipe() {
		return CarCraftingManager.getInstance().getReciepeByName("car_transporter");
	}

	
}
