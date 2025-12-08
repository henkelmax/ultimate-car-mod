package de.maxhenkel.car.entity.car.base;

import javax.annotation.Nullable;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import java.util.AbstractMap;
import java.util.Map;

public abstract class EntityCarFuelBase extends EntityCarDamageBase implements ResourceHandler<FluidResource> {

    private static final EntityDataAccessor<Integer> FUEL_AMOUNT = SynchedEntityData.defineId(EntityCarFuelBase.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> FUEL_TYPE = SynchedEntityData.defineId(EntityCarFuelBase.class, EntityDataSerializers.STRING);

    private final SnapshotJournal<Map.Entry<Fluid, Integer>> fluidJournal = new SnapshotJournal<>() {
        @Override
        protected Map.Entry<Fluid, Integer> createSnapshot() {
            return new AbstractMap.SimpleEntry<>(getFluid(), getFuelAmount());
        }

        @Override
        protected void revertToSnapshot(Map.Entry<Fluid, Integer> snapshot) {
            Fluid fluid = snapshot.getKey();
            if (fluid != null) {
                setFuelType(fluid);
            } else {
                setFuelType((String) null);
            }
            setFuelAmount(snapshot.getValue());
        }
    };

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

        return BuiltInRegistries.FLUID.getValue(Identifier.parse(fuelType));
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
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putInt("fuel", getFuelAmount());
        valueOutput.putString("fuel_type", getFuelType());
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        setFuelAmount(valueInput.getIntOr("fuel", 0));
        valueInput.getString("fuel_type").ifPresent(this::setFuelType);
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidResource getResource(int index) {
        if (index == 0) {
            int fuelAmount = getFuelAmount();
            if (fuelAmount <= 0) {
                return FluidResource.of(Fluids.EMPTY);
            }
            Fluid f = getFluid();
            if (f == null) {
                return FluidResource.of(new FluidStack(ModFluids.BIO_DIESEL.get(), fuelAmount));
            } else {
                return FluidResource.of(new FluidStack(f, fuelAmount));
            }
        }
        return FluidResource.of(Fluids.EMPTY);
    }

    @Override
    public long getAmountAsLong(int index) {
        if (index == 0) {
            return getFuelAmount();
        }
        return 0;
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        if (index == 0) {
            Fluid fluid = getFluid();
            if (fluid == null || resource.is(fluid)) {
                return getMaxFuel();
            }

        }
        return 0;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return isValidFuel(resource.getFluid());
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (index != 0) {
            return 0;
        }
        if (!isValidFuel(resource.getFluid())) {
            return 0;
        }
        Fluid fluid = getFluid();
        if (fluid != null && getFuelAmount() > 0 && !resource.getFluid().equals(fluid)) {
            return 0;
        }

        int actualAmount = Math.min(amount, getMaxFuel() - getFuelAmount());

        fluidJournal.updateSnapshots(transaction);
        int i = getFuelAmount() + actualAmount;
        if (i > getMaxFuel()) {
            i = getMaxFuel();
        }
        setFuelAmount(i);
        setFuelType(resource.getFluid());

        return actualAmount;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        Fluid fluid = getFluid();
        if (fluid != null && !resource.is(fluid)) {
            return 0;
        }
        int currentAmount = getFuelAmount();

        int extractedAmount = Math.min(amount, currentAmount);

        fluidJournal.updateSnapshots(transaction);
        int newAmount = currentAmount - extractedAmount;

        if (newAmount <= 0) {
            setFuelType((String) null);
            setFuelAmount(0);
        } else {
            setFuelAmount(newAmount);
        }

        return extractedAmount;
    }

}
