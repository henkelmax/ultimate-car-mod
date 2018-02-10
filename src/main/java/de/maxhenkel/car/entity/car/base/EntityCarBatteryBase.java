package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.net.MessageStartCar;
import de.maxhenkel.car.net.MessageStarting;
import de.maxhenkel.car.proxy.CommonProxy;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public abstract class EntityCarBatteryBase extends EntityCarBase {

    private static final DataParameter<Integer> BATTERY_LEVEL = EntityDataManager.<Integer>createKey(EntityCarBatteryBase.class,
            DataSerializers.VARINT);
    private static final DataParameter<Integer> STARTING_TIME = EntityDataManager.<Integer>createKey(EntityCarBatteryBase.class,
            DataSerializers.VARINT);
    private static final DataParameter<Boolean> STARTING = EntityDataManager.<Boolean>createKey(EntityCarBatteryBase.class,
            DataSerializers.BOOLEAN);

    private boolean carStopping;

    public EntityCarBatteryBase(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (isStarting()) {
            setBatteryLevel(getBatteryLevel() - 2);
            System.out.println(getBatteryLevel());
            setStartingTime(getStartingTime() + 1);
        } else {
            setStartingTime(0);
        }
        if (getStartingTime() > 50) {//TODO random time or battery
            startCarEngine(getDriver());
            setStartingTime(0);
        }

    }

    @Override
    public void setStarted(boolean started) {
        EntityPlayer driver = getDriver();
        if (driver != null) {
            //CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStarting(false, false, driver));
        }
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
            if (isStarted()) {
                setStarted(false);
                carStopping = true;
                if (world.isRemote) {
                    EntityPlayer dr = getDriver();
                    if (dr != null) {
                        CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStartCar(false, true, false, dr));
                    }
                }
                return;
            }
        } else {
            if (carStopping) {
                //TO prevent car from making stop start sound after releasing the starter key
                carStopping = false;
                return;
            }
            if (world.isRemote) {
                EntityPlayer dr = getDriver();
                if (!isStarted() && dr != null) {
                    CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStartCar(false, false, playFailSound, dr));
                }
            }
        }
        this.dataManager.set(STARTING, Boolean.valueOf(starting));
    }

    public float getBatterySoundPitchLevel() {
        float batteryLevel = getBatteryPercentage();

        return batteryLevel;/*

        if(batteryLevel<0.5){//TODO change to smaller value
            return 2F*batteryLevel - 0.1F*((float) getStartingTime());
        }
        return 1F;*/
    }

    public float getBatteryPercentage() {
        float max = getMaxBatteryLevel();
        float curr = getBatteryLevel();
        return curr / max;
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
        System.out.println("Read: " + compound.getInteger("battery"));
        setBatteryLevel(compound.getInteger("battery"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("battery", getBatteryLevel());
        System.out.println("Write: " + compound.getInteger("battery"));
    }

    @Override
    public void playFailSound() {
        System.out.println("fail: " + getBatterySoundPitchLevel());
        ModSounds.playSound(getFailSound(), world, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume, getBatterySoundPitchLevel());
    }
}
