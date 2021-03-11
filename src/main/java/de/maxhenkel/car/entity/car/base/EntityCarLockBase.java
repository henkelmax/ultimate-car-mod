package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.UUID;

public abstract class EntityCarLockBase extends EntityCarInventoryBase {

    private static final DataParameter<Boolean> LOCKED = EntityDataManager.defineId(EntityCarInventoryBase.class, DataSerializers.BOOLEAN);

    public EntityCarLockBase(EntityType type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    public boolean canPlayerEnterCar(PlayerEntity player) {
        if (isLocked()) {
            player.displayClientMessage(new TranslationTextComponent("message.car_locked"), true);
            return false;
        }
        return super.canPlayerEnterCar(player);
    }

    @Override
    public void destroyCar(PlayerEntity player, boolean dropParts) {
        if (isLocked() && !player.hasPermissions(2)) {
            player.displayClientMessage(new TranslationTextComponent("message.car_locked"), true);
            return;
        }

        super.destroyCar(player, dropParts);
    }

    @Override
    public boolean canPlayerAccessInventoryExternal(PlayerEntity player) {
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
        ModSounds.playSound(getLockSound(), level, blockPosition(), null, SoundCategory.MASTER, 1F);
    }

    public void playUnLockSound() {
        ModSounds.playSound(getUnLockSound(), level, blockPosition(), null, SoundCategory.MASTER, 1F);
    }

    public SoundEvent getLockSound() {
        return ModSounds.CAR_LOCK;
    }

    public SoundEvent getUnLockSound() {
        return ModSounds.CAR_UNLOCK;
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);
        compound.putBoolean("locked", isLocked());
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        setLocked(compound.getBoolean("locked"), false);
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!isLocked() && player.isShiftKeyDown() && player.abilities.instabuild && !stack.isEmpty() && stack.getItem().equals(ModItems.KEY)) {
            UUID uuid = ItemKey.getCar(stack);
            if (uuid == null) {
                ItemKey.setCar(stack, getUUID());
                return ActionResultType.CONSUME;
            }
        }
        return super.interact(player, hand);
    }

}
