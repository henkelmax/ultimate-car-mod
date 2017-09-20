package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntitySplitTank extends TileEntityBase implements ITickable, IFluidHandler, IInventory {

	private int currentMix;
	public int maxMix;
	public int mixUsage;

	private int currentBioDiesel;
	public int maxBioDiesel;
	public int bioDieselGeneration;

	private int currentGlycerin;
	public int maxGlycerin;
	public int glycerinGeneration;

	public int generatingTime;
	private int timeToGenerate;

	protected InventoryBasic inventory;

	public TileEntitySplitTank() {
		this.inventory = new InventoryBasic("", false, 0);
		this.currentMix = 0;
		this.maxMix = Config.splitTankFluidStorage;

		this.currentBioDiesel = 0;
		this.maxBioDiesel = Config.splitTankFluidStorage;

		this.currentGlycerin = 0;
		this.maxGlycerin = Config.splitTankFluidStorage;

		this.generatingTime = Config.splitTankGeneratingTime;
		this.timeToGenerate = 0;

		this.mixUsage = Config.splitTankMixUsage;
		this.glycerinGeneration = Config.splitTankGlycerinGeneration;
		this.bioDieselGeneration = Config.splitTankBioDieselGeneration;
	}

	@Override
	public void update() {
		if (worldObj.isRemote) {
			return;
		}

		if (timeToGenerate > 0) {
			timeToGenerate--;

			if (timeToGenerate == 0) {

				if (currentMix - mixUsage >= 0) {
					currentMix -= mixUsage;
					if (currentBioDiesel + bioDieselGeneration <= maxBioDiesel) {
						currentBioDiesel += bioDieselGeneration;
					}
					if (currentGlycerin + glycerinGeneration <= maxGlycerin) {
						currentGlycerin += glycerinGeneration;
					}
				}
				// synchronize();
			}
		} else {
			if (currentMix >= mixUsage) {
				if (currentBioDiesel + bioDieselGeneration <= maxBioDiesel) {
					if (currentGlycerin + glycerinGeneration <= maxGlycerin) {
						timeToGenerate = generatingTime;
					}

				}

			}
		}

		if (worldObj.getTotalWorldTime() % 200 == 0) {
			synchronize();
		}

		markDirty();
	}

	public float getBioDieselPerc() {
		return ((float) currentBioDiesel) / ((float) maxBioDiesel);
	}

	public float getGlycerinPerc() {
		return ((float) currentGlycerin) / ((float) maxGlycerin);
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return currentMix;
		case 1:
			return currentBioDiesel;
		case 2:
			return currentGlycerin;
		case 3:
			return timeToGenerate;
		}

		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			currentMix = value;
			break;
		case 1:
			currentBioDiesel = value;
			break;
		case 2:
			currentGlycerin = value;
			break;
		case 3:
			timeToGenerate = value;
			break;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("mix", currentMix);
		compound.setInteger("bio_diesel", currentBioDiesel);
		compound.setInteger("glycerin", currentGlycerin);
		compound.setInteger("time", timeToGenerate);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		currentMix = compound.getInteger("mix");
		currentBioDiesel = compound.getInteger("bio_diesel");
		currentGlycerin = compound.getInteger("glycerin");
		timeToGenerate = compound.getInteger("timeToGenerate");
		super.readFromNBT(compound);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(this.pos, 1, getUpdateTag());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return this.writeToNBT(new NBTTagCompound());
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("tile.split_tank.name");
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { new IFluidTankProperties() {

			@Override
			public FluidStack getContents() {
				return new FluidStack(ModFluids.CANOLA_METHANOL_MIX, currentMix);
			}

			@Override
			public int getCapacity() {
				return maxMix;
			}

			@Override
			public boolean canFillFluidType(FluidStack fluidStack) {
				return fluidStack.getFluid().equals(ModFluids.CANOLA_METHANOL_MIX);
			}

			@Override
			public boolean canFill() {
				return true;
			}

			@Override
			public boolean canDrainFluidType(FluidStack fluidStack) {
				return false;
			}

			@Override
			public boolean canDrain() {
				return false;
			}
		}, new IFluidTankProperties() {

			@Override
			public FluidStack getContents() {
				return new FluidStack(ModFluids.BIO_DIESEL, currentBioDiesel);
			}

			@Override
			public int getCapacity() {
				return maxBioDiesel;
			}

			@Override
			public boolean canFillFluidType(FluidStack fluidStack) {
				return false;
			}

			@Override
			public boolean canFill() {
				return false;
			}

			@Override
			public boolean canDrainFluidType(FluidStack fluidStack) {
				return fluidStack.getFluid().equals(ModFluids.BIO_DIESEL);
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		}, new IFluidTankProperties() {

			@Override
			public FluidStack getContents() {
				return new FluidStack(ModFluids.GLYCERIN, currentGlycerin);
			}

			@Override
			public int getCapacity() {
				return maxGlycerin;
			}

			@Override
			public boolean canFillFluidType(FluidStack fluidStack) {
				return false;
			}

			@Override
			public boolean canFill() {
				return false;
			}

			@Override
			public boolean canDrainFluidType(FluidStack fluidStack) {
				return fluidStack.getFluid().equals(ModFluids.GLYCERIN);
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		} };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource.getFluid().equals(ModFluids.CANOLA_METHANOL_MIX)) {
			int amount = Math.min(maxMix - currentMix, resource.amount);
			if (doFill) {
				currentMix += amount;
				markDirty();
			}
			return amount;
		}

		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		if (resource.getFluid().equals(ModFluids.GLYCERIN)) {
			int amount = Math.min(resource.amount, currentGlycerin);

			if (doDrain) {
				currentGlycerin -= amount;
				markDirty();
			}

			return new FluidStack(ModFluids.GLYCERIN, amount);
		} else if (resource.getFluid().equals(ModFluids.BIO_DIESEL)) {
			int amount = Math.min(resource.amount, currentBioDiesel);

			if (doDrain) {
				currentBioDiesel -= amount;
				markDirty();
			}
			return new FluidStack(ModFluids.BIO_DIESEL, amount);
		}

		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		if (currentGlycerin > currentBioDiesel) {
			int amount = Math.min(maxDrain, currentGlycerin);

			if (doDrain) {
				currentGlycerin -= amount;
				markDirty();
			}

			return new FluidStack(ModFluids.GLYCERIN, amount);
		} else {
			int amount = Math.min(maxDrain, currentBioDiesel);

			if (doDrain) {
				currentBioDiesel -= amount;
				markDirty();
			}
			return new FluidStack(ModFluids.BIO_DIESEL, amount);
		}
	}

	@Override
	public String getName() {
		return inventory.getName();
	}

	@Override
	public boolean hasCustomName() {
		return inventory.hasCustomName();
	}

	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return inventory.decrStackSize(index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return inventory.removeStackFromSlot(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inventory.setInventorySlotContents(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return inventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory(EntityPlayer player) {
		inventory.openInventory(player);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		inventory.closeInventory(player);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return inventory.isItemValidForSlot(index, stack);
	}

	@Override
	public void clear() {
		inventory.clear();
	}

}
