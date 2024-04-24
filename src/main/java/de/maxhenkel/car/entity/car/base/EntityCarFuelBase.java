package de.maxhenkel.car.entity.car.base;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public abstract class EntityCarFuelBase extends EntityCarDamageBase implements IFluidHandler {

    private static final EntityDataAccessor<Integer> FUEL_AMOUNT = SynchedEntityData.defineId(EntityCarFuelBase.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> FUEL_TYPE = SynchedEntityData.defineId(EntityCarFuelBase.class, EntityDataSerializers.STRING);

    public EntityCarFuelBase(EntityType type, Level worldIn) {
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
            if (tickCount % tickFuel == 0) {
                acceleratingFuelTick();
            }
        } else if (fuel > 0 && isStarted()) {
            if (tickCount % (tickFuel * 100) == 0) {
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
        setFuelAmount(Math.max(newFuel, 0));
    }

    @Override
    public boolean canPlayerDriveCar(Player player) {

        if (getFuelAmount() <= 0) {
            return false;
        }

        return super.canPlayerDriveCar(player);
    }

    @Override
    public boolean canStartCarEngine(Player player) {
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
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(FUEL_AMOUNT, 0);
        builder.define(FUEL_TYPE, "");
    }

    public void setFuelAmount(int fuel) {
        this.entityData.set(FUEL_AMOUNT, fuel);
    }

    public void setFuelType(String fluid) {
        if (fluid == null) {
            fluid = "";
        }
        entityData.set(FUEL_TYPE, fluid);
    }

    public void setFuelType(Fluid fluid) {
        setFuelType(BuiltInRegistries.FLUID.getKey(fluid).toString());
    }

    public String getFuelType() {
        return this.entityData.get(FUEL_TYPE);
    }

    @Nullable
    public Fluid getFluid() {
        String fuelType = getFuelType();
        if (fuelType == null || fuelType.isEmpty()) {
            return null;
        }

        return BuiltInRegistries.FLUID.get(new ResourceLocation(fuelType));
    }

    public int getFuelAmount() {
        return this.entityData.get(FUEL_AMOUNT);
    }

    public boolean isValidFuel(Fluid fluid) {
        if (fluid == null) {
            return false;
        }
        return getEfficiency(fluid) > 0;
    }

    public abstract int getEfficiency(@Nullable Fluid fluid);

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("fuel", getFuelAmount());
        compound.putString("fuel_type", getFuelType());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
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
            return new FluidStack(ModFluids.BIO_DIESEL.get(), getFuelAmount());
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
            return FluidStack.EMPTY;
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
