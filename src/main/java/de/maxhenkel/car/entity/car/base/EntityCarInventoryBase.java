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
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public abstract class EntityCarInventoryBase extends EntityCarFuelBase implements IInventory{

	protected IInventory inventory;
	
	public EntityCarInventoryBase(World worldIn) {
		super(worldIn);
		
		this.inventory=new InventoryBasic(new TextComponentTranslation("container.car").getFormattedText(), false, getInventorySize());
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand) {
		if(canPlayerAccessInventory(player) && player.isSneaking()){
			player.displayGUIChest(this);
			return true;
		}
		return super.processInitialInteract(player, stack, hand);
	}
	
	public boolean canPlayerAccessInventory(EntityPlayer player){
		return true;
	}
	
	public int getInventorySize(){
		return 27;
	}
	
	@Override
	public void destroyCar(boolean dropParts) {
		InventoryHelper.dropInventoryItems(worldObj, this, this);
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
		
		NBTTagList list = compound.getTagList("inventory", 10);
		
		for (int i = 0; i < list.tagCount(); i++) {
			NBTTagCompound tag = list.getCompoundTagAt(i);
			ItemStack stack=ItemStack.loadItemStackFromNBT(tag);
			inventory.setInventorySlotContents(i, stack);
		}
	}
	
	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		
		NBTTagList list = new NBTTagList();

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			if (inventory.getStackInSlot(i) != null) {
				NBTTagCompound tag = new NBTTagCompound();
				inventory.getStackInSlot(i).writeToNBT(tag);
				list.appendTag(tag);
			}
		}

		compound.setTag("inventory", list);
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
	public void markDirty() {
		inventory.markDirty();
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
	public int getField(int id) {
		return inventory.getField(id);
	}

	@Override
	public void setField(int id, int value) {
		inventory.setField(id, value);
	}

	@Override
	public int getFieldCount() {
		return inventory.getFieldCount();
	}

	@Override
	public void clear() {
		inventory.clear();
	}

}
