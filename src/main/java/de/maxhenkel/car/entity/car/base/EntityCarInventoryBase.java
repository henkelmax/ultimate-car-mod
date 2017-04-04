package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public abstract class EntityCarInventoryBase extends EntityCarFuelBase implements IInventory{

	protected IInventory internalInventory;
	protected IInventory externalInventory;
	
	public EntityCarInventoryBase(World worldIn) {
		super(worldIn);
		
		this.internalInventory=new InventoryBasic(getCarName().getFormattedText(), false, 27);
		this.externalInventory=new InventoryBasic(getCarName().getFormattedText(), false, 0);
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand) {
		if(canPlayerAccessInventory(player) && player.isSneaking()){
			if(externalInventory.getSizeInventory()<=0){
				player.displayGUIChest(this);
			}else{
				player.displayGUIChest(externalInventory);
			}
			
			return true;
		}
		return super.processInitialInteract(player, stack, hand);
	}
	
	public boolean canPlayerAccessInventory(EntityPlayer player){
		return true;
	}
	
	@Override
	public void destroyCar(boolean dropParts) {
		InventoryHelper.dropInventoryItems(worldObj, this, this);
		InventoryHelper.dropInventoryItems(worldObj, this, externalInventory);
		super.destroyCar(dropParts);
	}
	
	@Override
	public void openCarGUi(EntityPlayer player) {
		super.openCarGUi(player);
		if(!worldObj.isRemote){
			player.openGui(Main.instance(), GuiHandler.GUI_CAR, worldObj, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        }
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		
		readInventory("inventory", compound, internalInventory);
		
		if(externalInventory.getSizeInventory()>0){
			readInventory("external_inventory", compound, externalInventory);
		}
	}
	
	public static void readInventory(String name, NBTTagCompound compound, IInventory inv){
		NBTTagList list = compound.getTagList(name, 10);
		
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			ItemStack stack=ItemStack.loadItemStackFromNBT(tag);
			inv.setInventorySlotContents(i, stack);
		}
	}
	
	public static void writeInventory(String name, NBTTagCompound compound, IInventory inv){
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < inv.getSizeInventory(); i++) {
			if (inv.getStackInSlot(i) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				inv.getStackInSlot(i).writeToNBT(tag);
				list.appendTag(tag);
			}
		}

		compound.setTag(name, list);
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		
		writeInventory("inventory", compound, internalInventory);
		
		if(externalInventory.getSizeInventory()>0){
			writeInventory("external_inventory", compound, externalInventory);
		}
	}

	@Override
	public int getSizeInventory() {
		return internalInventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return internalInventory.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return internalInventory.decrStackSize(index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return internalInventory.removeStackFromSlot(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		internalInventory.setInventorySlotContents(index, stack);
	}

	@Override
	public int getInventoryStackLimit() {
		return internalInventory.getInventoryStackLimit();
	}

	@Override
	public void markDirty() {
		internalInventory.markDirty();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return internalInventory.isUseableByPlayer(player);
	}

	@Override
	public void openInventory(EntityPlayer player) {
		internalInventory.openInventory(player);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		internalInventory.closeInventory(player);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return internalInventory.isItemValidForSlot(index, stack);
	}

	@Override
	public int getField(int id) {
		return internalInventory.getField(id);
	}

	@Override
	public void setField(int id, int value) {
		internalInventory.setField(id, value);
	}

	@Override
	public int getFieldCount() {
		return internalInventory.getFieldCount();
	}

	@Override
	public void clear() {
		internalInventory.clear();
	}

}
