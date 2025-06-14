package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.items.ItemRepairTool;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;

public abstract class EntityCarDamageBase extends EntityCarBatteryBase {

    private static final EntityDataAccessor<Float> DAMAGE = SynchedEntityData.defineId(EntityCarDamageBase.class, EntityDataSerializers.FLOAT);

    public EntityCarDamageBase(EntityType type, Level worldIn) {
        super(type, worldIn);

    }

    @Override
    public void tick() {
        super.tick();
        if (isInLava()) {
            addDamage(1);
        }

        if (isStarted() || getDamage() > 99F) {
            particles();
        }
    }

    public void particles() {
        if (!level().isClientSide) {
            return;
        }

        if (getDamage() < 50) {
            return;
        }

        int amount;
        int damage = (int) getDamage();

        if (damage < 70) {
            if (random.nextInt(10) != 0) {
                return;
            }
            amount = 1;
        } else if (damage < 80) {
            if (random.nextInt(5) != 0) {
                return;
            }
            amount = 1;
        } else if (damage < 90) {
            amount = 2;
        } else {
            amount = 3;
        }

        for (int i = 0; i < amount; i++) {
            this.level().addParticle(ParticleTypes.LARGE_SMOKE,
                    getX() + (random.nextDouble() - 0.5D) * getCarWidth(),
                    getY() + random.nextDouble() * getCarHeight(),
                    getZ() + (random.nextDouble() - 0.5D) * getCarWidth(),
                    0.0D, 0.0D, 0.0D);
        }

    }

    @Override
    public void onCollision(float speed) {
        super.onCollision(speed);
        float percSpeed = speed / getMaxSpeed();

        if (percSpeed > 0.8F) {
            addDamage(percSpeed * 5);
            playCrashSound();

            if (percSpeed > 0.9F) {
                setStarted(false);
                playStopSound();
            }
        }
    }

    private long lastDamage;

    @Override
    public boolean hurtServer(ServerLevel serverLevel, DamageSource source, float damage) {
        if (this.isInvulnerable()) {
            return false;
        }

        if (level().isClientSide || !isAlive()) {
            return false;
        }

        if (!(source.getDirectEntity() instanceof Player)) {
            return false;
        }
        Player player = (Player) source.getDirectEntity();

        if (player == null) {
            return false;
        }

        if (player.equals(getDriver())) {
            return false;
        }


        ItemStack stack = player.getMainHandItem();

        if (stack.getItem() instanceof ItemRepairTool) {
            long time = player.level().getGameTime();
            if (time - lastDamage < 10L) {
                destroyCar(player, true);
                if (player instanceof ServerPlayer serverPlayer) {
                    stack.hurtAndBreak(50, serverPlayer.level(), serverPlayer, (item) -> {
                    });
                }
            } else {
                lastDamage = time;
            }

            return true;
        }

        return false;
    }

    public void addDamage(float val) {
        setDamage(getDamage() + val);
    }

    @Override
    public boolean canStartCarEngine(Player player) {
        boolean b = true;
        if (getDamage() >= 100F) {
            return false;
        }
        return super.canStartCarEngine(player) && b;
    }

    @Override
    public int getTimeToStart() {
        int value = super.getTimeToStart();

        if (getDamage() >= 95) {
            value += random.nextInt(25) + 50;
        } else if (getDamage() >= 90) {
            value += random.nextInt(15) + 30;
        } else if (getDamage() >= 80) {
            value += random.nextInt(15) + 10;
        } else if (getDamage() >= 50) {
            value += random.nextInt(10) + 5;
        }

        return value;
    }

    public boolean canEngineStayOn() {
        if (isInWater()) {
            addDamage(25);
            return false;
        }
        if (isInLava()) {
            return false;
        }

        if (getDamage() >= 100) {
            return false;
        }

        return super.canEngineStayOn();
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DAMAGE, 0F);
    }

    public void setDamage(float damage) {
        if (damage > 100F) {
            damage = 100F;
        } else if (damage < 0) {
            damage = 0;
        }
        this.entityData.set(DAMAGE, damage);
    }

    public float getDamage() {
        return this.entityData.get(DAMAGE);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putFloat("damage", getDamage());
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        setDamage(valueInput.getFloatOr("damage", 0F));
    }

}
