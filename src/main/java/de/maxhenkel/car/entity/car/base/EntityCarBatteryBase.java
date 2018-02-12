package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopStart;
import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityCarBatteryBase extends EntityCarBase {

    private static final DataParameter<Integer> BATTERY_LEVEL = EntityDataManager.<Integer>createKey(EntityCarBatteryBase.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> STARTING_TIME = EntityDataManager.<Integer>createKey(EntityCarBatteryBase.class,
            DataSerializers.VARINT);
    private static final DataParameter<Boolean> STARTING = EntityDataManager.<Boolean>createKey(EntityCarBatteryBase.class,
            DataSerializers.BOOLEAN);

    @SideOnly(Side.CLIENT)
    private SoundLoopStart startLoop;

    //Server side
    private boolean carStopped;
    private boolean carStarted;

    //Client side
    private int timeSinceStarted;

    public EntityCarBatteryBase(World worldIn) {
        super(worldIn);
    }

    // /summon car:car_wood ~ ~ ~ {"fuel": 1000, "battery":10000}
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (world.isRemote) {
            if (isStarted()) {
                timeSinceStarted++;
                if (ticksExisted % 2 == 0) {//How often particles will spawn
                    spawnParticles(getSpeed() > 0.1F);
                }
            } else {
                timeSinceStarted = 0;
            }
            return; //Important because car not going off bug
        }

        if (isStarting()) {
            setBatteryLevel(getBatteryLevel() - getBatteryUsage());
            //System.out.println(getBatteryLevel());
            setStartingTime(getStartingTime() + 1);
            if (getBatteryLevel() <= 0) {
                setStarting(false, true);//??
            }
        } else {
            setStartingTime(0);
        }
        if (getStartingTime() > getTimeToStart()) {
            startCarEngine();
        }

        if (isStarted()) {
            setStartingTime(0);
            carStarted = true;
            float speedPerc = getSpeed() / getMaxSpeed();

            int chargingRate = (int) (speedPerc * 10F);
            if (chargingRate < 5) {
                chargingRate = 1;
            }

            if (ticksExisted % 20 == 0) {
                //System.out.println("Battery: " + getBatteryLevel() + " chargingRate: " + chargingRate + " temp: " + getTemperature() + " baseUsage: " + getBatteryUsage() + " carStopped: " + carStopped+ " carStarted: " + carStarted);
                setBatteryLevel(getBatteryLevel() + chargingRate);
            }
        }
    }

    public void spawnParticles(boolean driving) {
        if (!world.isRemote) {
            return;
        }
        Vec3d lookVec = getLookVec().normalize();
        double offX = lookVec.x * -width / 2;
        double offY = lookVec.y;
        double offZ = lookVec.z * -width / 2;

        if (timeSinceStarted > 0 && timeSinceStarted < 20) {
            double speedX = lookVec.x * -0.1D;
            double speedZ = lookVec.z * -0.1D;
            if (this instanceof EntityCarDamageBase) {
                float damage = ((EntityCarDamageBase) this).getDamage();
                int count = 1;
                double r = 0.1;
                System.out.println(damage);
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
                    spawnParticle(EnumParticleTypes.SMOKE_LARGE, offX, offY, offZ, speedX, speedZ, r);
                }
            } else {
                spawnParticle(EnumParticleTypes.SMOKE_LARGE, offX, offY, offZ, speedX, speedZ);
            }
        } else if (driving) {
            double speedX = lookVec.x * -0.2D;
            double speedZ = lookVec.z * -0.2D;
            spawnParticle(EnumParticleTypes.SMOKE_NORMAL, offX, offY, offZ, speedX, speedZ);
        } else {
            double speedX = lookVec.x * -0.05D;
            double speedZ = lookVec.z * -0.05D;
            spawnParticle(EnumParticleTypes.SMOKE_NORMAL, offX, offY, offZ, speedX, speedZ);
        }

    }

    private void spawnParticle(EnumParticleTypes particleTypes, double offX, double offY, double offZ, double speedX, double speedZ, double random) {
        world.spawnParticle(particleTypes,
                posX + offX + (rand.nextDouble() * random),
                posY + (rand.nextDouble() * random) + height / 8F,
                posZ + offZ + (rand.nextDouble() * random), speedX, 0.0D, speedZ);
    }

    private void spawnParticle(EnumParticleTypes particleTypes, double offX, double offY, double offZ, double speedX, double speedZ) {
        spawnParticle(particleTypes, offX, offY, offZ, speedX, speedZ, 0.1D);
    }

    public int getTimeToStart() {
        int baseTime = rand.nextInt(20) + 10;

        float temp = getTemperature();

        if (temp < 0F) {
            baseTime += 30;
        } else if (temp < -0.3F) {
            baseTime += 40;
        }

        float batteryPerc = getBatteryPercentage();

        if (batteryPerc < 0.5) {
            baseTime += 25;
        } else if (batteryPerc < 0.75) {
            baseTime += 15;
        }

        return baseTime;
    }

    public float getTemperature() {
        return world.getBiome(getPosition()).getTemperature(getPosition()) - 0.3F;
    }

    public int getBatteryUsage() {
        if (!Config.useBattery) {
            return 0;
        }

        float temp = getTemperature();
        int baseUsage = 4;
        if (temp < -0.3F) {
            baseUsage += 2;
        } else if (temp < 0F) {
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
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(BATTERY_LEVEL, Integer.valueOf(getMaxBatteryLevel()));
        this.dataManager.register(STARTING_TIME, Integer.valueOf(0));
        this.dataManager.register(STARTING, Boolean.valueOf(false));
    }

    public int getStartingTime() {
        return this.dataManager.get(STARTING_TIME);
    }

    public void setStartingTime(int time) {
        this.dataManager.set(STARTING_TIME, Integer.valueOf(time));
    }

    public boolean isStarting() {
        return this.dataManager.get(STARTING);
    }

    /**
     * Fail sound is only played when stopping the starting process
     */
    public void setStarting(boolean starting, boolean playFailSound) {
        if (starting) {
            if (getBatteryLevel() <= 0) {
                return;//TODO nostart
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
                    playFailSound();//TODO nost
                }
            }
        }
        this.dataManager.set(STARTING, Boolean.valueOf(starting));
    }

    public float getBatterySoundPitchLevel() {

        int batteryLevel = getBatteryLevel();

        int startLevel = getMaxBatteryLevel() / 3;//TODO change maybe

        float basePitch = 1F - 0.002F * ((float) getStartingTime());

        if (batteryLevel > startLevel) {
            return basePitch;
        }

        int levelUnder = startLevel - batteryLevel;

        float perc = (float) levelUnder / (float) startLevel;

        float pitch = basePitch - (perc / 2.3F); //2 = max 0.5 pitch
        //System.out.println(pitch);
        return pitch;
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
        this.dataManager.set(BATTERY_LEVEL, Integer.valueOf(level));
    }

    public int getBatteryLevel() {
        return this.dataManager.get(BATTERY_LEVEL);
    }

    public int getMaxBatteryLevel() {
        return 1000;
    }

    @Override
    public void updateSounds() {
        if (!isStarted() && isStarting()) {
            checkStartLoop();
        }
        super.updateSounds();
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setBatteryLevel(compound.getInteger("battery"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("battery", getBatteryLevel());
    }

    @SideOnly(Side.CLIENT)
    public void checkStartLoop() {
        if (startLoop == null || startLoop.isDonePlaying()) {
            startLoop = new SoundLoopStart(world, this, getStartSound(), SoundCategory.NEUTRAL);
            ModSounds.playSoundLoop(startLoop, world);
        }
    }

    @Override
    public void playFailSound() {
        ModSounds.playSound(getFailSound(), world, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume, getBatterySoundPitchLevel());
    }
}