package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopStarting;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public abstract class EntityCarBatteryBase extends EntityCarTemperatureBase {

    private static final EntityDataAccessor<Integer> BATTERY_LEVEL = SynchedEntityData.defineId(EntityCarBatteryBase.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> STARTING_TIME = SynchedEntityData.defineId(EntityCarBatteryBase.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> STARTING = SynchedEntityData.defineId(EntityCarBatteryBase.class, EntityDataSerializers.BOOLEAN);

    @OnlyIn(Dist.CLIENT)
    private SoundLoopStarting startingLoop;

    //Server side
    private boolean carStopped;
    private boolean carStarted;

    //Client side
    private int timeSinceStarted;

    private int timeToStart;

    public EntityCarBatteryBase(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    // /summon car:car_wood ~ ~ ~ {"fuel": 1000, "battery":10000}
    @Override
    public void tick() {
        super.tick();

        if (level().isClientSide) {
            if (isStarted()) {
                timeSinceStarted++;
                if (tickCount % 2 == 0) { //How often particles will spawn
                    spawnParticles(getSpeed() > 0.1F);
                }
            } else {
                timeSinceStarted = 0;
            }
            return; //Important because car not going off bug
        }

        if (isStarting()) {
            if (tickCount % 2 == 0) {
                setBatteryLevel(getBatteryLevel() - getBatteryUsage());
            }

            setStartingTime(getStartingTime() + 1);
            if (getBatteryLevel() <= 0) {
                setStarting(false, true);//??
            }
        } else {
            setStartingTime(0);
        }

        int time = getStartingTime();

        if (time > 0) { // prevent always calling gettimetostart
            if (timeToStart <= 0) {
                timeToStart = getTimeToStart();
            }

            if (time > getTimeToStart()) {
                startCarEngine();
                timeToStart = 0;
            }
        }

        if (isStarted()) {
            setStartingTime(0);
            carStarted = true;
            float speedPerc = getSpeed() / getMaxSpeed();

            int chargingRate = (int) (speedPerc * 7F);
            if (chargingRate < 5) {
                chargingRate = 1;
            }

            if (tickCount % 20 == 0) {
                setBatteryLevel(getBatteryLevel() + chargingRate);
            }
        }
    }

    public void spawnParticles(boolean driving) {
        if (!level().isClientSide) {
            return;
        }
        Vec3 lookVec = getLookAngle().normalize();
        double offX = lookVec.x * -1D;
        double offY = lookVec.y;
        double offZ = lookVec.z * -1D;

        //Engine started smoke should only come 1 second after start and only if the engine is colder than 50°C
        if (timeSinceStarted > 0 && timeSinceStarted < 20 && getTemperature() < 50F) {
            double speedX = lookVec.x * -0.1D;
            double speedZ = lookVec.z * -0.1D;
            if (this instanceof EntityCarDamageBase) {
                float damage = ((EntityCarDamageBase) this).getDamage();
                int count = 1;
                double r = 0.1;

                if (damage > 0.9F) {
                    count = 6;
                    r = 0.7;
                } else if (damage > 0.75F) {
                    count = 3;
                    r = 0.7;
                } else if (damage > 0.5F) {
                    count = 2;
                    r = 0.3;
                }
                for (int i = 0; i <= count; i++) {
                    spawnParticle(ParticleTypes.LARGE_SMOKE, offX, offY, offZ, speedX, speedZ, r);
                }
            } else {
                spawnParticle(ParticleTypes.LARGE_SMOKE, offX, offY, offZ, speedX, speedZ);
            }
        } else if (driving) {
            double speedX = lookVec.x * -0.2D;
            double speedZ = lookVec.z * -0.2D;
            spawnParticle(ParticleTypes.SMOKE, offX, offY, offZ, speedX, speedZ);
        } else {
            double speedX = lookVec.x * -0.05D;
            double speedZ = lookVec.z * -0.05D;
            spawnParticle(ParticleTypes.SMOKE, offX, offY, offZ, speedX, speedZ);
        }

    }

    private void spawnParticle(ParticleOptions particleTypes, double offX, double offY, double offZ, double speedX, double speedZ, double r) {
        level().addParticle(particleTypes,
                getX() + offX + (random.nextDouble() * r - r / 2D),
                getY() + offY + (random.nextDouble() * r - r / 2D) + getCarHeight() / 8F,
                getZ() + offZ + (random.nextDouble() * r - r / 2D),
                speedX, 0.0D, speedZ);
    }

    private void spawnParticle(ParticleOptions particleTypes, double offX, double offY, double offZ, double speedX, double speedZ) {
        spawnParticle(particleTypes, offX, offY, offZ, speedX, speedZ, 0.1D);
    }

    public int getTimeToStart() {
        int time = random.nextInt(10) + 5;

        float temp = getTemperature();
        if (temp < 0F) {
            time += 40;
        } else if (temp < 10F) {
            time += 35;
        } else if (temp < 30F) {
            time += 10;
        } else if (temp < 60F) {
            time += 5;
        }

        float batteryPerc = getBatteryPercentage();

        if (batteryPerc < 0.5F) {
            time += 20 + random.nextInt(10);
        } else if (batteryPerc < 0.75F) {
            time += 10 + random.nextInt(10);
        }

        return time;
    }

    public int getBatteryUsage() {
        if (!Main.SERVER_CONFIG.useBattery.get()) {
            return 0;
        }

        float temp = getBiomeTemperatureCelsius();
        int baseUsage = 2;
        if (temp < 0F) {
            baseUsage += 2;
        } else if (temp < 15F) {
            baseUsage += 1;
        }
        return baseUsage;
    }

    @Override
    public void setStarted(boolean started) {
        setStarting(false, false);
        super.setStarted(started);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(BATTERY_LEVEL, getMaxBatteryLevel());
        this.entityData.define(STARTING_TIME, 0);
        this.entityData.define(STARTING, Boolean.FALSE);
    }

    public int getStartingTime() {
        return this.entityData.get(STARTING_TIME);
    }

    public void setStartingTime(int time) {
        this.entityData.set(STARTING_TIME, time);
    }

    public boolean isStarting() {
        return this.entityData.get(STARTING);
    }

    /**
     * Fail sound is only played when stopping the starting process
     */
    public void setStarting(boolean starting, boolean playFailSound) {
        if (starting) {
            if (getBatteryLevel() <= 0) {
                return;
            }
            if (isStarted()) {
                setStarted(false, true, false);
                carStopped = true;
                return;
            }
        } else {
            if (carStarted || carStopped) {
                //TO prevent car from making stop start sound after releasing the starter key
                carStopped = false;
                carStarted = false;
                return;
            }
            if (playFailSound) {
                if (getBatteryLevel() > 0) {
                    playFailSound();
                }
            }
        }
        this.entityData.set(STARTING, starting);
    }

    public float getBatterySoundPitchLevel() {

        int batteryLevel = getBatteryLevel();

        int startLevel = getMaxBatteryLevel() / 3;

        float basePitch = 1F - 0.002F * ((float) getStartingTime());

        if (batteryLevel > startLevel) {
            return basePitch;
        }

        int levelUnder = startLevel - batteryLevel;

        float perc = (float) levelUnder / (float) startLevel;

        return basePitch - (perc / 2.3F);
    }

    public float getBatteryPercentage() {
        return ((float) getBatteryLevel()) / ((float) getMaxBatteryLevel());
    }

    public void setBatteryLevel(int level) {
        if (level < 0) {
            level = 0;
        } else if (level > getMaxBatteryLevel()) {
            level = getMaxBatteryLevel();
        }
        this.entityData.set(BATTERY_LEVEL, level);
    }

    public int getBatteryLevel() {
        return this.entityData.get(BATTERY_LEVEL);
    }

    public int getMaxBatteryLevel() {
        return 1000;
    }

    @Override
    public void updateSounds() {
        if (!isStarted() && isStarting()) {
            checkStartingLoop();
        }
        super.updateSounds();
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setBatteryLevel(compound.getInt("battery"));
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("battery", getBatteryLevel());
    }

    @OnlyIn(Dist.CLIENT)
    public void checkStartingLoop() {
        if (!isSoundPlaying(startingLoop)) {
            startingLoop = new SoundLoopStarting(this, getStartingSound(), SoundSource.MASTER);
            ModSounds.playSoundLoop(startingLoop, level());
        }
    }

    @Override
    public void playFailSound() {
        ModSounds.playSound(getFailSound(), level(), blockPosition(), null, SoundSource.MASTER, 1F, getBatterySoundPitchLevel());
    }

}
