package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.DamageSourceCar;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.net.MessageCarGui;
import de.maxhenkel.car.net.MessageCarHorn;
import de.maxhenkel.car.net.MessageControlCar;
import de.maxhenkel.car.net.MessageCrash;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopHigh;
import de.maxhenkel.car.sounds.SoundLoopIdle;
import de.maxhenkel.car.sounds.SoundLoopStart;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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

    private static final EntityDataAccessor<Float> SPEED = SynchedEntityData.defineId(EntityCarBase.class, EntityDataSerializers.FLOAT);
    private static final EntityDataAccessor<Boolean> STARTED = SynchedEntityData.defineId(EntityCarBase.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FORWARD = SynchedEntityData.defineId(EntityCarBase.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> BACKWARD = SynchedEntityData.defineId(EntityCarBase.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> LEFT = SynchedEntityData.defineId(EntityCarBase.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> RIGHT = SynchedEntityData.defineId(EntityCarBase.class, EntityDataSerializers.BOOLEAN);

    public EntityCarBase(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public float maxUpStep() {
        return 0.5F;
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

        Runnable task;
        while ((task = tasks.poll()) != null) {
            task.run();
        }

        if (isStarted() && !canEngineStayOn()) {
            setStarted(false);
        }

        updateGravity();
        controlCar();
        checkPush();

        move(MoverType.SELF, getDeltaMovement());

        if (level().isClientSide) {
            updateSounds();
        }

        updateWheelRotation();
    }

    public void centerCar() {
        Direction facing = getDirection();
        switch (facing) {
            case SOUTH:
                setYRot(0F);
                break;
            case NORTH:
                setYRot(180F);
                break;
            case EAST:
                setYRot(-90F);
                break;
            case WEST:
                setYRot(90F);
                break;
        }
    }

    private final BlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();

    @Override
    public boolean canCollideWith(Entity entityIn) {
        if (!level().isClientSide && Main.SERVER_CONFIG.damageEntities.get() && entityIn instanceof LivingEntity && !getPassengers().contains(entityIn)) {
            if (entityIn.getBoundingBox().intersects(getBoundingBox())) {
                float speed = getSpeed();
                if (speed > 0.35F) {
                    float damage = speed * 10;
                    tasks.add(() -> {
                        ServerLevel serverLevel = (ServerLevel) level();
                        Optional<Holder.Reference<DamageType>> holder = serverLevel.registryAccess().holder(DamageSourceCar.DAMAGE_CAR_TYPE);
                        holder.ifPresent(damageTypeReference -> entityIn.hurt(new DamageSource(damageTypeReference, this), damage));
                    });
                }
            }
        }
        return super.canCollideWith(entityIn);
    }


    public void checkPush() {
        List<Player> list = level().getEntitiesOfClass(Player.class, getBoundingBox().expandTowards(0.2, 0, 0.2).expandTowards(-0.2, 0, -0.2));

        for (Player player : list) {
            if (!player.hasPassenger(this) && player.isShiftKeyDown()) {
                double motX = calculateMotionX(0.05F, player.getYRot());
                double motZ = calculateMotionZ(0.05F, player.getYRot());
                move(MoverType.PLAYER, new Vec3(motX, 0, motZ));
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
    public boolean isSoundPlaying(SoundInstance sound) {
        if (sound == null) {
            return false;
        }
        return Minecraft.getInstance().getSoundManager().isActive(sound);
    }

    public void destroyCar(Player player, boolean dropParts) {
        if (player instanceof ServerPlayer serverPlayer) {
            kill(serverPlayer.serverLevel());
        }
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
            rotationSpeed = Mth.abs(getRotationModifier() / (float) Math.pow(speed, 2));

            rotationSpeed = Mth.clamp(rotationSpeed, getMinRotationSpeed(), getMaxRotationSpeed());
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

        setYRot(getYRot() + deltaRotation);
        float delta = Math.abs(getYRot() - yRotO);
        while (getYRot() > 180F) {
            setYRot(getYRot() - 360F);
            yRotO = getYRot() - delta;
        }
        while (getYRot() <= -180F) {
            setYRot(getYRot() + 360F);
            yRotO = delta + getYRot();
        }

        if (horizontalCollision) {
            if (level().isClientSide && !collidedLastTick) {
                onCollision(speed);
                collidedLastTick = true;
            }
        } else {
            setDeltaMovement(calculateMotionX(getSpeed(), getYRot()), getDeltaMovement().y, calculateMotionZ(getSpeed(), getYRot()));
            if (level().isClientSide) {
                collidedLastTick = false;
            }
        }
    }

    public float getModifier() {
        BlockPos pos = new BlockPos((int) getX(), (int) (getY() - 0.1D), (int) getZ());
        BlockState state = level().getBlockState(pos);

        if (state.isAir() || Main.SERVER_CONFIG.carDriveBlockList.stream().anyMatch(tag -> tag.contains(state.getBlock()))) {
            return Main.SERVER_CONFIG.carOnroadSpeed.get().floatValue();
        } else {
            return Main.SERVER_CONFIG.carOffroadSpeed.get().floatValue();
        }
    }

    public void onCollision(float speed) {
        if (level().isClientSide) {
            PacketDistributor.sendToServer(new MessageCrash(speed, this));
        }
        setSpeed(0.01F);
        setDeltaMovement(0D, getDeltaMovement().y, 0D);
    }

    public boolean canPlayerDriveCar(Player player) {
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

    public void updateControls(boolean forward, boolean backward, boolean left, boolean right, Player player) {
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
        if (level().isClientSide && needsUpdate) {
            PacketDistributor.sendToServer(new MessageControlCar(forward, backward, left, right, player));
        }
    }

    public void startCarEngine() {
        Player player = getDriver();
        if (player != null && canStartCarEngine(player)) {
            setStarted(true);
        }
    }

    public boolean canStartCarEngine(Player player) {
        if (isInWater() || isInLava()) {
            return false;
        }

        return true;
    }

    public abstract double getPlayerYOffset();

    public boolean canPlayerEnterCar(Player player) {
        return true;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (!canPlayerEnterCar(player)) {
            return InteractionResult.FAIL;
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

    public void openCarGUI(Player player) {
        if (level().isClientSide) {
            PacketDistributor.sendToServer(new MessageCarGui(player));
        }
    }

    public boolean isAccelerating() {
        boolean b = (isForward() || isBackward()) && !horizontalCollision;
        return b && isStarted();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(STARTED, false);
        builder.define(SPEED, 0F);
        builder.define(FORWARD, false);
        builder.define(BACKWARD, false);
        builder.define(LEFT, false);
        builder.define(RIGHT, false);
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
    public void readAdditionalSaveData(CompoundTag compound) {
        setStarted(compound.getBoolean("started"), false, false);
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        compound.putBoolean("started", isStarted());
    }

    public void playStopSound() {
        ModSounds.playSound(getStopSound(), level(), blockPosition(), null, SoundSource.MASTER, 1F);
    }

    public void playFailSound() {
        ModSounds.playSound(getFailSound(), level(), blockPosition(), null, SoundSource.MASTER, 1F);
    }

    public void playCrashSound() {
        ModSounds.playSound(getCrashSound(), level(), blockPosition(), null, SoundSource.MASTER, 1F);
    }

    public void playHornSound() {
        ModSounds.playSound(getHornSound(), level(), blockPosition(), null, SoundSource.MASTER, 1F);
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
            idleLoop = new SoundLoopIdle(this, getIdleSound(), SoundSource.MASTER);
            ModSounds.playSoundLoop(idleLoop, level());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void checkHighLoop() {
        if (!isSoundPlaying(highLoop)) {
            highLoop = new SoundLoopHigh(this, getHighSound(), SoundSource.MASTER);
            ModSounds.playSoundLoop(highLoop, level());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void checkStartLoop() {
        if (!isSoundPlaying(startLoop)) {
            startLoop = new SoundLoopStart(this, getStartSound(), SoundSource.MASTER);
            ModSounds.playSoundLoop(startLoop, level());
        }
    }

    public void onHornPressed(Player player) {
        if (level().isClientSide) {
            PacketDistributor.sendToServer(new MessageCarHorn(true, player));
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
                List<Monster> list = level().getEntitiesOfClass(Monster.class, new AABB(getX() - radius, getY() - radius, getZ() - radius, getX() + radius, getY() + radius, getZ() + radius));
                for (Monster ent : list) {
                    fleeEntity(ent);
                }
            }
        }
    }

    public void fleeEntity(Monster entity) {
        double fleeDistance = 10;
        Vec3 vecCar = new Vec3(getX(), getY(), getZ());
        Vec3 vecEntity = new Vec3(entity.getX(), entity.getY(), entity.getZ());
        Vec3 fleeDir = vecEntity.subtract(vecCar);
        fleeDir = fleeDir.normalize();
        entity.getNavigation().moveTo(vecEntity.x + fleeDir.x * fleeDistance, vecEntity.y + fleeDir.y * fleeDistance, vecEntity.z + fleeDir.z * fleeDistance, 2.5);
    }

}
