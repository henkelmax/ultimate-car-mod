package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public abstract class EntityCarLockBase extends EntityCarInventoryBase {

    private static final EntityDataAccessor<Boolean> LOCKED = SynchedEntityData.defineId(EntityCarInventoryBase.class, EntityDataSerializers.BOOLEAN);

    public EntityCarLockBase(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public boolean canPlayerEnterCar(Player player) {
        if (isLocked()) {
            player.displayClientMessage(Component.translatable("message.car_locked"), true);
            return false;
        }
        return super.canPlayerEnterCar(player);
    }

    @Override
    public void destroyCar(Player player, boolean dropParts) {
        if (isLocked() && !player.hasPermissions(2)) {
            player.displayClientMessage(Component.translatable("message.car_locked"), true);
            return;
        }

        super.destroyCar(player, dropParts);
    }

    @Override
    public boolean canPlayerAccessInventoryExternal(Player player) {
        if (isLocked()) {
            return false;
        }

        return super.canPlayerAccessInventoryExternal(player);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(LOCKED, Boolean.FALSE);
    }

    public void setLocked(boolean locked, boolean playsound) {
        if (locked && playsound) {
            playLockSound();
        } else if (!locked && playsound) {
            playUnLockSound();
        }

        this.entityData.set(LOCKED, locked);
    }

    public boolean isLocked() {
        return this.entityData.get(LOCKED);
    }

    public void playLockSound() {
        ModSounds.playSound(getLockSound(), level, blockPosition(), null, SoundSource.MASTER, 1F);
    }

    public void playUnLockSound() {
        ModSounds.playSound(getUnLockSound(), level, blockPosition(), null, SoundSource.MASTER, 1F);
    }

    public SoundEvent getLockSound() {
        return ModSounds.CAR_LOCK.get();
    }

    public SoundEvent getUnLockSound() {
        return ModSounds.CAR_UNLOCK.get();
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("locked", isLocked());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        setLocked(compound.getBoolean("locked"), false);
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!isLocked() && player.isShiftKeyDown() && player.getAbilities().instabuild && !stack.isEmpty() && stack.getItem().equals(ModItems.KEY.get())) {
            UUID uuid = ItemKey.getCar(stack);
            if (uuid == null) {
                ItemKey.setCar(stack, getUUID());
                return InteractionResult.CONSUME;
            }
        }
        return super.interact(player, hand);
    }

}
