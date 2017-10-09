package de.maxhenkel.car.entity.car.base;

import javax.annotation.Nullable;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.registries.CarFluid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public abstract class EntityCarFuelBase extends EntityCarDamageBase implements IFluidHandler{

	private static final DataParameter<Integer> FUEL_AMOUNT = EntityDataManager.<Integer>createKey(EntityCarFuelBase.class,
			DataSerializers.VARINT);
	private static final DataParameter<String> FUEL_TYPE = EntityDataManager.<String>createKey(EntityCarFuelBase.class,
			DataSerializers.STRING);
	
	protected int maxFuel=1000;

	public EntityCarFuelBase(World worldIn) {
		super(worldIn);
		
	}

	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();

		fuelTick();
	}
	
	protected int calculateTickFuel(){
		double efficiency=getEfficiency(getFluid());
		int ticks=(int)(efficiency*100D);
		if(ticks<=0){
			ticks=1;
		}
		return ticks;
	}
	
	protected void fuelTick(){
		float fuel=getFuelAmount();
		int tickFuel=calculateTickFuel();
		if (fuel > 0 && isAccelerating()) {
			if(ticksExisted%tickFuel==0){
				acceleratingFuelTick();
			}
		}else if(fuel > 0 && isStarted()){
			if(ticksExisted%(tickFuel*100)==0){
				idleFuelTick();
			}
		}
	}
	
	protected void idleFuelTick(){
		removeFuel(1);
	}
	
	protected void acceleratingFuelTick(){
		removeFuel(1);
	}
	
	private void removeFuel(int amount){
		int fuel=getFuelAmount();
		int newFuel = fuel - amount;
		if (newFuel <= 0) {
			setFuelAmount(0);
		} else {
			setFuelAmount(newFuel);
		}
	}

	@Override
	public boolean canPlayerDriveCar(EntityPlayer player) {

		if (getFuelAmount() <= 0) {
			return false;
		}

		return super.canPlayerDriveCar(player);
	}
	
	@Override
	public boolean canStartCarEngine(EntityPlayer player) {
		if(getFuelAmount()<=0){
			return false;
		}
		
		return super.canStartCarEngine(player);
	}
	
	public boolean canEngineStayOn(){
		if(getFuelAmount()<=0){
			return false;
		}
		
		return super.canEngineStayOn();
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(FUEL_AMOUNT, Integer.valueOf(0));
		this.dataManager.register(FUEL_TYPE, new String());
	}

	public void setFuelAmount(int fuel) {
		this.dataManager.set(FUEL_AMOUNT, Integer.valueOf(fuel));
	}
	
	public void setFuelType(String fluid) {
		this.dataManager.set(FUEL_TYPE, fluid);
	}
	
	public void setFuelType(Fluid fluid) {
		this.dataManager.set(FUEL_TYPE, fluid.getName());
	}

	public String getFuelType() {
		return this.dataManager.get(FUEL_TYPE);
	}
	
	@Nullable
	public Fluid getFluid(){
		return FluidRegistry.getFluid(getFuelType());
	}
	
	public int getFuelAmount() {
		return this.dataManager.get(FUEL_AMOUNT);
	}

	public int getMaxFuel() {
		return maxFuel;
	}
	
	public boolean isValidFuel(Fluid fluid){
		return getEfficiency(fluid)>0D;
	}
	
	public double getEfficiency(Fluid fluid){
		if(fluid==null){
			return 1D;
		}
		for(CarFluid cf:CarFluid.REGISTRY){
			if(!cf.getCarID().equals(getID())) {
				continue;//TODO other versions
			}
			if(cf.getInput().isValid(fluid)){
				return cf.getEfficiency();
			}
		}
		return 0D;
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setInteger("fuel", getFuelAmount());
		compound.setString("fuel_type", getFuelType());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		setFuelAmount(compound.getInteger("fuel"));
		if(compound.hasKey("fuel_type")){
			setFuelType(compound.getString("fuel_type"));
		}else{
			setFuelType(ModFluids.BIO_DIESEL.getName());
		}
	}
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{new IFluidTankProperties() {
			
			@Override
			public FluidStack getContents() {
				Fluid f=getFluid();
				if(f==null){
					return new FluidStack(ModFluids.BIO_DIESEL, getFuelAmount());
				}else{
					return new FluidStack(f, getFuelAmount());
				}
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
		
		if(getFluid()!=null&&getFuelAmount()>0&&!resource.getFluid().equals(getFluid())){
			return 0;
		}
		
		int amount=Math.min(resource.amount, maxFuel-getFuelAmount());
		
		if(doFill){
			int i=getFuelAmount()+amount;
			if(i>maxFuel){
				i=maxFuel;
			}
			setFuelAmount(i);
			setFuelType(resource.getFluid());
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
