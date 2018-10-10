package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import javax.annotation.Nullable;

public class EntityGenericCar extends EntityCarNumberPlateBase{

    public EntityGenericCar(World worldIn) {
        super(worldIn);

    }

    @Override
    public float getMaxSpeed() {
        return 0.5F;
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.2F;
    }

    @Override
    public float getAcceleration() {
        return 0.032F;
    }

    @Override
    public float getMaxRotationSpeed() {
        return 5F;
    }

    @Override
    public float getMinRotationSpeed() {
        return 2.0F;
    }

    @Override
    public float getRollResistance() {
        return 0.02F;
    }

    @Override
    public float getOptimalTemperature() {
        return 90F;
    }

    @Override
    public int getMaxFuel() {
        return 1000;
    }

    @Override
    public double getEfficiency(@Nullable Fluid fluid) {
        return 1.0;
    }

    @Override
    public float getRotationModifier() {
        return 0.5F;
    }

    @Override
    public double getPlayerYOffset() {
        return 0.2D;
    }

    @Override
    public boolean doesEnterThirdPerson() {
        return true;
    }

    @Override
    public ITextComponent getCarName() {
        return new TextComponentString("Test");
    }

    public SoundEvent getStopSound() {
        return ModSounds.engine_stop;
    }

    public SoundEvent getFailSound() {
        return ModSounds.engine_fail;
    }

    public SoundEvent getCrashSound() {
        return ModSounds.car_crash;
    }

    public SoundEvent getStartSound() {
        return ModSounds.engine_start;
    }

    public SoundEvent getStartingSound() {
        return ModSounds.engine_starting;
    }

    public SoundEvent getIdleSound() {
        return ModSounds.engine_idle;
    }

    public SoundEvent getHighSound() {
        return ModSounds.engine_high;
    }

    public SoundEvent getHornSound() {
        return ModSounds.car_horn;
    }

    @Override
    public int getPassengerSize() {
        return 1;
    }
}
