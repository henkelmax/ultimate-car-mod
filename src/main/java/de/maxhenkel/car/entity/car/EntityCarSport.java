package de.maxhenkel.car.entity.car;

import de.maxhenkel.car.entity.car.base.EntityCarInventoryBase;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.reciepe.CarBuilderSport;
import de.maxhenkel.car.reciepe.ICarbuilder;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

public class EntityCarSport extends EntityCarInventoryBase{

	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityCarSport.class,
			DataSerializers.VARINT);

	public EntityCarSport(World worldIn) {
		this(worldIn, EnumDyeColor.WHITE);
	}
	
	public EntityCarSport(World worldIn, EnumDyeColor type) {
		super(worldIn);
		setType(type);
		setSize(1.4F, 1.6F);
		fuelTick=10;
		fuelIdleTick=150;
		maxFuel=1500;
		maxSpeed=0.65F;
		acceleration=0.04F;
		minRotationSpeed=1.1F;
	}
	
	@Override
	public float getRotationModifier() {
		return 0.5F;
	}

	@Override
	public ITextComponent getCarName() {
		return new TextComponentTranslation("entity.car_sport.name");
	}
	
	@Override
	public boolean isValidFuel(Fluid fluid) {
		return fluid.equals(ModFluids.BIO_DIESEL);
	}

	@Override
	public ICarbuilder getBuilder() {
		return new CarBuilderSport(getType());
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(TYPE, EnumDyeColor.WHITE.getMetadata());
	}

	public void setType(EnumDyeColor color) {
		this.dataManager.set(TYPE, color.getMetadata());
	}

	public EnumDyeColor getType() {
		return EnumDyeColor.byMetadata(this.dataManager.get(TYPE));
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setInteger("type", getType().getMetadata());
		super.writeEntityToNBT(compound);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		setType(EnumDyeColor.byMetadata(compound.getInteger("type")));
		super.readEntityFromNBT(compound);
	}
	
	@Override
	public SoundEvent getStopSound() {
		return ModSounds.sport_engine_stop;
	}
	
	@Override
	public SoundEvent getFailSound() {
		return ModSounds.sport_engine_fail;
	}
	
	@Override
	public SoundEvent getStartSound() {
		return ModSounds.sport_engine_start;
	}
	
	@Override
	public SoundEvent getIdleSound() {
		return ModSounds.sport_engine_idle;
	}
	
	@Override
	public SoundEvent getHighSound() {
		return ModSounds.sport_engine_high;
	}
	
	@Override
	public int getStartSoundTime() {
		return 1000;
	}
	
}
