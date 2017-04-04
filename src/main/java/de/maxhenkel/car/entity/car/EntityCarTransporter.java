package de.maxhenkel.car.entity.car;

import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.reciepe.CarBuilderTransporter;
import de.maxhenkel.car.reciepe.ICarbuilder;
import net.minecraft.entity.Entity;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class EntityCarTransporter extends EntityCarInventoryBase{
	
	private static final DataParameter<Boolean> HAS_CONTAINER = EntityDataManager.<Boolean>createKey(EntityCarTransporter.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityCarTransporter.class,
			DataSerializers.VARINT);
	
	
	public EntityCarTransporter(World worldIn) {
		this(worldIn, false, EnumDyeColor.WHITE);
	}
	
	public EntityCarTransporter(World worldIn, boolean hasContainer, EnumDyeColor color) {
		super(worldIn);
		setSize(2.0F, 1.51F);
		fuelTick=30;
		maxFuel=2000;
		maxSpeed=0.4F;
		setHasContainer(hasContainer);
		setType(color);
	}
	
	@Override
	public int getExternalInventorySize() {
		if(getHasContainer()){
			return 54;
		}else{
			return 27;
		}
	}
	
	@Override
	public float getRotationModifier() {
		return 0.25F;
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
	public float getFrontOffsetForPassenger(int i, Entity passenger) {
		return 0.55F;
	}
	
	@Override
	public float getsideOffsetForPassenger(int i, Entity passenger) {
		if(i<=0){
			return -0.38F;
		}
		return 0.38F;
	}
	
	@Override
	public int getPassengerSize() {
		return 2;
	}
	
	@Override
	public double getMountedYOffset() {
		return -0.5D;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(HAS_CONTAINER, false);
		this.dataManager.register(TYPE, EnumDyeColor.WHITE.getMetadata());
	}

	public void setHasContainer(boolean has) {
		this.dataManager.set(HAS_CONTAINER, has);
	}

	public boolean getHasContainer() {
		return this.dataManager.get(HAS_CONTAINER);
	}
	
	public void setType(EnumDyeColor color) {
		this.dataManager.set(TYPE, color.getMetadata());
	}

	public EnumDyeColor getType() {
		return EnumDyeColor.byMetadata(this.dataManager.get(TYPE));
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("has_container", getHasContainer());
		compound.setInteger("type", getType().getMetadata());
		super.writeEntityToNBT(compound);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		setHasContainer(compound.getBoolean("has_container"));
		setType(EnumDyeColor.byMetadata(compound.getInteger("type")));
		super.readEntityFromNBT(compound);
	}

	@Override
	public ICarbuilder getBuilder() {
		return new CarBuilderTransporter(getHasContainer(), getType());
	}
	
}
