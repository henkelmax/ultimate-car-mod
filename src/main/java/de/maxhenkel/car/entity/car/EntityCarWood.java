package de.maxhenkel.car.entity.car;

import java.util.Random;
import de.maxhenkel.car.entity.car.base.EntityCarLockBase;
import de.maxhenkel.car.entity.car.base.EntityCarNumberPlateBase;
import de.maxhenkel.car.reciepe.CarBuilderWoodCar;
import de.maxhenkel.car.reciepe.ICarbuilder;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityCarWood extends EntityCarNumberPlateBase {

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
	public float getRotationModifier() {
		return 0.5F;
	}

	@Override
	public ITextComponent getCarName() {
		return new TextComponentTranslation("entity.car_wood.name");
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
		return new CarBuilderWoodCar(getType());
	}

	@Override
	public String getID() {
		return "car_wood";
	}

	
}
