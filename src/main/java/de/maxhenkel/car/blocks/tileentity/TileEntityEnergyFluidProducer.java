package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.blocks.BlockGui;
import net.minecraft.block.BlockState;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public abstract class TileEntityEnergyFluidProducer extends TileEntityBase implements IEnergyStorage, ISidedInventory, ITickable, IFluidHandler{

	protected Inventory inventory;

	protected int maxStorage;
	protected int storedEnergy;
	protected int energyUsage;

	protected int timeToGenerate;
	protected int generatingTime;

	protected int maxMillibuckets;
	protected int millibucketsPerUse;
	protected int currentMillibuckets;

	public TileEntityEnergyFluidProducer(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
		this.inventory = new Inventory(2);
		this.maxStorage = 10000;
		this.storedEnergy = 0;
		this.timeToGenerate = 0;
		this.generatingTime = 200;
		this.maxMillibuckets = 3000;
		this.currentMillibuckets = 0;
		this.energyUsage = 10;
		this.millibucketsPerUse = 100;
	}

	public final IIntArray FIELDS = new IIntArray() {
		public int get(int index) {
			switch (index) {
				case 0:
					return timeToGenerate;
				case 1:
					return storedEnergy;
				case 2:
					return currentMillibuckets;
				default:
					return 0;
			}
		}

		public void set(int index, int value) {
			switch (index) {
				case 0:
					timeToGenerate = value;
					break;
				case 1:
					storedEnergy = value;
					break;
				case 2:
					currentMillibuckets = value;
					break;
			}
		}
		public int size() {
			return 3;
		}
	};



	@Override
	public void tick() {
		if (world.isRemote) {
			return;
		}
		
		if (isEnabled()) {
			setBlockEnabled(true);
		} else {
			setBlockEnabled(false);
		}

		ItemStack input = inventory.getStackInSlot(0);
		ItemStack output = inventory.getStackInSlot(1);
		
		if (timeToGenerate > 0 && storedEnergy >= energyUsage) {
			storedEnergy -= energyUsage;
			timeToGenerate--;

			if (timeToGenerate == 0) {
				if (ItemTools.isStackEmpty(output)) {
					inventory.setInventorySlotContents(1, getOutputItem());
				} else if (output.getCount() < output.getMaxStackSize()) {
					if (ItemStack.areItemsEqual(output, getOutputItem())) {
						ItemTools.incrItemStack(output, null);
						inventory.setInventorySlotContents(1, output);
					}
				}

				if (currentMillibuckets + millibucketsPerUse <= maxMillibuckets) {
					currentMillibuckets += millibucketsPerUse;
				}
			}
		} else if (storedEnergy >= energyUsage) {
			if (!ItemTools.isStackEmpty(input)) {
				if (isValidItem(input)) {
					if (ItemTools.isStackEmpty(output)||output.getCount() < output.getMaxStackSize()) {
						if (currentMillibuckets + millibucketsPerUse <= maxMillibuckets) {
							ItemTools.decrItemStack(input, null);
							if(input.getCount()<=0){
								ItemTools.removeStackFromSlot(inventory, 0);
							}
							timeToGenerate = generatingTime;
						}
					}
				}
			}
		}
		markDirty();
	}

	public boolean isEnabled() {
		if (storedEnergy >= energyUsage && currentMillibuckets+millibucketsPerUse <= maxMillibuckets) {
			if (!ItemTools.isStackEmpty(inventory.getStackInSlot(0))||(timeToGenerate>0&&storedEnergy > 0)) {
				if (ItemTools.isStackEmpty(inventory.getStackInSlot(1))
						|| inventory.getStackInSlot(1).getCount() < inventory.getStackInSlot(1).getMaxStackSize()) {
					return true;
				}
			}
		}

		return false;
	}
	
	public abstract BlockGui getOwnBlock();

	public void setBlockEnabled(boolean enabled) {
		BlockState state = world.getBlockState(getPos());
		if (state.getBlock().equals(getOwnBlock())) {
			getOwnBlock().setPowered(world, pos, state, enabled);
		}
	}

	public abstract ItemStack getOutputItem();

	public abstract boolean isValidItem(ItemStack stack);
	
	public float getEnergyPercent() {
		return ((float) storedEnergy) / ((float) maxStorage);
	}

	public float getFluidPercent() {
		return ((float) currentMillibuckets) / ((float) maxMillibuckets);
	}

	public float getProgress() {
		return ((float) timeToGenerate) / ((float) generatingTime);
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putInt("energy_stored", storedEnergy);
		compound.putInt("time_generated", timeToGenerate);
		compound.putInt("fluid_stored", currentMillibuckets);

		ItemTools.saveInventory(compound, "slots", inventory);

		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		storedEnergy = compound.getInt("energy_stored");
		timeToGenerate = compound.getInt("time_generated");
		currentMillibuckets = compound.getInt("fluid_stored");

		ItemTools.readInventory(compound, "slots", inventory);
		
		super.read(compound);
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
	public boolean isUsableByPlayer(PlayerEntity player) {
		return inventory.isUsableByPlayer(player);
	}

	@Override
	public void openInventory(PlayerEntity player) {
		inventory.openInventory(player);
	}

	@Override
	public void closeInventory(PlayerEntity player) {
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

	/*@Override
	public String getName() {
		return inventory.getName();
	}

	@Override
	public boolean hasCustomName() {
		return inventory.hasCustomName();
	}
	*/
	@Override
	public boolean isEmpty() {
		return inventory.isEmpty();
	}

	@Override
	public int[] getSlotsForFace(Direction side) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, Direction direction) {
		if (index == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, Direction direction) {
		if (index == 1) {
			return true;
		}
		return false;
	}
	

	public Inventory getInventory() {
		return inventory;
	}

	public int getMaxStorage() {
		return maxStorage;
	}

	public int getStoredEnergy() {
		return storedEnergy;
	}

	public int getEnergyUsage() {
		return energyUsage;
	}

	public int getTimeToGenerate() {
		return timeToGenerate;
	}

	public int getGeneratingTime() {
		return generatingTime;
	}

	public int getMaxMillibuckets() {
		return maxMillibuckets;
	}

	public int getMillibucketsPerUse() {
		return millibucketsPerUse;
	}

	public int getCurrentMillibuckets() {
		return currentMillibuckets;
	}
	
	public abstract Fluid getProducingFluid();
	
	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[]{new IFluidTankProperties() {
			
			@Override
			public FluidStack getContents() {
				return new FluidStack(getProducingFluid(), currentMillibuckets);
			}
			
			@Override
			public int getCapacity() {
				return maxMillibuckets;
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
				return fluidStack.getFluid().equals(getProducingFluid());
			}
			
			@Override
			public boolean canDrain() {
				return true;
			}
		}};
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		int amount=Math.min(resource.amount, currentMillibuckets);
		
		if(doDrain){
			currentMillibuckets-=amount;
			markDirty();
		}
		
		return new FluidStack(getProducingFluid(), amount);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		int amount=Math.min(maxDrain, currentMillibuckets);
		
		if(doDrain){
			currentMillibuckets-=amount;
			markDirty();
		}
		
		return new FluidStack(getProducingFluid(), amount);
	}

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        int energyNeeded = maxStorage - storedEnergy;

        if (!simulate) {
            storedEnergy += Math.min(energyNeeded, maxReceive);
            markDirty();
        }

        return Math.min(energyNeeded, maxReceive);
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        return 0;
    }

    @Override
    public int getEnergyStored() {
        return storedEnergy;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxStorage;
    }

    @Override
    public boolean canExtract() {
        return false;
    }

    @Override
    public boolean canReceive() {
        return true;
    }
}
