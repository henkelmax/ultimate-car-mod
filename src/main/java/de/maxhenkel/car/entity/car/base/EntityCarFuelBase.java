package de.maxhenkel.car.entity.car.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class EntityCarFuelBase extends EntityCarDamageBase implements IFluidHandler {

    private static final DataParameter<Integer> FUEL_AMOUNT = EntityDataManager.createKey(EntityCarFuelBase.class, DataSerializers.VARINT);
    private static final DataParameter<String> FUEL_TYPE = EntityDataManager.createKey(EntityCarFuelBase.class, DataSerializers.STRING);

    public EntityCarFuelBase(EntityType type, World worldIn) {
        super(type, worldIn);
    }

    public abstract int getMaxFuel();

    @Override
    public void tick() {
        super.tick();

        fuelTick();
    }

    protected void fuelTick() {
        int fuel = getFuelAmount();
        int tickFuel = getEfficiency(getFluid());
        if (tickFuel <= 0) {
            return;
        }
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
        dataManager.register(FUEL_AMOUNT, 0);
        dataManager.register(FUEL_TYPE, "");
    }

    public void setFuelAmount(int fuel) {
        this.dataManager.set(FUEL_AMOUNT, fuel);
    }

    public void setFuelType(String fluid) {
        if (fluid == null) {
            fluid = "";
        }
        dataManager.set(FUEL_TYPE, fluid);
    }

    public void setFuelType(Fluid fluid) {
        setFuelType(fluid.getRegistryName().toString());
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

        return ForgeRegistries.FLUIDS.getValue(new ResourceLocation(fuelType));
    }

    public int getFuelAmount() {
        return this.dataManager.get(FUEL_AMOUNT);
    }

    public boolean isValidFuel(Fluid fluid) {
        if (fluid == null) {
            return false;
        }
        return getEfficiency(fluid) > 0;
    }

    public abstract int getEfficiency(@Nullable Fluid fluid);

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
        }
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        Fluid f = getFluid();
        if (f == null) {
            return new FluidStack(ModFluids.BIO_DIESEL, getFuelAmount());
        } else {
            return new FluidStack(f, getFuelAmount());
        }
    }

    @Override
    public int getTankCapacity(int tank) {
        return getMaxFuel();
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return isValidFuel(stack.getFluid());
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource == null || !isValidFuel(resource.getFluid())) {
            return 0;
        }

        if (getFluid() != null && getFuelAmount() > 0 && !resource.getFluid().equals(getFluid())) {
            return 0;
        }

        int amount = Math.min(resource.getAmount(), getMaxFuel() - getFuelAmount());

        if (action.execute()) {
            int i = getFuelAmount() + amount;
            if (i > getMaxFuel()) {
                i = getMaxFuel();
            }
            setFuelAmount(i);
            setFuelType(resource.getFluid());
        }

        return amount;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource == null) {
            return FluidStack.EMPTY;
        }

        if (resource.getFluid() == null || !resource.getFluid().equals(getFluid())) {
            return FluidStack.EMPTY;
        }

        return drain(resource.getAmount(), action);
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        Fluid fluid = getFluid();
        int totalAmount = getFuelAmount();

        if (fluid == null) {
            return null;
        }

        int amount = Math.min(maxDrain, totalAmount);


        if (action.execute()) {
            int newAmount = totalAmount - amount;


            if (newAmount <= 0) {
                setFuelType((String) null);
                setFuelAmount(0);
            } else {
                setFuelAmount(newAmount);
            }
        }

        return new FluidStack(fluid, amount);
    }

}
