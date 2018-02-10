package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopStart;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundCategory;
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

    boolean carStarted;
    boolean carStopped;

    public EntityCarBatteryBase(World worldIn) {
        super(worldIn);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (isStarting()) {
            setBatteryLevel(getBatteryLevel() - 4);
            System.out.println(getBatteryLevel());
            setStartingTime(getStartingTime() + 1);
        } else {
            setStartingTime(0);
        }
        if (getStartingTime() > 50) {//TODO random time or battery
            startCarEngine();
            carStarted = true;
            setStartingTime(0);
        }

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
            if (isStarted()) {
                setStarted(false, true, false);
                carStopped = true;
                return;
            }
        } else {
            if (carStarted || carStopped) {
                //TO prevent car from making stop start sound after releasing the starter key
                carStarted = false;
                carStopped = false;
                return;
            }
            setStarted(false, false, playFailSound);
        }
        this.dataManager.set(STARTING, Boolean.valueOf(starting));
    }

    public float getBatterySoundPitchLevel() {

        int batteryLevel = getBatteryLevel();

        int startLevel = getMaxBatteryLevel() / 2;//TODO change to smaller value

        if (batteryLevel > startLevel) {
            return 1F;
        }

        int levelUnder = startLevel - batteryLevel;

        float perc = (float) levelUnder / (float) startLevel;

        float pitch = 1 - (perc / 2);
        System.out.println(pitch);
        return pitch;
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
