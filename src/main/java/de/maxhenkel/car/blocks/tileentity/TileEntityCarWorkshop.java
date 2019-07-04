package de.maxhenkel.car.blocks.tileentity;

import java.util.ArrayList;
import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.car.parts.Part;
import de.maxhenkel.car.entity.car.parts.PartRegistry;
import de.maxhenkel.car.items.ICarPart;
import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.tools.ItemTools;
import de.maxhenkel.car.blocks.BlockCarWorkshopOutter;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityCarWorkshop extends TileEntityBase implements IInventory {

    private Inventory craftingMatrix;
    private Inventory repairInventory;
    private EntityGenericCar currentCraftingCar;
    private List<ITextComponent> messages;

    public TileEntityCarWorkshop() {
        super(Main.CAR_WORKSHOP_TILE_ENTITY_TYPE);
        this.craftingMatrix = new Inventory(15);
        this.repairInventory = new Inventory(3);
        this.messages = new ArrayList<>();
        //updateRecipe();
    }

    public Inventory getRepairInventory() {
        return repairInventory;
    }

    public EntityCarBase getCarOnTop() {
        BlockState ownState = world.getBlockState(getPos());

        if (!ownState.getBlock().equals(ModBlocks.CAR_WORKSHOP)) {
            return null;
        }

        BlockPos start = getPos().offset(Direction.UP);

        AxisAlignedBB aabb = new AxisAlignedBB(start.getX(), start.getY(), start.getZ(), start.getX() + 1,
                start.getY() + 1, start.getZ() + 1);

        List<EntityCarBase> cars = world.getEntitiesWithinAABB(EntityCarBase.class, aabb);
        if (cars.isEmpty()) {
            return null;
        }

        return cars.get(0);
    }

    public void spawnCar(PlayerEntity player) {
        if (!areBlocksAround()) {
            player.sendMessage(new TranslationTextComponent("message.incomplete_structure"));
            return;
        }

        if (!isTopFree()) {
            player.sendMessage(new TranslationTextComponent("message.blocks_on_top"));
            return;
        }

        updateRecipe();

        EntityGenericCar car = currentCraftingCar;

        if (car == null || !isCurrentCraftingCarValid()) {
            player.sendMessage(new TranslationTextComponent("message.no_reciepe"));
            return;
        }
        BlockPos spawnPos = pos.up();
        car.setPosition(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);

        removeCraftItems();
        car.setFuelAmount(100);
        world.addEntity(car);
        car.setIsSpawned(true);
        car.initTemperature();
    }

    // Multiblock \/

    public void checkValidity() {
        if (areBlocksAround()) {
            placeStructure();
        }
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
        BlockState state = world.getBlockState(pos);
        if (!state.getBlock().equals(ModBlocks.CAR_WORKSHOP)) {
            return;
        }
        ModBlocks.CAR_WORKSHOP.setValid(world, pos, state, valid);
    }

    private BlockState getState(int meta) {
        return ModBlocks.CAR_WORKSHOP_OUTTER.getDefaultState().with(BlockCarWorkshopOutter.POSITION,
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
        BlockPos pos = getPos();
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {
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
        BlockState state = world.getBlockState(p);
        if (state.getBlock().equals(ModBlocks.CAR_WORKSHOP_OUTTER)) {
            return true;
        }
        return false;
    }

    // Inventory

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        ItemTools.saveInventory(compound, "crafting", craftingMatrix);

        ItemTools.saveInventory(compound, "repair", repairInventory);

        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        ItemTools.readInventory(compound, "crafting", craftingMatrix);

        ItemTools.readInventory(compound, "repair", repairInventory);

        super.read(compound);
    }

    public void updateRecipe() {
        EntityGenericCar car = new EntityGenericCar(world);

        //Put keys in inventory
        car.setInventorySlotContents(0, ItemKey.getKeyForCar(car.getUniqueID()));
        car.setInventorySlotContents(1, ItemKey.getKeyForCar(car.getUniqueID()));

        car.setIsSpawned(false);

        List<ItemStack> partStacks = new ArrayList<>();

        for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
            ItemStack stack = craftingMatrix.getStackInSlot(i);

            if (!(stack.getItem() instanceof ICarPart)) {
                continue;
            }

            ICarPart itemCarPart = (ICarPart) stack.getItem();

            Part part = itemCarPart.getPart(stack);

            if (part == null) {
                continue;
            }

            partStacks.add(stack);
        }

        for (int i = 0; i < partStacks.size(); i++) {
            car.getPartInventory().setInventorySlotContents(i, partStacks.get(i).copy().split(1));
        }

        car.initParts();

        List<ITextComponent> messages = new ArrayList<>();

        boolean showable = PartRegistry.isValid(car, messages);

        this.messages = messages;

        if (!showable) {
            this.currentCraftingCar = null;
            return;
        }

        car.tryInitPartsAndModel();
        car.setPartSerializer();

        this.currentCraftingCar = car;
    }

    public void removeCraftItems() {
        for (int i = 0; i < craftingMatrix.getSizeInventory(); i++) {
            ItemStack stack = craftingMatrix.getStackInSlot(i);
            if (!ItemTools.isStackEmpty(stack)) {
                craftingMatrix.setInventorySlotContents(i, ItemTools.decrItemStack(stack, null));
            }
        }
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
    public boolean isUsableByPlayer(PlayerEntity player) {
        return craftingMatrix.isUsableByPlayer(player);
    }

    @Override
    public boolean isEmpty() {
        return craftingMatrix.isEmpty();
    }

    @Override
    public void openInventory(PlayerEntity player) {
        craftingMatrix.openInventory(player);
    }

    @Override
    public void closeInventory(PlayerEntity player) {
        craftingMatrix.openInventory(player);
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return craftingMatrix.isItemValidForSlot(index, stack);
    }

    @Override
    public void clear() {
        craftingMatrix.clear();
        updateRecipe();
    }

    public EntityGenericCar getCurrentCraftingCar() {
        return currentCraftingCar;
    }

    public boolean isCurrentCraftingCarValid() {
        return messages.size() <= 0;
    }

    public List<ITextComponent> getMessages() {
        return messages;
    }

    public void repairCar(PlayerEntity player) {
        if (!areBlocksAround()) {
            player.sendMessage(new TranslationTextComponent("message.incomplete_structure"));
            return;
        }

        if (!areRepairItemsInside()) {
            player.sendMessage(new TranslationTextComponent("message.no_repair_items"));
            return;
        }

        EntityCarBase carBase = getCarOnTop();

        if (!(carBase instanceof EntityCarDamageBase)) {
            player.sendMessage(new TranslationTextComponent("message.no_car"));
            return;
        }

        EntityCarDamageBase car = (EntityCarDamageBase) carBase;

        if (car.getDamage() <= 0) {
            return;
        }

        damageRepairItemsInside(player);

        car.setDamage(car.getDamage() - 10F);

        ModSounds.playSound(ModSounds.RATCHET, world, pos, null, SoundCategory.BLOCKS);
    }

    public boolean areRepairItemsInside() {
        for (int i = 0; i < repairInventory.getSizeInventory(); i++) {
            if (ItemTools.isStackEmpty(repairInventory.getStackInSlot(i))) {
                return false;
            }
        }

        return true;
    }

    public void damageRepairItemsInside(PlayerEntity player) {
        for (int i = 0; i < repairInventory.getSizeInventory(); i++) {
            ItemStack stack = repairInventory.getStackInSlot(i);
            if (!ItemTools.isStackEmpty(stack)) {
                stack.damageItem(10, player, playerEntity -> {
                });
            }
        }
    }

    @Override
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.car_workshop.name");
    }

    @Override
    public IIntArray getFields() {
        return new IntArray(0);
    }
}
