package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public abstract class EntityCarFuelBase extends EntityCarDamageBase implements IFluidHandler{

	private static final DataParameter<Integer> FUEL = EntityDataManager.<Integer>createKey(EntityCarFuelBase.class,
			DataSerializers.VARINT);
	
	protected int millibucketsPerFuelTick = 1;
	protected int millibucketsPerIdleFuelTick = 1;
	protected int fuelTick=30;
	protected int fuelIdleTick=250;
	protected int maxFuel=1000;

	public EntityCarFuelBase(World worldIn) {
		super(worldIn);
		
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		fuelTick();
	}
	
	protected void fuelTick(){
		float fuel=getFuel();
		
		if (fuel > 0 && isAccelerating()) {
			if(ticksExisted%fuelTick==0){
				acceleratingFuelTick();
			}
		}else if(fuel > 0 && isStarted()){
			if(ticksExisted%fuelIdleTick==0){
				idleFuelTick();
			}
		}
	}
	
	protected void idleFuelTick(){
		removeFuel(millibucketsPerIdleFuelTick);
	}
	
	protected void acceleratingFuelTick(){
		removeFuel(millibucketsPerFuelTick);
	}
	
	private void removeFuel(int amount){
		int fuel=getFuel();
		int newFuel = fuel - amount;
		if (newFuel <= 0) {
			setFuel(0);
		} else {
			setFuel(newFuel);
		}
	}

	@Override
	public boolean canPlayerDriveCar(EntityPlayer player) {

		if (getFuel() <= 0) {
			return false;
		}

		return super.canPlayerDriveCar(player);
	}
	
	@Override
	public boolean canStartCarEngine(EntityPlayer player) {
		if(getFuel()<=0){
			return false;
		}
		
		return super.canStartCarEngine(player);
	}
	
	public boolean canEngineStayOn(){
		if(getFuel()<=0){
			return false;
		}
		
		return super.canEngineStayOn();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(FUEL, Integer.valueOf(0));
	}

	public void setFuel(int fuel) {
		this.dataManager.set(FUEL, Integer.valueOf(fuel));
		//System.out.println("Set: " +fuel +"  remote: " +world.isRemote);
	}

	public int getFuel() {
		return this.dataManager.get(FUEL);
	}

	public int getMaxFuel() {
		return maxFuel;
	}
	
	public abstract boolean isValidFuel(Fluid fluid);
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("fuel", getFuel());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setFuel(compound.getInteger("fuel"));
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{new IFluidTankProperties() {
			
			@Override
			public FluidStack getContents() {
				return new FluidStack(ModFluids.BIO_DIESEL, getFuel());
			}
			
			@Override
			public int getCapacity() {
				return maxFuel;
			}
			
			@Override
			public boolean canFillFluidType(FluidStack fluidStack) {
				return isValidFuel(fluidStack.getFluid());
			}
			
			@Override
			public boolean canFill() {
				return true;
			}
			
			@Override
			public boolean canDrainFluidType(FluidStack fluidStack) {
				return false;
			}
			
			@Override
			public boolean canDrain() {
				return false;
			}
		}};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {

		if(!isValidFuel(resource.getFluid())){
			return 0;
		}
		
		int amount=Math.min(resource.amount, maxFuel-getFuel());
		
		if(doFill){
			int i=getFuel()+amount;
			if(i>maxFuel){
				i=maxFuel;
			}
			setFuel(i);
		}
		
		return amount;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
	}

}
