package de.maxhenkel.car.entity.car.base;

import java.util.UUID;

import de.maxhenkel.car.Config;
import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public abstract class EntityCarLockBase extends EntityCarInventoryBase {

	private static final DataParameter<Boolean> LOCKED = EntityDataManager
			.<Boolean>createKey(EntityCarInventoryBase.class, DataSerializers.BOOLEAN);

	public EntityCarLockBase(World worldIn) {
		super(worldIn);
	}

	@Override
	public boolean canPlayerEnterCar(EntityPlayer player) {
		if (isLocked()) {
			return false;
		}
		return super.canPlayerEnterCar(player);
	}

	@Override
	public void destroyCar(EntityPlayer player, boolean dropParts) {
		if (isLocked()) {
			player.sendStatusMessage(new TextComponentTranslation("message.car_locked"), true);
			return;
		}

		super.destroyCar(player, dropParts);
	}

	@Override
	public boolean canPlayerAccessInventoryExternal(EntityPlayer player) {
		if (isLocked()) {
			return false;
		}

		return super.canPlayerAccessInventoryExternal(player);
	}

	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(LOCKED, Boolean.valueOf(false));
	}

	public void setLocked(boolean locked, boolean playsound) {
		if (locked && playsound) {
			playLockSound();
		} else if (!locked && playsound) {
			playUnLockSound();
		}

		this.dataManager.set(LOCKED, Boolean.valueOf(locked));
	}

	public boolean isLocked() {
		return this.dataManager.get(LOCKED);
	}

	public void playLockSound() {
		ModSounds.playSound(getLockSound(), world, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
	}

	public void playUnLockSound() {
		ModSounds.playSound(getUnLockSound(), world, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
	}

	public SoundEvent getLockSound() {
		return ModSounds.car_lock;
	}

	public SoundEvent getUnLockSound() {
		return ModSounds.car_unlock;
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("locked", isLocked());
		super.writeEntityToNBT(compound);
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		setLocked(compound.getBoolean("locked"), false);
		super.readEntityFromNBT(compound);
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		ItemStack stack=player.getActiveItemStack();//TODO test
		if (player.isSneaking() && player.capabilities.isCreativeMode && !ItemTools.isStackEmpty(stack)
				&& stack.getItem().equals(ModItems.KEY)) {
			UUID uuid = ItemKey.getCar(stack);
			if (uuid == null) {
				ItemKey.setCar(stack, getUniqueID());
				return true;
			}
		}
		return super.processInitialInteract(player, hand);
	}

}
