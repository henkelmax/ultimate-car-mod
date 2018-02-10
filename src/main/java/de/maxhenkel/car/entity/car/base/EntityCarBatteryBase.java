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
import net.minecraft.world.biome.Biome;
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
    // /summon car:car_wood ~ ~ ~ {"fuel": 1000, "battery":10000}
    @Override
    public void onUpdate() {
        super.onUpdate();

        if (isStarting()) {
            setBatteryLevel(getBatteryLevel() - getBatteryUsage());
            System.out.println(getBatteryLevel());
            setStartingTime(getStartingTime() + 1);
        } else {
            setStartingTime(0);
        }
        if (getStartingTime() > getTimeToStart()) {
            startCarEngine();
            carStarted = true;
            setStartingTime(0);
        }

        if(isStarted()){
            float speedPerc=getSpeed()/getMaxSpeed();

            int chargingRate= (int) (speedPerc*10F);
            if(chargingRate<5){
                chargingRate=1;
            }

            if(ticksExisted%20==0){
                System.out.println("Battery: " +getBatteryLevel() +" chargingRate: " +chargingRate +" temp: " +getTemperature() +" baseUsage: " +getBatteryUsage());
                setBatteryLevel(getBatteryLevel()+chargingRate);
            }
        }
    }

    public int getTimeToStart(){
        int baseTime=rand.nextInt(10)+10;

        float temp=getTemperature();

        if(temp<0F){
            baseTime+=30;
        }else if(temp<-0.3F){
            baseTime+=40;
        }

        float batteryPerc=getBatteryPercentage();

        if(batteryPerc<0.5){
            baseTime+=25;
        }else if(batteryPerc<0.75){
            baseTime+=15;
        }

        return baseTime;
    }

    public float getTemperature(){
        return world.getBiome(getPosition()).getTemperature(getPosition())-0.3F;
    }

    public int getBatteryUsage(){
        float temp=getTemperature();
        int baseUsage=4;
        if(temp<-0.3F){
            baseUsage+=2;
        }else if(temp<0F){
            baseUsage+=1;
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

        int startLevel = getMaxBatteryLevel() / 3;//TODO change maybe

        if (batteryLevel > startLevel) {
            return 1F;
        }

        int levelUnder = startLevel - batteryLevel;

        float perc = (float) levelUnder / (float) startLevel;

        float pitch = 1 - (perc / 2);
        return pitch;
    }

    public float getBatteryPercentage() {
        return ((float)getBatteryLevel()) / ((float)getMaxBatteryLevel());
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
