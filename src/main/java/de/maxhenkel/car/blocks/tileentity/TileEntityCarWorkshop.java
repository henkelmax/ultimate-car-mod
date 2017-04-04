package de.maxhenkel.car.blocks.tileentity;

import java.util.List;
import javax.annotation.Nullable;
import de.maxhenkel.car.ItemTools;
import de.maxhenkel.car.blocks.BlockCarWorkshopOutter;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.reciepe.CarCraftingManager;
import de.maxhenkel.car.reciepe.ICarCraftingInventory;
import de.maxhenkel.car.reciepe.ICarbuilder;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityCarWorkshop extends TileEntityBase implements ICarCraftingInventory {

	private InventoryBasic craftingMatrix;
	private InventoryBasic repairInventory;
	private EntityCarBase currentCraftingCar;

	public TileEntityCarWorkshop() {
		this.craftingMatrix = new InventoryBasic(getDisplayName().getFormattedText(), false, 15);
		this.repairInventory=new InventoryBasic(getDisplayName().getFormattedText(), false, 3);
		updateRecipe();
	}
	
	public InventoryBasic getRepairInventory() {
		return repairInventory;
	}
	
	public EntityCarBase getCarOnTop() {
		IBlockState ownState = worldObj.getBlockState(getPos());

		if (!ownState.getBlock().equals(ModBlocks.CAR_WORKSHOP)) {
			return null;
		}

		BlockPos start = getPos().offset(EnumFacing.UP);

		AxisAlignedBB aabb = new AxisAlignedBB(start.getX(), start.getY(), start.getZ(), start.getX() + 1,
				start.getY() + 1, start.getZ() + 1);

		List<EntityCarBase> cars = worldObj.getEntitiesWithinAABB(EntityCarBase.class, aabb);
		if (cars.isEmpty()) {
			return null;
		}

		return cars.get(0);
	}

	public void spawnCar(EntityPlayer player) {
		if (!areBlocksAround()) {
			player.addChatMessage(new TextComponentTranslation("message.incomplete_structure"));
			return;
		}

		if (!isTopFree()) {
			player.addChatMessage(new TextComponentTranslation("message.blocks_on_top"));
			return;
		}
		
		updateRecipe();

		EntityCarBase car = currentCraftingCar;

		if (car == null) {
			player.addChatMessage(new TextComponentTranslation("message.no_reciepe"));
			return;
		}
		BlockPos spawnPos = pos.up();
		
		car.setPosition(spawnPos.getX()+car.width/2, spawnPos.getY(), spawnPos.getZ()+car.width/2);
		
		removeCraftItems();
		worldObj.spawnEntityInWorld(car);
	}

	// Multiblock \/

	public void checkValidity() {
		if (areBlocksAround()) {
			placeStructure();
		}
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("tile.car_workshop.name");
	}

	/*
	 * north=1 northeast=2 east=3 southeast=4 south=5 southwest=6 west=7
	 * northwest=8
	 */
	private void placeStructure() {
		worldObj.setBlockState(pos.add(0, 0, -1), getState(1));
		worldObj.setBlockState(pos.add(1, 0, -1), getState(2));
		worldObj.setBlockState(pos.add(1, 0, 0), getState(3));
		worldObj.setBlockState(pos.add(1, 0, 1), getState(4));
		worldObj.setBlockState(pos.add(0, 0, 1), getState(5));
		worldObj.setBlockState(pos.add(-1, 0, 1), getState(6));
		worldObj.setBlockState(pos.add(-1, 0, 0), getState(7));
		worldObj.setBlockState(pos.add(-1, 0, -1), getState(8));

		setOwnBlockValid(true);
	}

	private void setOwnBlockValid(boolean valid) {
		IBlockState state = worldObj.getBlockState(pos);
		if (!state.getBlock().equals(ModBlocks.CAR_WORKSHOP)) {
			return;
		}
		ModBlocks.CAR_WORKSHOP.setValid(worldObj, pos, state, valid);
	}

	private IBlockState getState(int meta) {
		return ModBlocks.CAR_WORKSHOP_OUTTER.getDefaultState().withProperty(BlockCarWorkshopOutter.POSITION,
				meta);
	}

	public boolean areBlocksAround() {
		if (!checkSideBlock(pos.add(0, 0, 1))) {
			return false;
		}
		if (!checkSideBlock(pos.add(1, 0, 0))) {
			return false;
		}
		if (!checkSideBlock(pos.add(1, 0, 1))) {
			return false;
		}
		if (!checkSideBlock(pos.add(0, 0, -1))) {
			return false;
		}
		if (!checkSideBlock(pos.add(-1, 0, 0))) {
			return false;
		}
		if (!checkSideBlock(pos.add(-1, 0, -1))) {
			return false;
		}
		if (!checkSideBlock(pos.add(-1, 0, 1))) {
			return false;
		}
		if (!checkSideBlock(pos.add(1, 0, -1))) {
			return false;
		}
		return true;
	}

	public boolean isTopFree() {
		for (int x = pos.getX() - 1; x <= pos.getX() + 1; x++) {
			for (int y = pos.getY() + 1; y <= pos.getX() + 2; y++) {
				for (int z = pos.getZ() - 1; z <= pos.getZ() + 1; z++) {
					if (!checkBlockAir(new BlockPos(x, y, z))) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private boolean checkBlockAir(BlockPos p) {
		if (worldObj.isAirBlock(p)) {
			return true;
		}

		return false;
	}

	private boolean checkSideBlock(BlockPos p) {
		IBlockState state = worldObj.getBlockState(p);
		if (state.getBlock().equals(ModBlocks.CAR_WORKSHOP_OUTTER)) {
			return true;
		}
		return false;
	}

	// Inventory

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		ItemTools.saveInventory(compound, "crafting", craftingMatrix);
		
		ItemTools.saveInventory(compound, "repair", repairInventory);

		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		ItemTools.readInventory(compound, "crafting", craftingMatrix);
		
		ItemTools.readInventory(compound, "repair", repairInventory);

		super.readFromNBT(compound);
	}

	public void updateRecipe() {
		ICarbuilder builder = getCurrentRecipe();
		if (builder == null) {
			this.currentCraftingCar = null;
			return;
		}
		this.currentCraftingCar = builder.build(worldObj);
	}

	@Nullable
	public ICarbuilder getCurrentRecipe() {
		return CarCraftingManager.getInstance().findMatchingRecipe(this);
	}

	public void removeCraftItems() {
		for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
			ItemStack stack = craftingMatrix.getStackInSlot(i);
			if (!ItemTools.isStackEmpty(stack)) {
				stack.stackSize--;
				if(stack.stackSize<=0){
					craftingMatrix.setInventorySlotContents(i, null);
				}
			}
		}
	}

	@Override
	public String getName() {
		return craftingMatrix.getName();
	}

	@Override
	public boolean hasCustomName() {
		return craftingMatrix.hasCustomName();
	}

	@Override
	public int getSizeInventory() {
		return craftingMatrix.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return craftingMatrix.getStackInSlot(index);
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = craftingMatrix.decrStackSize(index, count);
		updateRecipe();
		return stack;
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		ItemStack stack = craftingMatrix.removeStackFromSlot(index);
		updateRecipe();
		return stack;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		craftingMatrix.setInventorySlotContents(index, stack);
		updateRecipe();
	}

	@Override
	public int getInventoryStackLimit() {
		return craftingMatrix.getInventoryStackLimit();
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return craftingMatrix.isUseableByPlayer(player);
	}

	@Override
	public void openInventory(EntityPlayer player) {
		craftingMatrix.openInventory(player);
	}

	@Override
	public void closeInventory(EntityPlayer player) {
		craftingMatrix.openInventory(player);
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return craftingMatrix.isItemValidForSlot(index, stack);
	}

	@Override
	public int getField(int id) {
		return craftingMatrix.getField(id);
	}

	@Override
	public void setField(int id, int value) {
		craftingMatrix.setField(id, value);
	}

	@Override
	public int getFieldCount() {
		return craftingMatrix.getFieldCount();
	}

	@Override
	public void clear() {
		craftingMatrix.clear();
		updateRecipe();
	}

	@Override
	public ItemStack getStackInRowAndColumn(int row, int column) {
		return row >= 0 && row < 5 && column >= 0 && column <= 3 ? this.getStackInSlot(row + column * 5)
				: null;
	}

	public EntityCarBase getCurrentCraftingCar() {
		return currentCraftingCar;
	}

	public void repairCar(EntityPlayer player) {
		if (!areBlocksAround()) {
			player.addChatMessage(new TextComponentTranslation("message.incomplete_structure"));
			return;
		}
		
		if(!areRepairItemsInside()){
			player.addChatMessage(new TextComponentTranslation("message.no_repair_items"));
			return;
		}
		
		EntityCarBase carBase=getCarOnTop();
		
		if(!(carBase instanceof EntityCarDamageBase)){
			player.addChatMessage(new TextComponentTranslation("message.no_car"));
			return;
		}
		
		EntityCarDamageBase car=(EntityCarDamageBase) carBase;
		
		if(car.getDamage()<=0){
			return;
		}
		
		damageRepairItemsInside(player);
		
		car.setDamage(car.getDamage()-10F);
		
		ModSounds.playSound(SoundEvents.BLOCK_ANVIL_USE, worldObj, pos, null, SoundCategory.BLOCKS);
	}
	
	public boolean areRepairItemsInside(){
		for (int i = 0; i < repairInventory.getSizeInventory(); i++) {
			if(ItemTools.isStackEmpty(repairInventory.getStackInSlot(i))){
				return false;
			}
		}
		
		return true;
	}
	
	public void damageRepairItemsInside(EntityPlayer player){
		for (int i = 0; i < repairInventory.getSizeInventory(); i++) {
			ItemStack stack=repairInventory.getStackInSlot(i);
			if(!ItemTools.isStackEmpty(stack)){
				stack.damageItem(10, player);
			}
		}
	}

}
