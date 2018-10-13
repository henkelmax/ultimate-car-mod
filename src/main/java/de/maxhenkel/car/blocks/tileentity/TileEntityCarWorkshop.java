package de.maxhenkel.car.blocks.tileentity;

import java.util.ArrayList;
import java.util.List;
import de.maxhenkel.car.entity.car.base.EntityCarTemperatureBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.blocks.BlockCarWorkshopOutter;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityCarWorkshop extends TileEntityBase implements IInventory {

	private InventoryBasic craftingMatrix;
	private InventoryBasic repairInventory;
	private EntityGenericCar currentCraftingCar;

	public TileEntityCarWorkshop() {
		this.craftingMatrix = new InventoryBasic(getDisplayName().getFormattedText(), false, 15);
		this.repairInventory=new InventoryBasic(getDisplayName().getFormattedText(), false, 3);
		//updateRecipe();
	}
	
	public InventoryBasic getRepairInventory() {
		return repairInventory;
	}
	
	public EntityCarBase getCarOnTop() {
		IBlockState ownState = world.getBlockState(getPos());

		if (!ownState.getBlock().equals(ModBlocks.CAR_WORKSHOP)) {
			return null;
		}

		BlockPos start = getPos().offset(EnumFacing.UP);

		AxisAlignedBB aabb = new AxisAlignedBB(start.getX(), start.getY(), start.getZ(), start.getX() + 1,
				start.getY() + 1, start.getZ() + 1);

		List<EntityCarBase> cars = world.getEntitiesWithinAABB(EntityCarBase.class, aabb);
		if (cars.isEmpty()) {
			return null;
		}

		return cars.get(0);
	}

	public void spawnCar(EntityPlayer player) {
		if (!areBlocksAround()) {
			player.sendMessage(new TextComponentTranslation("message.incomplete_structure"));
			return;
		}

		if (!isTopFree()) {
			player.sendMessage(new TextComponentTranslation("message.blocks_on_top"));
			return;
		}
		
		updateRecipe();

		EntityGenericCar car = currentCraftingCar;

		if (car == null) {
			player.sendMessage(new TextComponentTranslation("message.no_reciepe"));
			return;
		}
		BlockPos spawnPos = pos.up();
		car.setPosition(spawnPos.getX()+0.5, spawnPos.getY(), spawnPos.getZ()+0.5);
		
		removeCraftItems();
		car.setFuelAmount(100);
		world.spawnEntity(car);
		car.initTemperature();
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
		world.setBlockState(pos.add(0, 0, -1), getState(1));
		world.setBlockState(pos.add(1, 0, -1), getState(2));
		world.setBlockState(pos.add(1, 0, 0), getState(3));
		world.setBlockState(pos.add(1, 0, 1), getState(4));
		world.setBlockState(pos.add(0, 0, 1), getState(5));
		world.setBlockState(pos.add(-1, 0, 1), getState(6));
		world.setBlockState(pos.add(-1, 0, 0), getState(7));
		world.setBlockState(pos.add(-1, 0, -1), getState(8));

		setOwnBlockValid(true);
	}

	private void setOwnBlockValid(boolean valid) {
		IBlockState state = world.getBlockState(pos);
		if (!state.getBlock().equals(ModBlocks.CAR_WORKSHOP)) {
			return;
		}
		ModBlocks.CAR_WORKSHOP.setValid(world, pos, state, valid);
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
		BlockPos pos=getPos();
		for (int x = - 1; x <= 1; x++) {
			for (int y = 1; y <= 2; y++) {
				for (int z =  - 1; z <= 1; z++) {
					if (!checkBlockAir(pos.add(x, y, z))) {
						return false;
					}
				}
			}
		}

		return true;
	}

	private boolean checkBlockAir(BlockPos p) {
		return world.isAirBlock(p);
	}

	private boolean checkSideBlock(BlockPos p) {
		IBlockState state = world.getBlockState(p);
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
		EntityGenericCar car=new EntityGenericCar(world);

		List<String> parts=new ArrayList<>();

		for(int i=0; i<craftingMatrix.getSizeInventory(); i++){
		    ItemStack stack=craftingMatrix.getStackInSlot(i);

		    if(stack.getItem().equals(Items.STICK)){
                parts.add("oak_chassis");
                parts.add("wheel");
            }
        }

		car.setPartStrings(parts.toArray(new String[0]));
		car.tryInitModel();

		//TODO check if valid

		this.currentCraftingCar = car;
	}
/*
	@Nullable
	public ICarbuilder getCurrentRecipe() {
		return CarCraftingRegistry.findMatchingRecipe(this);
	}*/

	public void removeCraftItems() {
		for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
			ItemStack stack = craftingMatrix.getStackInSlot(i);
			if (!ItemTools.isStackEmpty(stack)) {
				craftingMatrix.setInventorySlotContents(i, ItemTools.decrItemStack(stack, null));
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
	public boolean isUsableByPlayer(EntityPlayer player) {
		return craftingMatrix.isUsableByPlayer(player);
	}
	
	@Override
	public boolean isEmpty() {
		return craftingMatrix.isEmpty();
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

	/*@Override
	public ItemStack getStackInRowAndColumn(int row, int column) {
		return row >= 0 && row < 5 && column >= 0 && column <= 3 ? this.getStackInSlot(row + column * 5)
				: ItemTools.EMPTY;
	}*/

	public EntityGenericCar getCurrentCraftingCar() {
		return currentCraftingCar;
	}

	public void repairCar(EntityPlayer player) {
		if (!areBlocksAround()) {
			player.sendMessage(new TextComponentTranslation("message.incomplete_structure"));
			return;
		}
		
		if(!areRepairItemsInside()){
			player.sendMessage(new TextComponentTranslation("message.no_repair_items"));
			return;
		}
		
		EntityCarBase carBase=getCarOnTop();
		
		if(!(carBase instanceof EntityCarDamageBase)){
			player.sendMessage(new TextComponentTranslation("message.no_car"));
			return;
		}
		
		EntityCarDamageBase car=(EntityCarDamageBase) carBase;
		
		if(car.getDamage()<=0){
			return;
		}
		
		damageRepairItemsInside(player);
		
		car.setDamage(car.getDamage()-10F);
		
		ModSounds.playSound(ModSounds.ratchet, world, pos, null, SoundCategory.BLOCKS);
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
				ItemTools.damageStack(stack, 10, player);
			}
		}
	}

}
