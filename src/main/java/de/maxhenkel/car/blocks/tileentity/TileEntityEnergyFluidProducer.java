package de.maxhenkel.car.blocks.tileentity;

import java.util.List;

import cofh.api.energy.IEnergyReceiver;
import de.maxhenkel.car.ItemTools;
import de.maxhenkel.car.blocks.BlockGui;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

public abstract class TileEntityEnergyFluidProducer extends TileEntityBase implements IEnergyReceiver, ISidedInventory, ITickable, IFluidHandler{

	protected InventoryBasic inventory;

	protected int maxStorage; //final
	protected int storedEnergy;
	protected int energyUsage; //final

	protected int timeToGenerate;
	protected int generatingTime; //final

	protected int maxMillibuckets; //final
	protected int millibucketsPerUse; //final
	protected int currentMillibuckets;

	public TileEntityEnergyFluidProducer() {
		this.inventory = new InventoryBasic(getDisplayName().getFormattedText(), false, 2);
		this.maxStorage = 10000;
		this.storedEnergy = 0;
		this.timeToGenerate = 0;
		this.generatingTime = 200;
		this.maxMillibuckets = 3000;
		this.currentMillibuckets = 0;
		this.energyUsage = 10;
		this.millibucketsPerUse = 100;
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

		ItemStack input = inventory.getStackInSlot(0);
		ItemStack output = inventory.getStackInSlot(1);
		
		if (timeToGenerate > 0 && storedEnergy >= energyUsage) {
			storedEnergy -= energyUsage;
			timeToGenerate--;

			if (timeToGenerate == 0) {
				if (ItemTools.isStackEmpty(output)) {
					inventory.setInventorySlotContents(1, getOutputItem());
				} else if (output.stackSize < output.getMaxStackSize()) {
					if (output.areItemsEqual(output, getOutputItem())) {
						output.stackSize++;
						inventory.setInventorySlotContents(1, output);
					}
				}

				if (currentMillibuckets + millibucketsPerUse <= maxMillibuckets) {
					currentMillibuckets += millibucketsPerUse;
				}
			}
		} else if (storedEnergy >= energyUsage) {
			if (!ItemTools.isStackEmpty(input)) {
				if (contains(getInputItems(), input.getItem())) {
					if (ItemTools.isStackEmpty(output)||output.stackSize < output.getMaxStackSize()) {
						if (currentMillibuckets + millibucketsPerUse <= maxMillibuckets) {
							input.stackSize--;
							if(input.stackSize<=0){
								inventory.setInventorySlotContents(0, null);
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
						|| inventory.getStackInSlot(1).stackSize < inventory.getStackInSlot(1).getMaxStackSize()) {
					return true;
				}
			}
		}

		return false;
	}
	
	public abstract BlockGui getOwnBlock();

	public void setBlockEnabled(boolean enabled) {
		IBlockState state = worldObj.getBlockState(getPos());
		if (state.getBlock().equals(getOwnBlock())) {
			getOwnBlock().setPowered(worldObj, pos, state, enabled);
		}
	}

	public abstract ItemStack getOutputItem();

	public abstract List<Item> getInputItems();
	
	public boolean contains(List<Item> list, Item item){
		for(Item i:list){
			if(i.equals(item)){
				return true;
			}
		}
		return false;
	}

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
	public int getField(int id) {
		switch (id) {
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

	@Override
	public void setField(int id, int value) {
		switch (id) {
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

	@Override
	public int getFieldCount() {
		return 3;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("energy_stored", storedEnergy);
		compound.setInteger("time_generated", timeToGenerate);
		compound.setInteger("fluid_stored", currentMillibuckets);

		NBTTagList list = new NBTTagList();

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				inventory.getStackInSlot(i).writeToNBT(tag);
				list.appendTag(tag);
			}
		}

		compound.setTag("slots", list);

		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		storedEnergy = compound.getInteger("energy_stored");
		timeToGenerate = compound.getInteger("time_generated");
		currentMillibuckets = compound.getInteger("fluid_stored");

		NBTTagList list = compound.getTagList("slots", 10);

		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			ItemStack stack = ItemStack.loadItemStackFromNBT(tag);
			inventory.setInventorySlotContents(i, stack);
		}

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
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		int energyNeeded = maxStorage - storedEnergy;

		if (!simulate) {
			storedEnergy += Math.min(energyNeeded, maxReceive);
			markDirty();
		}

		return Math.min(energyNeeded, maxReceive);
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
	public abstract ITextComponent getDisplayName();

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

	@Override
	public String getName() {
		return inventory.getName();
	}

	@Override
	public boolean hasCustomName() {
		return inventory.hasCustomName();
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return new int[] { 0, 1 };
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		if (index == 0) {
			return true;
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		if (index == 1) {
			return true;
		}
		return false;
	}
	

	public InventoryBasic getInventory() {
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
	
}
