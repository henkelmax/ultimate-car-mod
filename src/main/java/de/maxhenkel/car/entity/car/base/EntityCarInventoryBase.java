package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.gui.GuiHandler;
import de.maxhenkel.car.items.ItemCanister;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;

public abstract class EntityCarInventoryBase extends EntityCarFuelBase implements IInventory {

    protected IInventory internalInventory;
    protected IInventory externalInventory;
    protected IInventory partInventory;

    public EntityCarInventoryBase(World worldIn) {
        super(worldIn);

        this.internalInventory = new InventoryBasic(getCarName().getUnformattedText(), false, 27);
        this.externalInventory = new InventoryBasic(getCarName().getUnformattedText(), false, 0);
        this.partInventory = new InventoryBasic(getCarName().getUnformattedText(), false, 15);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if (canPlayerAccessInventoryExternal(player) && player.isSneaking()) {
            //Canister
            ItemStack stack = player.getHeldItem(hand);

            if (!ItemTools.isStackEmpty(stack) && (stack.getItem() instanceof ItemCanister)) {
                boolean success = ItemCanister.fillCanister(stack, this);

                if (success) {
                    ModSounds.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, world, getPosition(), null, SoundCategory.BLOCKS);
                }
                return true;
            }

            //Inv

            if (externalInventory.getSizeInventory() <= 0) {
                player.displayGUIChest(internalInventory);
            } else {
                player.displayGUIChest(externalInventory);
            }

            return true;
        }
        return super.processInitialInteract(player, hand);
    }

    public boolean canPlayerAccessInventoryExternal(EntityPlayer player) {
        return true;
    }

    public IInventory getPartInventory() {
        return partInventory;
    }

    @Override
    public void destroyCar(EntityPlayer player, boolean dropParts) {
        super.destroyCar(player, dropParts);

        InventoryHelper.dropInventoryItems(world, this, this);
        InventoryHelper.dropInventoryItems(world, this, externalInventory);
        InventoryHelper.dropInventoryItems(world, this, partInventory);
    }

    @Override
    public void openCarGUi(EntityPlayer player) {
        super.openCarGUi(player);
        if (!world.isRemote) {
            player.openGui(Main.instance(), GuiHandler.GUI_CAR, world, player.getPosition().getX(), player.getPosition().getY(), player.getPosition().getZ());
        }
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        ItemTools.readInventory(compound, "int_inventory", internalInventory);

        this.externalInventory= new InventoryBasic(getCarName().getUnformattedText(), false, compound.getInteger("external_inventory_size"));
        ItemTools.readInventory(compound, "external_inventory", externalInventory);

        ItemTools.readInventory(compound, "parts", partInventory);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        ItemTools.saveInventory(compound, "int_inventory", internalInventory);

        compound.setInteger("external_inventory_size", externalInventory.getSizeInventory());
        ItemTools.saveInventory(compound, "external_inventory", externalInventory);

        ItemTools.saveInventory(compound, "parts", partInventory);
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
    public boolean isUsableByPlayer(EntityPlayer player) {
        return internalInventory.isUsableByPlayer(player);
    }

    @Override
    public boolean isEmpty() {
        return internalInventory.isEmpty();
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
