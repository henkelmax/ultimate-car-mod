package de.maxhenkel.car.entity.car.base;

import javax.annotation.Nullable;

import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.tools.FluidStackWrapper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public abstract class EntityCarFuelBase extends EntityCarDamageBase implements IFluidHandler {

    private static final DataParameter<Integer> FUEL_AMOUNT = EntityDataManager.<Integer>createKey(EntityCarFuelBase.class,
            DataSerializers.VARINT);
    private static final DataParameter<String> FUEL_TYPE = EntityDataManager.<String>createKey(EntityCarFuelBase.class,
            DataSerializers.STRING);

    public EntityCarFuelBase(EntityType type, World worldIn) {
        super(type, worldIn);
    }

    public abstract int getMaxFuel();

    @Override
    public void tick() {
        super.tick();

        fuelTick();
    }

    protected int calculateTickFuel() {
        float efficiency = getEfficiency(getFluid());
        int ticks = (int) (efficiency * 100D);
        if (ticks <= 0) {
            ticks = 1;
        }
        return ticks;
    }

    protected void fuelTick() {
        float fuel = getFuelAmount();
        int tickFuel = calculateTickFuel();
        if (fuel > 0 && isAccelerating()) {
            if (ticksExisted % tickFuel == 0) {
                acceleratingFuelTick();
            }
        } else if (fuel > 0 && isStarted()) {
            if (ticksExisted % (tickFuel * 100) == 0) {
                idleFuelTick();
            }
        }
    }

    protected void idleFuelTick() {
        removeFuel(1);
    }

    protected void acceleratingFuelTick() {
        removeFuel(1);
    }

    private void removeFuel(int amount) {
        int fuel = getFuelAmount();
        int newFuel = fuel - amount;
        if (newFuel <= 0) {
            setFuelAmount(0);
        } else {
            setFuelAmount(newFuel);
        }
    }

    @Override
    public boolean canPlayerDriveCar(PlayerEntity player) {

        if (getFuelAmount() <= 0) {
            return false;
        }

        return super.canPlayerDriveCar(player);
    }

    @Override
    public boolean canStartCarEngine(PlayerEntity player) {
        if (getFuelAmount() <= 0) {
            return false;
        }

        return super.canStartCarEngine(player);
    }

    public boolean canEngineStayOn() {
        if (getFuelAmount() <= 0) {
            return false;
        }

        return super.canEngineStayOn();
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(FUEL_AMOUNT, Integer.valueOf(0));
        this.dataManager.register(FUEL_TYPE, new String());
    }

    public void setFuelAmount(int fuel) {
        this.dataManager.set(FUEL_AMOUNT, Integer.valueOf(fuel));
    }

    public void setFuelType(String fluid) {
        if (fluid == null) {
            fluid = "";
        }
        this.dataManager.set(FUEL_TYPE, fluid);
    }

    public void setFuelType(Fluid fluid) {
        String fluidName = fluid.getName();
        if (fluidName == null) {
            fluidName = "";
        }
        this.dataManager.set(FUEL_TYPE, fluidName);
    }

    public String getFuelType() {
        return this.dataManager.get(FUEL_TYPE);
    }

    @Nullable
    public Fluid getFluid() {
        String fuelType = getFuelType();
        if (fuelType == null || fuelType.isEmpty()) {
            return null;
        }
        return FluidStackWrapper.byName(fuelType);
    }

    public int getFuelAmount() {
        return this.dataManager.get(FUEL_AMOUNT);
    }

    public boolean isValidFuel(Fluid fluid) {
        if (fluid == null) {
            return false;
        }
        return getEfficiency(fluid) > 0F;
    }

    public abstract float getEfficiency(@Nullable Fluid fluid);

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("fuel", getFuelAmount());
        compound.putString("fuel_type", getFuelType());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setFuelAmount(compound.getInt("fuel"));
        if (compound.contains("fuel_type")) {
            setFuelType(compound.getString("fuel_type"));
        } else {
            setFuelType(ModFluids.BIO_DIESEL.getName());
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{new IFluidTankProperties() {

            @Override
            public FluidStack getContents() {
                Fluid f = getFluid();
                if (f == null) {
                    return new FluidStackWrapper(ModFluids.BIO_DIESEL, getFuelAmount());
                } else {
                    return new FluidStackWrapper(f, getFuelAmount());
                }
            }

            @Override
            public int getCapacity() {
                return getMaxFuel();
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
                Fluid f = getFluid();
                if (f == null) {
                    return false;
                }
                return fluidStack.getFluid().equals(f);
            }

            @Override
            public boolean canDrain() {
                return true;
            }
        }};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {

        if (resource == null || !isValidFuel(resource.getFluid())) {
            return 0;
        }

        if (getFluid() != null && getFuelAmount() > 0 && !resource.getFluid().equals(getFluid())) {
            return 0;
        }

        int amount = Math.min(resource.amount, getMaxFuel() - getFuelAmount());

        if (doFill) {
            int i = getFuelAmount() + amount;
            if (i > getMaxFuel()) {
                i = getMaxFuel();
            }
            setFuelAmount(i);
            setFuelType(resource.getFluid());
        }

        return amount;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null) {
            return null;
        }

        if (resource.getFluid() == null || !resource.getFluid().equals(getFluid())) {
            return null;
        }

        return drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        Fluid fluid = getFluid();
        int totalAmount = getFuelAmount();

        if (fluid == null) {
            return null;
        }

        int amount = Math.min(maxDrain, totalAmount);


        if (doDrain) {
            int newAmount = totalAmount - amount;


            if (newAmount <= 0) {
                setFuelType((String) null);
                setFuelAmount(0);
            } else {
                setFuelAmount(newAmount);
            }
        }

        return new FluidStackWrapper(fluid, amount);
    }

}
