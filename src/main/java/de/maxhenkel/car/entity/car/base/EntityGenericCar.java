package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.model.obj.OBJModel;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.entity.model.obj.OBJModelOptions;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;
import javax.annotation.Nullable;

public class EntityGenericCar extends EntityCarNumberPlateBase{

    private static final DataParameter<String> CHASSIS_TYPE = EntityDataManager.<String>createKey(EntityGenericCar.class, DataSerializers.STRING);

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

    @Override
    public int getPassengerSize() {
        return 1;
    }

    @Override
    public float getNumberPlateOffsetX() {
        return 0F;
    }

    @Override
    public float getNumberPlateOffsetY() {
        return -0.45F;
    }

    @Override
    public float getNumberPlateOffsetZ() {
        return -0.94F;
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

    private OBJModel wheel;
    private OBJModel chassis;

    private OBJModelInstance[] parts;

    private void initModel(){
        this.wheel = new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/wheel.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/wheel.png"));
        this.chassis = new OBJModel(
                new ResourceLocation(Main.MODID, "models/entity/woodcar.obj"),
                new ResourceLocation(Main.MODID, "textures/entity/oak_wood.png"));
        parts = new OBJModelInstance[]{
                new OBJModelInstance(wheel,
                        new OBJModelOptions(new Vec3d(9.5F / 16F, 4F / 16F, 8F / 16F)).setSpeedRotationFactor(80F)),
                new OBJModelInstance(wheel,
                        new OBJModelOptions(new Vec3d(9.5F / 16F, 4F / 16F, -8F / 16F)).setSpeedRotationFactor(80F)),
                new OBJModelInstance(wheel,
                        new OBJModelOptions(new Vec3d(-9.5F / 16F, 4F / 16F, 8F / 16F)).setSpeedRotationFactor(80F)),
                new OBJModelInstance(wheel,
                        new OBJModelOptions(new Vec3d(-9.5F / 16F, 4F / 16F, -8F / 16F)).setSpeedRotationFactor(80F)),
                new OBJModelInstance(chassis,
                        new OBJModelOptions(new Vec3d(0, 8.5F / 16F, 0)))
        };
    }

    public OBJModelInstance[] getModels() {
        if(parts==null){
            initModel();
        }
        return parts;
    }
}
