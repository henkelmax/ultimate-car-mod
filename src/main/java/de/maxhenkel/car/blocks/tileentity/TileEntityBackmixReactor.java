package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.tools.FluidStackWrapper;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.IIntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityBackmixReactor extends TileEntityBase implements ITickableTileEntity, IFluidHandler, IEnergyStorage, IInventory {

	public final int maxStorage;
	protected int storedEnergy;
	public final int energyUsage;

	public final int methanolUsage;
	public final int maxMethanol;
	protected int currentMethanol;

	public final int canolaUsage;
	public final int maxCanola;
	protected int currentCanola;

	public final int maxMix;
	protected int currentMix;

	public final int mixGeneration;

	public final int generatingTime;
	protected int timeToGenerate;

	public TileEntityBackmixReactor() {
		super(Main.BACKMIX_REACTOR_TILE_ENTITY_TYPE);
		this.maxStorage = Config.backmixReactorEnergyStorage.get();
		this.storedEnergy = 0;
		this.energyUsage = Config.backmixReactorEnergyUsage.get();

		this.maxMethanol = Config.backmixReactorFluidStorage.get();
		this.maxCanola = Config.backmixReactorFluidStorage.get();
		this.maxMix = Config.backmixReactorFluidStorage.get();

		this.currentCanola = 0;
		this.currentMethanol = 0;
		this.currentMix = 0;

		this.generatingTime = Config.backmixReactorGeneratingTime.get();
		this.timeToGenerate = 0;

		this.mixGeneration = Config.backmixReactorMixGeneration.get();
		this.methanolUsage = Config.backmixReactorMethanolUsage.get();
		this.canolaUsage = Config.backmixReactorCanolaUsage.get();
	}

	public final IIntArray FIELDS = new IIntArray() {
		public int get(int index) {
			switch (index) {
				case 0:
					return storedEnergy;
				case 1:
					return currentCanola;
				case 2:
					return currentMethanol;
				case 3:
					return currentMix;
				case 4:
					return timeToGenerate;
			}
			return 0;
		}

		public void set(int index, int value) {
			switch (index) {
				case 0:
					storedEnergy = value;
					break;
				case 1:
					currentCanola = value;
					break;
				case 2:
					currentMethanol = value;
					break;
				case 3:
					currentMix = value;
					break;
				case 4:
					timeToGenerate = value;
					break;
			}
		}
		public int size() {
			return 5;
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

		if (timeToGenerate > 0 && storedEnergy >= energyUsage) {
			storedEnergy -= energyUsage;
			timeToGenerate--;
			

			if (timeToGenerate == 0) {

				if (currentMix + mixGeneration <= maxMix) {
					currentMix += mixGeneration;
					currentCanola-=canolaUsage;
					currentMethanol-=methanolUsage;
				}
			}
		} else if (storedEnergy >= energyUsage) {
			if (currentCanola >= canolaUsage) {
				if (currentMethanol >= methanolUsage) {
					if (currentMix + mixGeneration <= maxMix) {
						timeToGenerate = generatingTime;
					}
				}
			}
		}
		markDirty();
	}

	public boolean isEnabled() {
		if (storedEnergy > 0 && currentMix < maxMix) {
			if(currentMethanol >= methanolUsage){
				if(currentCanola >= canolaUsage){
					return true;
				}
			}
		}
		return false;
	}

	public void setBlockEnabled(boolean enabled) {
		BlockState state = world.getBlockState(getPos());
		if (state.getBlock().equals(ModBlocks.BACKMIX_REACTOR)) {
			ModBlocks.BACKMIX_REACTOR.setPowered(world, pos, state, enabled);
		}
	}

	@Override
	public CompoundNBT write(CompoundNBT compound) {
		compound.putInt("stored_endergy", storedEnergy);
		compound.putInt("canola", currentCanola);
		compound.putInt("methanol", currentMethanol);
		compound.putInt("mix", currentMix);
		compound.putInt("time", timeToGenerate);
		return super.write(compound);
	}

	@Override
	public void read(CompoundNBT compound) {
		storedEnergy=compound.getInt("stored_endergy");
		currentCanola=compound.getInt("canola");
		currentMethanol=compound.getInt("methanol");
		currentMix=compound.getInt("mix");
		timeToGenerate=compound.getInt("time");
		super.read(compound);
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { new IFluidTankProperties() {

			@Override
			public FluidStack getContents() {
				return new FluidStackWrapper(ModFluids.CANOLA_OIL, currentCanola);
			}

			@Override
			public int getCapacity() {
				return maxCanola;
			}

			@Override
			public boolean canFillFluidType(FluidStack fluidStack) {
				return fluidStack.getFluid().equals(ModFluids.CANOLA_OIL);
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
				return new FluidStackWrapper(ModFluids.METHANOL, currentMethanol);
			}

			@Override
			public int getCapacity() {
				return maxMethanol;
			}

			@Override
			public boolean canFillFluidType(FluidStack fluidStack) {
				return fluidStack.getFluid().equals(ModFluids.METHANOL);
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
				return new FluidStackWrapper(ModFluids.CANOLA_METHANOL_MIX, currentMix);
			}

			@Override
			public int getCapacity() {
				return maxMix;
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
				return fluidStack.getFluid().equals(ModFluids.CANOLA_METHANOL_MIX);
			}

			@Override
			public boolean canDrain() {
				return true;
			}
		} };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {
		if (resource.getFluid().equals(ModFluids.METHANOL)) {
			int amount = Math.min(maxMethanol - currentMethanol, resource.amount);
			if (doFill) {
				currentMethanol += amount;
				markDirty();
			}
			return amount;
		} else if (resource.getFluid().equals(ModFluids.CANOLA_OIL)) {
			int amount = Math.min(maxCanola - currentCanola, resource.amount);
			if (doFill) {
				currentCanola += amount;
				markDirty();
			}
			return amount;
		}

		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		int amount = Math.min(resource.amount, currentMix);

		if (doDrain) {
			currentMix -= amount;
			markDirty();
		}

		return new FluidStackWrapper(ModFluids.CANOLA_METHANOL_MIX, amount);
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		int amount = Math.min(maxDrain, currentMix);

		if (doDrain) {
			currentMix -= amount;
			markDirty();
		}

		return new FluidStackWrapper(ModFluids.CANOLA_METHANOL_MIX, amount);
	}

	@Override
	public int getSizeInventory() {
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return null;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return null;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {

	}

	@Override
	public int getInventoryStackLimit() {
		return 0;
	}

	@Override
	public boolean isUsableByPlayer(PlayerEntity player) {
		return true;
	}

	@Override
	public void openInventory(PlayerEntity player) {

	}

	@Override
	public void closeInventory(PlayerEntity player) {

	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return false;
	}

	@Override
	public void clear() {

	}

	@Override
	public boolean isEmpty() {
		return true;
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

	public int getCurrentCanola() {
		return currentCanola;
	}

	public int getCurrentMethanol() {
		return currentMethanol;
	}

	public int getCurrentMix() {
		return currentMix;
	}

	public int getStoredEnergy() {
		return storedEnergy;
	}

	public int getTimeToGenerate() {
		return timeToGenerate;
	}

	@Override
	public ITextComponent getTranslatedName() {
		return new TranslationTextComponent("block.car.backmix_reactor");
	}

	@Override
	public IIntArray getFields() {
		return FIELDS;
	}
}
