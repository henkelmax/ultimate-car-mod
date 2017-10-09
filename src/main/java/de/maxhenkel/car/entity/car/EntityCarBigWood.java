package de.maxhenkel.car.entity.car;

import de.maxhenkel.car.entity.car.base.EntityCarLockBase;
import de.maxhenkel.car.reciepe.CarBuilderWoodCarBig;
import de.maxhenkel.car.reciepe.ICarbuilder;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityCarBigWood extends EntityCarLockBase{

	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityCarBigWood.class,
			DataSerializers.VARINT);
	
	public EntityCarBigWood(World worldIn) {
		this(worldIn, EnumType.OAK);
	}
	
	public EntityCarBigWood(World worldIn, EnumType type) {
		super(worldIn);
		setType(type);
		setSize(1.5F, 1.6F);
		maxFuel=1500;
		//maxSpeed=0.48F;
	}
	
	@Override
	public float getFrontOffsetForPassenger(int i, Entity passenger) {
		if (getPassengers().size() > 1) {
			if (i == 0) {
				return 0.2F;
			} else {
				return -0.6F;
			}
		}
		return 0;
	}
	
	@Override
	public float getRotationModifier() {
		return 0.5F;
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

	@Override
	public ICarbuilder getBuilder() {
		return new CarBuilderWoodCarBig(getType());
	}

	@Override
	public String getID() {
		return "car_big_wood";
	}
}
