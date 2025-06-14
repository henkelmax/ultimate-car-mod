package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class EntityCarTemperatureBase extends EntityCarBase {

    private static final EntityDataAccessor<Float> TEMPERATURE = SynchedEntityData.defineId(EntityCarTemperatureBase.class, EntityDataSerializers.FLOAT);

    public EntityCarTemperatureBase(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            return;
        }
        if (tickCount % 20 != 0) {
            return;
        }

        float speedPerc = getSpeed() / getMaxSpeed();

        int tempRate = (int) (speedPerc * 10F) + 1;

        if (tempRate > 5) {
            tempRate = 5;
        }

        float rate = tempRate * 0.2F + (random.nextFloat() - 0.5F) * 0.1F;

        float temp = getTemperature();

        float tempToReach = getTemperatureToReach();

        if (MathUtils.isInBounds(temp, tempToReach, rate)) {
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
        return (level().getBiome(blockPosition()).value().getHeightAdjustedTemperature(blockPosition(), blockPosition().getY()) - 0.3F) * 30F;
    }

    public float getTemperature() {
        return this.entityData.get(TEMPERATURE);
    }

    public void setTemperature(float temperature) {
        this.entityData.set(TEMPERATURE, temperature);
    }

    public abstract float getOptimalTemperature();

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(TEMPERATURE, 0F);
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        setTemperature(valueInput.getFloatOr("temperature", 0F));
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putFloat("temperature", getTemperature());
    }

    /**
     * Sets the car temperature to the current temperature at the cars position
     */
    public void initTemperature() {
        setTemperature(getBiomeTemperatureCelsius());
    }

}
