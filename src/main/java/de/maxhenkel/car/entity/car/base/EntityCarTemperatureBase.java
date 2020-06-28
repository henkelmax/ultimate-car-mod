package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.tools.MathTools;
import net.minecraft.entity.EntityType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;

public abstract class EntityCarTemperatureBase extends EntityCarBase {

    private static final DataParameter<Float> TEMPERATURE = EntityDataManager.<Float>createKey(EntityCarTemperatureBase.class, DataSerializers.FLOAT);

    public EntityCarTemperatureBase(EntityType type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        if (world.isRemote) {
            return;
        }
        if (ticksExisted % 20 != 0) {
            return;
        }

        float speedPerc = getSpeed() / getMaxSpeed();

        int tempRate = (int) (speedPerc * 10F) + 1;

        if (tempRate > 5) {
            tempRate = 5;
        }

        float rate = tempRate * 0.2F + (rand.nextFloat() - 0.5F) * 0.1F;

        float temp = getTemperature();

        float tempToReach = getTemperatureToReach();

        if (MathTools.isInBounds(temp, tempToReach, rate)) {
            setTemperature(tempToReach);
        } else {
            if (tempToReach < temp) {
                rate = -rate;
            }
            setTemperature(temp + rate);
        }
    }

    public float getTemperatureToReach() {
        float biomeTemp = getBiomeTemperatureCelsius();

        if (!isStarted()) {
            return biomeTemp;
        }
        float optimalTemp = getOptimalTemperature();

        if (biomeTemp > 45F) {
            optimalTemp = 100F;
        } else if (biomeTemp <= 0F) {
            optimalTemp = 80F;
        }
        return Math.max(biomeTemp, optimalTemp);
    }

    public float getBiomeTemperatureCelsius() {
        return (world.getBiome(func_233580_cy_()).getTemperature(func_233580_cy_()) - 0.3F) * 30F;
    }

    public float getTemperature() {
        return this.dataManager.get(TEMPERATURE);
    }

    public void setTemperature(float temperature) {
        this.dataManager.set(TEMPERATURE, Float.valueOf(temperature));
    }

    public abstract float getOptimalTemperature();

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(TEMPERATURE, Float.valueOf(0F));
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setTemperature(compound.getFloat("temperature"));
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putFloat("temperature", getTemperature());
    }

    /**
     * Sets the car temperature to the current temperature at the cars position
     */
    public void initTemperature() {
        setTemperature(getBiomeTemperatureCelsius());
    }

}
