package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.DamageSourceCar;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.net.*;
import de.maxhenkel.car.sounds.SoundLoopStart;
import de.maxhenkel.corelib.math.MathUtils;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopHigh;
import de.maxhenkel.car.sounds.SoundLoopIdle;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.entity.*;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
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

    private static final DataParameter<Float> SPEED = EntityDataManager.defineId(EntityCarBase.class, DataSerializers.FLOAT);
    private static final DataParameter<Boolean> STARTED = EntityDataManager.defineId(EntityCarBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FORWARD = EntityDataManager.defineId(EntityCarBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> BACKWARD = EntityDataManager.defineId(EntityCarBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> LEFT = EntityDataManager.defineId(EntityCarBase.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> RIGHT = EntityDataManager.defineId(EntityCarBase.class, DataSerializers.BOOLEAN);

    public EntityCarBase(EntityType type, World worldIn) {
        super(type, worldIn);
        this.maxUpStep = 0.5F;
    }

    public abstract float getMaxSpeed();

    public abstract float getMaxReverseSpeed();

    public abstract float getAcceleration();

    public abstract float getMaxRotationSpeed();

    public abstract float getMinRotationSpeed();

    public abstract float getRollResistance();

    public abstract float getRotationModifier();

    public abstract float getPitch();

    @Override
    public void tick() {
        super.tick();

        if (isStarted() && !canEngineStayOn()) {
            setStarted(false);
        }

        updateGravity();
        controlCar();
        checkPush();

        move(MoverType.SELF, getDeltaMovement());

        if (level.isClientSide) {
            updateSounds();
        }

        updateWheelRotation();
    }

    public void centerCar() {
        Direction facing = getDirection();
        switch (facing) {
            case SOUTH:
                yRot = 0F;
                break;
            case NORTH:
                yRot = 180F;
                break;
            case EAST:
                yRot = -90F;
                break;
            case WEST:
                yRot = 90F;
                break;
        }
    }

    @Override
    public boolean canCollideWith(Entity entityIn) {
        if (Main.SERVER_CONFIG.damageEntities.get() && entityIn instanceof LivingEntity && !getPassengers().contains(entityIn)) {
            if (entityIn.getBoundingBox().intersects(getBoundingBox())) {
                float speed = getSpeed();
                if (speed > 0.35F) {
                    float damage = speed * 10;
                    entityIn.hurt(DamageSourceCar.DAMAGE_CAR, damage);
                }

            }
        }
        return super.canCollideWith(entityIn);
    }


    public void checkPush() {
        List<PlayerEntity> list = level.getEntitiesOfClass(PlayerEntity.class, getBoundingBox().expandTowards(0.2, 0, 0.2).expandTowards(-0.2, 0, -0.2));

        for (PlayerEntity player : list) {
            if (!player.hasPassenger(this) && player.isShiftKeyDown()) {
                double motX = calculateMotionX(0.05F, player.yRot);
                double motZ = calculateMotionZ(0.05F, player.yRot);
                move(MoverType.PLAYER, new Vector3d(motX, 0, motZ));
                return;
            }
        }
    }

    public boolean canEngineStayOn() {
        return !isInWater() && !isInLava();
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
        return Minecraft.getInstance().getSoundManager().isActive(sound);
    }

    public void destroyCar(PlayerEntity player, boolean dropParts) {
        remove();
    }

    private void controlCar() {
        if (!isVehicle()) {
            setForward(false);
            setBackward(false);
            setLeft(false);
            setRight(false);
        }

        float modifier = getModifier();

        float maxSp = getMaxSpeed() * modifier;
        float maxBackSp = getMaxReverseSpeed() * modifier;

        float speed = MathUtils.subtractToZero(getSpeed(), getRollResistance());

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

        yRot += deltaRotation;
        float delta = Math.abs(yRot - yRotO);
        while (yRot > 180F) {
            yRot -= 360F;
            yRotO = yRot - delta;
        }
        while (yRot <= -180F) {
            yRot += 360F;
            yRotO = delta + yRot;
        }

        if (horizontalCollision) {
            if (level.isClientSide && !collidedLastTick) {
                onCollision(speed);
                collidedLastTick = true;
            }
        } else {
            setDeltaMovement(calculateMotionX(getSpeed(), yRot), getDeltaMovement().y, calculateMotionZ(getSpeed(), yRot));
            if (level.isClientSide) {
                collidedLastTick = false;
            }
        }
    }

    public float getModifier() {
        BlockPos pos = new BlockPos(getX(), getY() - 0.1D, getZ());
        BlockState state = level.getBlockState(pos);

        if (state.isAir(level, pos) || Main.SERVER_CONFIG.carDriveBlockList.stream().anyMatch(tag -> state.getBlock().is(tag))) {
            return Main.SERVER_CONFIG.carOnroadSpeed.get().floatValue();
        } else {
            return Main.SERVER_CONFIG.carOffroadSpeed.get().floatValue();
        }
    }

    public void onCollision(float speed) {
        if (level.isClientSide) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageCrash(speed, this));
        }
        setSpeed(0.01F);
        setDeltaMovement(0D, getDeltaMovement().y, 0D);
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
        if (isNoGravity()) {
            setDeltaMovement(getDeltaMovement().x, 0D, getDeltaMovement().z);
            return;
        }
        setDeltaMovement(getDeltaMovement().x, getDeltaMovement().y - 0.2D, getDeltaMovement().z);
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
        if (this.level.isClientSide && needsUpdate) {
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
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        if (!canPlayerEnterCar(player)) {
            return ActionResultType.FAIL;
        }
        return super.interact(player, hand);
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
        if (level.isClientSide) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageCarGui(player));
        }
    }

    public boolean isAccelerating() {
        boolean b = (isForward() || isBackward()) && !horizontalCollision;
        return b && isStarted();
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(STARTED, false);
        entityData.define(SPEED, 0F);
        entityData.define(FORWARD, false);
        entityData.define(BACKWARD, false);
        entityData.define(LEFT, false);
        entityData.define(RIGHT, false);
    }

    public void setSpeed(float speed) {
        this.entityData.set(SPEED, speed);
    }

    public float getSpeed() {
        return this.entityData.get(SPEED);
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
        entityData.set(STARTED, started);
    }

    public boolean isStarted() {
        return entityData.get(STARTED);
    }

    public void setForward(boolean forward) {
        entityData.set(FORWARD, forward);
    }

    public boolean isForward() {
        if (getDriver() == null || !canPlayerDriveCar(getDriver())) {
            return false;
        }
        return entityData.get(FORWARD);
    }

    public void setBackward(boolean backward) {
        entityData.set(BACKWARD, backward);
    }

    public boolean isBackward() {
        if (getDriver() == null || !canPlayerDriveCar(getDriver())) {
            return false;
        }
        return entityData.get(BACKWARD);
    }

    public void setLeft(boolean left) {
        entityData.set(LEFT, left);
    }

    public boolean isLeft() {
        return entityData.get(LEFT);
    }

    public void setRight(boolean right) {
        entityData.set(RIGHT, right);
    }

    public boolean isRight() {
        return entityData.get(RIGHT);
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        setStarted(compound.getBoolean("started"), false, false);
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        compound.putBoolean("started", isStarted());
    }

    public void playStopSound() {
        ModSounds.playSound(getStopSound(), level, blockPosition(), null, SoundCategory.MASTER, 1F);
    }

    public void playFailSound() {
        ModSounds.playSound(getFailSound(), level, blockPosition(), null, SoundCategory.MASTER, 1F);
    }

    public void playCrashSound() {
        ModSounds.playSound(getCrashSound(), level, blockPosition(), null, SoundCategory.MASTER, 1F);
    }

    public void playHornSound() {
        ModSounds.playSound(getHornSound(), level, blockPosition(), null, SoundCategory.MASTER, 1F);
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
            idleLoop = new SoundLoopIdle(this, getIdleSound(), SoundCategory.MASTER);
            ModSounds.playSoundLoop(idleLoop, level);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void checkHighLoop() {
        if (!isSoundPlaying(highLoop)) {
            highLoop = new SoundLoopHigh(this, getHighSound(), SoundCategory.MASTER);
            ModSounds.playSoundLoop(highLoop, level);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void checkStartLoop() {
        if (!isSoundPlaying(startLoop)) {
            startLoop = new SoundLoopStart(this, getStartSound(), SoundCategory.MASTER);
            ModSounds.playSoundLoop(startLoop, level);
        }
    }

    public void onHornPressed(PlayerEntity player) {
        if (level.isClientSide) {
            Main.SIMPLE_CHANNEL.sendToServer(new MessageCarHorn(true, player));
        } else {
            if (this instanceof EntityCarBatteryBase) {
                EntityCarBatteryBase car = (EntityCarBatteryBase) this;
                if (car.getBatteryLevel() < 10) {
                    return;
                }
                if (Main.SERVER_CONFIG.useBattery.get()) {
                    car.setBatteryLevel(car.getBatteryLevel() - 10);
                }
            }
            playHornSound();
            if (Main.SERVER_CONFIG.hornFlee.get()) {
                double radius = 15;
                List<MobEntity> list = level.getEntitiesOfClass(MobEntity.class, new AxisAlignedBB(getX() - radius, getY() - radius, getZ() - radius, getX() + radius, getY() + radius, getZ() + radius));
                for (MobEntity ent : list) {
                    fleeEntity(ent);
                }
            }
        }
    }

    public void fleeEntity(MobEntity entity) {
        double fleeDistance = 10;
        Vector3d vecCar = new Vector3d(getX(), getY(), getZ());
        Vector3d vecEntity = new Vector3d(entity.getX(), entity.getY(), entity.getZ());
        Vector3d fleeDir = vecEntity.subtract(vecCar);
        fleeDir = fleeDir.normalize();
        Vector3d fleePos = new Vector3d(vecEntity.x + fleeDir.x * fleeDistance, vecEntity.y + fleeDir.y * fleeDistance, vecEntity.z + fleeDir.z * fleeDistance);

        entity.getNavigation().moveTo(fleePos.x, fleePos.y, fleePos.z, 2.5);
    }

}
