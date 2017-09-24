package de.maxhenkel.car.blocks.tileentity;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.maxhenkel.car.Config;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.energy.EnergyUtil;
import de.maxhenkel.car.registries.GeneratorRecipe;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public class TileEntityGenerator extends TileEntityBase
		implements ITickable, IFluidHandler, IEnergyProvider, IInventory {

	public final int maxStorage;
	public int storedEnergy;

	public final int maxMillibuckets;
	protected int currentMillibuckets;

	protected Fluid currentFluid;
	
	protected InventoryBasic inventory;

	public TileEntityGenerator() {
		this.inventory=new InventoryBasic("", false, 0);
		this.maxStorage = Config.generatorEnergyStorage;
		this.storedEnergy = 0;
		this.maxMillibuckets = Config.generatorFluidStorage;
		this.currentMillibuckets = 0;
	}

	@Override
	public void update() {

		if (worldObj.isRemote) {
			return;
		}

		if (isEnabled()) {
			setBlockEnabled(true);
		} else {
			setBlockEnabled(false);
		}

		if (currentFluid != null && currentMillibuckets > 0
				&& (storedEnergy + getCurrentGenerationFactor()) <= maxStorage) {
			currentMillibuckets--;
			storedEnergy += getCurrentGenerationFactor();

			if (currentMillibuckets <= 0) {
				currentMillibuckets = 0;
				currentFluid = null;
			}
		}

		if (currentMillibuckets <= 0 && currentFluid != null) {
			currentMillibuckets = 0;
			currentFluid = null;
		}

		if (currentFluid == null && currentMillibuckets > 0) {
			currentMillibuckets = 0;
		}

		handlePushEnergy();
		markDirty();
	}
	
	public int getCurrentGenerationFactor(Fluid f){
		for(GeneratorRecipe recipe:GeneratorRecipe.REGISTRY){
			if(recipe.getInput().isValid(f)){
				return recipe.getEnergy();
			}
		}
		
		return 0;
	}
	
	public int getCurrentGenerationFactor(){
		return getCurrentGenerationFactor(currentFluid);
	}
	
	public boolean isValidFuel(Fluid f){
		return getCurrentGenerationFactor(f)>0;
	}

	private void handlePushEnergy() {
		for (EnumFacing side : EnumFacing.values()) {
			TileEntity te = worldObj.getTileEntity(pos.offset(side));

			if (!(te instanceof IEnergyReceiver)) {
				continue;
			}

			IEnergyReceiver rec = (IEnergyReceiver) te;

			EnergyUtil.pushEnergy(this, rec, storedEnergy, side.getOpposite(), side);
		}
	}

	public boolean isEnabled() {
		int fuelGen = getCurrentGenerationFactor();
		if (currentMillibuckets > 0 && storedEnergy + fuelGen < maxStorage) {
			return true;
		}
		return false;
	}

	public void setBlockEnabled(boolean enabled) {
		IBlockState state = worldObj.getBlockState(getPos());
		if (state.getBlock().equals(ModBlocks.GENERATOR)) {
			if (state.getValue(BlockGui.POWERED) != enabled) {
				ModBlocks.GENERATOR.setPowered(worldObj, pos, state, enabled);
			}
		}
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return storedEnergy;
		case 1:
			return currentMillibuckets;
		}
		return 0;
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			storedEnergy = value;
			break;
		case 1:
			currentMillibuckets = value;
			break;
		}
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("stored_energy", storedEnergy);
		if (currentFluid != null) {
			FluidStack stack = new FluidStack(currentFluid, currentMillibuckets);
			NBTTagCompound comp = new NBTTagCompound();
			stack.writeToNBT(comp);
			compound.setTag("fluid", comp);
		}
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {

		storedEnergy = compound.getInteger("stored_energy");

		if (compound.hasKey("fluid")) {
			FluidStack stack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("fluid"));
			currentFluid = stack.getFluid();
			currentMillibuckets = stack.amount;
		}

		super.readFromNBT(compound);
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("tile.generator.name");
	}

	@Override
	public int getFieldCount() {
		return 2;
	}

	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		int i = Math.min(maxExtract, storedEnergy);

		if (!simulate) {
			storedEnergy -= i;
			markDirty();
		}

		return i;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return storedEnergy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return maxStorage;
	}

	@Override
	public IFluidTankProperties[] getTankProperties() {
		return new IFluidTankProperties[] { new IFluidTankProperties() {

			@Override
			public FluidStack getContents() {
				if (currentFluid == null) {
					return null;
				}
				return new FluidStack(currentFluid, currentMillibuckets);
			}

			@Override
			public int getCapacity() {
				return maxMillibuckets;
			}

			@Override
			public boolean canFillFluidType(FluidStack fluidStack) {
				if (isValidFuel(fluidStack.getFluid())
						&& (currentFluid == null || currentFluid.equals(fluidStack.getFluid()))) {
					return true;
				}
				return false;
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
		} };
	}

	@Override
	public int fill(FluidStack resource, boolean doFill) {

		if ((currentFluid == null && isValidFuel(resource.getFluid()))
				|| resource.getFluid().equals(currentFluid)) {
			int amount = Math.min(maxMillibuckets - currentMillibuckets, resource.amount);
			if (doFill) {
				currentMillibuckets += amount;
				if (currentFluid == null) {
					currentFluid = resource.getFluid();
				}
				markDirty();
			}
			return amount;
		}

		return 0;
	}

	@Override
	public FluidStack drain(FluidStack resource, boolean doDrain) {
		return null;
	}

	@Override
	public FluidStack drain(int maxDrain, boolean doDrain) {
		return null;
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
