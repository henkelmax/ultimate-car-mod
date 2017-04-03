package de.maxhenkel.car.entity.car;

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

public class EntityCarBigWood extends EntityCarInventoryBase{

	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityCarBigWood.class,
			DataSerializers.VARINT);
	
	public EntityCarBigWood(World worldIn) {
		this(worldIn, EnumType.OAK);
	}
	
	public EntityCarBigWood(World worldIn, EnumType type) {
		super(worldIn);
		setType(type);
		setSize(1.5F, 1.6F);
		fuelTick=20;
		maxFuel=1500;
		maxSpeed=0.48F;
	}
	
	@Override
	public int getPassengerSize() {
		return 2;
	}

	@Override
	public ITextComponent getCarName() {
		return new TextComponentTranslation("entity.car_big_wood.name");
	}
	
	@Override
	public boolean isValidFuel(Fluid fluid) {
		return fluid.equals(ModFluids.BIO_DIESEL);
	}

	@Override
	public ICarRecipe getRecipe() {
		return CarCraftingManager.getInstance().getReciepeByName("car_big_wood_" +getType().getName());
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TYPE, EnumType.OAK.getMetadata());
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
}
