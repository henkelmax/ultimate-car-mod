package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.DamageSourceCar;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.net.*;
import de.maxhenkel.car.sounds.SoundLoopStart;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopHigh;
import de.maxhenkel.car.sounds.SoundLoopIdle;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

public abstract class EntityCarBase extends EntityVehicleBase {

    private float wheelRotation;

    @OnlyIn(Dist.CLIENT)
    private boolean collidedLastTick;

    @OnlyIn(Dist.CLIENT)
    private SoundLoopStart startLoop;
    @OnlyIn(Dist.CLIENT)
    private SoundLoopIdle idleLoop;
    @OnlyIn(Dist.CLIENT)
    private SoundLoopHigh highLoop;

    private static final DataParameter<Float> SPEED = EntityDataManager.<Float>createKey(EntityCarBase.class,
            DataSerializers.FLOAT);
    private static final DataParameter<Boolean> STARTED = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FORWARD = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BACKWARD = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> LEFT = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
            DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RIGHT = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
            DataSerializers.BOOLEAN);

    public EntityCarBase(EntityType type, World worldIn) {
        super(type, worldIn);
        this.stepHeight = 0.5F;
    }

    public abstract float getMaxSpeed();

    public abstract float getMaxReverseSpeed();

    public abstract float getAcceleration();

    public abstract float getMaxRotationSpeed();

    public abstract float getMinRotationSpeed();

    public abstract float getRollResistance();

    public abstract float getRotationModifier();

    @Override
    public void tick() {
        super.tick();

        if (isStarted() && !canEngineStayOn()) {
            setStarted(false);
        }

        this.updateGravity();
        this.controlCar();
        this.checkPush();

        this.move(MoverType.SELF, getMotion());

        if (world.isRemote) {
            updateSounds();
        }

        updateWheelRotation();
    }

    public void centerCar() {
		/*if(world.isRemote){
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageCenterCar(getDriver()));
		}*/
        Direction facing = getHorizontalFacing();
        switch (facing) {
            case SOUTH:
                rotationYaw = 0F;
                break;
            case NORTH:
                rotationYaw = 180F;
                break;
            case EAST:
                rotationYaw = -90F;
                break;
            case WEST:
                rotationYaw = 90F;
                break;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBox(Entity entityIn) {
        if (Config.damageEntities.get()) {
            if (entityIn.getBoundingBox().intersects(getCollisionBoundingBox())) {
                float speed = getSpeed();
                if (speed > 0.35F) {
                    float damage = speed * 10;
                    entityIn.attackEntityFrom(DamageSourceCar.DAMAGE_CAR, damage);
                }

            }
        }
        return super.getCollisionBox(entityIn);
    }


    public void checkPush() {
        if (getCollisionBoundingBox() == null) {
            return;
        }

        List<PlayerEntity> list = world.getEntitiesWithinAABB(PlayerEntity.class, getCollisionBoundingBox().expand(0.2, 0, 0.2).expand(-0.2, 0, -0.2));

        for (int j = 0; j < list.size(); j++) {
            PlayerEntity player = list.get(j);
            if (!player.isPassenger(this) && player.isSneaking()) {
                double motX = calculateMotionX(0.05F, player.rotationYaw);
                double motZ = calculateMotionZ(0.05F, player.rotationYaw);
                move(MoverType.PLAYER, new Vec3d(motX, 0, motZ));
                return;
            }
        }
    }

    public boolean canEngineStayOn() {
        if (isInWater() || isInLava()) {
            return false;
        }

        return true;
    }

    @OnlyIn(Dist.CLIENT)
    private boolean startedLast;

    @OnlyIn(Dist.CLIENT)
    public void updateSounds() {
        if (getSpeed() == 0 && isStarted()) {

            if (!startedLast) {
                checkStartLoop();
            } else if (!isSoundPlaying(startLoop)) {
                if (startLoop != null) {
                    startLoop.setDonePlaying();
                    startLoop = null;
                }

                checkIdleLoop();
            }
        }
        if (getSpeed() != 0 && isStarted()) {
            checkHighLoop();
        }

        startedLast = isStarted();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isSoundPlaying(ISound sound) {
        if (sound == null) {
            return false;
        }
        return Minecraft.getInstance().getSoundHandler().func_215294_c(sound);
    }

    public void destroyCar(PlayerEntity player, boolean dropParts) {
        remove();
    }

    private void controlCar() {

        if (!isBeingRidden()) {
            setForward(false);
            setBackward(false);
            setLeft(false);
            setRight(false);
        }

        float modifier = 1F;

        if (Config.carGroundSpeed.get()) {
            modifier = getModifier();
        }

        float maxSp = getMaxSpeed() * modifier;
        float maxBackSp = getMaxReverseSpeed() * modifier;

        float speed = MathTools.subtractToZero(getSpeed(), getRollResistance());

        if (isForward()) {
            if (speed <= maxSp) {
                speed = Math.min(speed + getAcceleration(), maxSp);
            }
        }

        if (isBackward()) {
            if (speed >= -maxBackSp) {
                speed = Math.max(speed - getAcceleration(), -maxBackSp);
            }
        }

        setSpeed(speed);


        float rotationSpeed = 0;
        if (Math.abs(speed) > 0.02F) {
            rotationSpeed = MathHelper.abs(getRotationModifier() / (float) Math.pow(speed, 2));

            rotationSpeed = MathHelper.clamp(rotationSpeed, getMinRotationSpeed(), getMaxRotationSpeed());
        }

        deltaRotation = 0;

        if (speed < 0) {
            rotationSpeed = -rotationSpeed;
        }

        if (isLeft()) {
            deltaRotation -= rotationSpeed;
        }
        if (isRight()) {
            deltaRotation += rotationSpeed;
        }

        rotationYaw += deltaRotation;
        float delta = Math.abs(rotationYaw - prevRotationYaw);
        while (rotationYaw > 180F) {
            rotationYaw -= 360F;
            prevRotationYaw = rotationYaw - delta;
        }
        while (rotationYaw <= -180F) {
            rotationYaw += 360F;
            prevRotationYaw = delta + rotationYaw;
        }
        /*if (rotationYaw > 180) {
            rotationYaw %= 360F;
            //prevRotationYaw = rotationYaw;
        } else if (rotationYaw <= -180) {
            rotationYaw %= 360;
            //prevRotationYaw = rotationYaw;
        }*/

        if (collidedHorizontally) {
            if (world.isRemote && !collidedLastTick) {
                onCollision(speed);
                collidedLastTick = true;
            }
        } else {
            setMotion(calculateMotionX(getSpeed(), rotationYaw), getMotion().y, calculateMotionZ(getSpeed(), rotationYaw));
            if (world.isRemote) {
                collidedLastTick = false;
            }
        }
    }

    public float getModifier() {
        BlockPos pos = getPosition().down();
        BlockState state = world.getBlockState(pos);

        if (state.isAir()) {
            return 1;
        }

        //TODO add grount types to config
        if (true/*Config.isDrivable(b)*/) {
            return 1F;
        } else {
            return 0.5F;
        }
    }

    public void onCollision(float speed) {
        if (world.isRemote) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageCrash(speed, this));
        }
        setSpeed(0.01F);
        setMotion(0D, getMotion().y, 0D);
    }

    public boolean canPlayerDriveCar(PlayerEntity player) {
        if (player.equals(getDriver()) && isStarted()) {
            return true;
        } else if (isInWater() || isInLava()) {
            return false;
        } else {
            return false;
        }
    }

    private void updateGravity() {
        if (hasNoGravity()) {
            setMotion(getMotion().x, 0D, getMotion().z);
            return;
        }
        setMotion(getMotion().x, getMotion().y - 0.2D, getMotion().z);
    }

    public void updateControls(boolean forward, boolean backward, boolean left, boolean right, PlayerEntity player) {
        boolean needsUpdate = false;

        if (isForward() != forward) {
            setForward(forward);
            needsUpdate = true;
        }

        if (isBackward() != backward) {
            setBackward(backward);
            needsUpdate = true;
        }

        if (isLeft() != left) {
            setLeft(left);
            needsUpdate = true;
        }

        if (isRight() != right) {
            setRight(right);
            needsUpdate = true;
        }
        if (this.world.isRemote && needsUpdate) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageControlCar(forward, backward, left, right, player));
        }
    }

    public void startCarEngine() {
        PlayerEntity player = getDriver();
        if (player != null && canStartCarEngine(player)) {
            setStarted(true);
        }
    }

    public boolean canStartCarEngine(PlayerEntity player) {
        if (isInWater() || isInLava()) {
            return false;
        }

        return true;
    }

    public abstract double getPlayerYOffset();

    public boolean canPlayerEnterCar(PlayerEntity player) {
        return true;
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        if (!canPlayerEnterCar(player)) {
            return false;
        }
        return super.processInitialInteract(player, hand);
    }

    public float getKilometerPerHour() {
        return (getSpeed() * 20 * 60 * 60) / 1000;
    }

    public float getWheelRotationAmount() {
        return 120F * getSpeed();
    }

    public void updateWheelRotation() {
        wheelRotation += getWheelRotationAmount();
    }

    public float getWheelRotation(float partialTicks) {
        return wheelRotation + getWheelRotationAmount() * partialTicks;
    }

    public void openCarGUI(PlayerEntity player) {
        if (world.isRemote) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageCarGui(player));
        }
    }

    public boolean isAccelerating() {
        boolean b = (isForward() || isBackward()) && !collidedHorizontally;
        return b && isStarted();
    }

    @Override
    protected void registerData() {
        this.dataManager.register(STARTED, Boolean.valueOf(false));
        this.dataManager.register(SPEED, Float.valueOf(0));
        this.dataManager.register(FORWARD, Boolean.valueOf(false));
        this.dataManager.register(BACKWARD, Boolean.valueOf(false));
        this.dataManager.register(LEFT, Boolean.valueOf(false));
        this.dataManager.register(RIGHT, Boolean.valueOf(false));
    }

    public void setSpeed(float speed) {
        this.dataManager.set(SPEED, speed);
    }

    public float getSpeed() {
        return this.dataManager.get(SPEED);
    }

    public void setStarted(boolean started) {
        setStarted(started, true, false);
    }

    public void setStarted(boolean started, boolean playStopSound, boolean playFailSound) {
        if (!started && playStopSound) {
            playStopSound();
        } else if (!started && playFailSound) {
            playFailSound();
        }
        if (started) {
            setForward(false);
            setBackward(false);
            setLeft(false);
            setRight(false);
        }
        this.dataManager.set(STARTED, Boolean.valueOf(started));
    }

    public boolean isStarted() {
        return this.dataManager.get(STARTED);
    }

    public void setForward(boolean forward) {
        this.dataManager.set(FORWARD, Boolean.valueOf(forward));
    }

    public boolean isForward() {
        if (getDriver() == null || !canPlayerDriveCar(getDriver())) {
            return false;
        }
        return this.dataManager.get(FORWARD);
    }

    public void setBackward(boolean backward) {
        this.dataManager.set(BACKWARD, Boolean.valueOf(backward));
    }

    public boolean isBackward() {
        if (getDriver() == null || !canPlayerDriveCar(getDriver())) {
            return false;
        }
        return this.dataManager.get(BACKWARD);
    }

    public void setLeft(boolean left) {
        this.dataManager.set(LEFT, Boolean.valueOf(left));
    }

    public boolean isLeft() {
        return this.dataManager.get(LEFT);
    }

    public void setRight(boolean right) {
        this.dataManager.set(RIGHT, Boolean.valueOf(right));
    }

    public boolean isRight() {
        return this.dataManager.get(RIGHT);
    }

    public abstract ITextComponent getCarName();

    @Override
    public void readAdditional(CompoundNBT compound) {
        setStarted(compound.getBoolean("started"), false, false);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        compound.putBoolean("started", isStarted());
    }

    protected float getVolume() {
        return Config.carVolume.get().floatValue();
    }

    public void playStopSound() {
        ModSounds.playSound(getStopSound(), world, getPosition(), null, SoundCategory.NEUTRAL, getVolume());
    }

    public void playFailSound() {
        ModSounds.playSound(getFailSound(), world, getPosition(), null, SoundCategory.NEUTRAL, getVolume());
    }

    public void playCrashSound() {
        ModSounds.playSound(getCrashSound(), world, getPosition(), null, SoundCategory.NEUTRAL, getVolume());
    }

    public void playHornSound() {
        ModSounds.playSound(getHornSound(), world, getPosition(), null, SoundCategory.NEUTRAL, getVolume());
    }

    public abstract SoundEvent getStopSound();

    public abstract SoundEvent getFailSound();

    public abstract SoundEvent getCrashSound();

    public abstract SoundEvent getStartSound();

    public abstract SoundEvent getStartingSound();

    public abstract SoundEvent getIdleSound();

    public abstract SoundEvent getHighSound();

    public abstract SoundEvent getHornSound();

    @OnlyIn(Dist.CLIENT)
    public void checkIdleLoop() {
        if (!isSoundPlaying(idleLoop)) {
            idleLoop = new SoundLoopIdle(world, this, getIdleSound(), SoundCategory.NEUTRAL);
            ModSounds.playSoundLoop(idleLoop, world);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void checkHighLoop() {
        if (!isSoundPlaying(highLoop)) {
            highLoop = new SoundLoopHigh(world, this, getHighSound(), SoundCategory.NEUTRAL);
            ModSounds.playSoundLoop(highLoop, world);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void checkStartLoop() {
        if (!isSoundPlaying(startLoop)) {
            startLoop = new SoundLoopStart(world, this, getStartSound(), SoundCategory.NEUTRAL);
            ModSounds.playSoundLoop(startLoop, world);
        }
    }

    public void onHornPressed(PlayerEntity player) {
        if (world.isRemote) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageCarHorn(true, player));
        } else {
            if (this instanceof EntityCarBatteryBase) {
                EntityCarBatteryBase car = (EntityCarBatteryBase) this;
                if (car.getBatteryLevel() < 10) {
                    return;
                }
                if (Config.useBattery.get()) {
                    car.setBatteryLevel(car.getBatteryLevel() - 10);
                }
            }
            playHornSound();
            if (Config.hornFlee.get()) {
                double radius = 15;
                List<LivingEntity> list = world.getEntitiesWithinAABB(LivingEntity.class, new AxisAlignedBB(posX - radius, posY - radius, posZ - radius, posX + radius, posY + radius, posZ + radius));
                for (LivingEntity ent : list) {
                    fleeEntity(ent);
                }
            }
        }
    }

    public void fleeEntity(LivingEntity entity) {
        double fleeDistance = 10;
        Vec3d vecCar = new Vec3d(posX, posY, posZ);
        Vec3d vecEntity = new Vec3d(entity.posX, entity.posY, entity.posZ);
        Vec3d fleeDir = vecEntity.subtract(vecCar);
        fleeDir = fleeDir.normalize();
        Vec3d fleePos = new Vec3d(vecEntity.x + fleeDir.x * fleeDistance, vecEntity.y + fleeDir.y * fleeDistance, vecEntity.z + fleeDir.z * fleeDistance);

        //TODO implement fleeing
        //entity.getNavigator().tryMoveToXYZ(fleePos.x, fleePos.y, fleePos.z, 2.5);
    }

}
