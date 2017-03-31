package de.maxhenkel.car.entity.car;

import java.util.Random;
import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.reciepe.CarCraftingManager;
import de.maxhenkel.car.reciepe.ICarRecipe;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class EntityCarWood extends EntityCarInventoryBase{

	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityCarWood.class,
			DataSerializers.VARINT);

	public EntityCarWood(World worldIn) {
		this(worldIn, EnumType.values()[new Random().nextInt(EnumType.values().length)]);
	}
	
	public EntityCarWood(World worldIn, EnumType type) {
		super(worldIn);
		setType(type);
	}

	@Override
	public ITextComponent getCarName() {
		return new TextComponentTranslation("entity.car_wood_" +getType().getName() +".name");
	}
	
	@Override
	public boolean isValidFuel(Fluid fluid) {
		return fluid.equals(ModFluids.BIO_DIESEL);
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TYPE, Integer.valueOf(0));
	}

	public void setType(EnumType type) {
		this.dataManager.set(TYPE, Integer.valueOf(type.getMetadata()));
	}

	public EnumType getType() {
		return EnumType.byMetadata(this.dataManager.get(TYPE));
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("type", getType().getMetadata());
		super.writeEntityToNBT(compound);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		setType(EnumType.byMetadata(compound.getInteger("type")));
		super.readEntityFromNBT(compound);
	}

	@Override
	public ICarRecipe getRecipe() {
		return CarCraftingManager.getInstance().getReciepeByName("car_wood_" +getType().getName());
	}

	
}
