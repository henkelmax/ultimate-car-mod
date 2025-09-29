package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.BlockCarWorkshopOutter;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.car.entity.car.base.EntityCarDamageBase;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.car.entity.car.parts.PartRegistry;
import de.maxhenkel.car.items.ICarPart;
import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TileEntityCarWorkshop extends TileEntityBase implements Container {

    private SimpleContainer craftingMatrix;
    private SimpleContainer repairInventory;
    private EntityGenericCar currentCraftingCar;
    private List<Component> messages;

    public TileEntityCarWorkshop(BlockPos pos, BlockState state) {
        super(CarMod.CAR_WORKSHOP_TILE_ENTITY_TYPE.get(), pos, state);
        this.craftingMatrix = new SimpleContainer(15);
        this.repairInventory = new SimpleContainer(3);
        this.messages = new ArrayList<>();
    }

    public SimpleContainer getRepairInventory() {
        return repairInventory;
    }

    public EntityGenericCar getCarOnTop() {
        BlockState ownState = level.getBlockState(worldPosition);

        if (!ownState.getBlock().equals(ModBlocks.CAR_WORKSHOP.get())) {
            return null;
        }

        BlockPos start = worldPosition.relative(Direction.UP);

        AABB aabb = new AABB(start.getX(), start.getY(), start.getZ(), start.getX() + 1,
                start.getY() + 1, start.getZ() + 1);

        List<EntityGenericCar> cars = level.getEntitiesOfClass(EntityGenericCar.class, aabb);
        if (cars.isEmpty()) {
            return null;
        }

        return cars.get(0);
    }

    public void spawnCar(ServerPlayer player) {
        if (!areBlocksAround()) {
            player.sendSystemMessage(Component.translatable("message.incomplete_structure"));
            return;
        }

        if (!isTopFree()) {
            player.sendSystemMessage(Component.translatable("message.blocks_on_top"));
            return;
        }

        updateRecipe();

        EntityGenericCar car = currentCraftingCar;

        if (car == null || !isCurrentCraftingCarValid()) {
            player.sendSystemMessage(Component.translatable("message.no_recipe"));
            return;
        }
        BlockPos spawnPos = worldPosition.above();
        car.setPos(spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5);

        removeCraftItems();
        car.setFuelAmount(100);
        level.addFreshEntity(car);
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
        level.setBlockAndUpdate(worldPosition.offset(0, 0, -1), getState(1));
        level.setBlockAndUpdate(worldPosition.offset(1, 0, -1), getState(2));
        level.setBlockAndUpdate(worldPosition.offset(1, 0, 0), getState(3));
        level.setBlockAndUpdate(worldPosition.offset(1, 0, 1), getState(4));
        level.setBlockAndUpdate(worldPosition.offset(0, 0, 1), getState(5));
        level.setBlockAndUpdate(worldPosition.offset(-1, 0, 1), getState(6));
        level.setBlockAndUpdate(worldPosition.offset(-1, 0, 0), getState(7));
        level.setBlockAndUpdate(worldPosition.offset(-1, 0, -1), getState(8));

        setOwnBlockValid(true);
    }

    private void setOwnBlockValid(boolean valid) {
        BlockState state = level.getBlockState(worldPosition);
        if (!state.getBlock().equals(ModBlocks.CAR_WORKSHOP.get())) {
            return;
        }
        ModBlocks.CAR_WORKSHOP.get().setValid(level, worldPosition, state, valid);
    }

    private BlockState getState(int meta) {
        return ModBlocks.CAR_WORKSHOP_OUTTER.get().defaultBlockState().setValue(BlockCarWorkshopOutter.POSITION, meta);
    }

    public boolean areBlocksAround() {
        if (!checkSideBlock(worldPosition.offset(0, 0, 1))) {
            return false;
        }
        if (!checkSideBlock(worldPosition.offset(1, 0, 0))) {
            return false;
        }
        if (!checkSideBlock(worldPosition.offset(1, 0, 1))) {
            return false;
        }
        if (!checkSideBlock(worldPosition.offset(0, 0, -1))) {
            return false;
        }
        if (!checkSideBlock(worldPosition.offset(-1, 0, 0))) {
            return false;
        }
        if (!checkSideBlock(worldPosition.offset(-1, 0, -1))) {
            return false;
        }
        if (!checkSideBlock(worldPosition.offset(-1, 0, 1))) {
            return false;
        }
        if (!checkSideBlock(worldPosition.offset(1, 0, -1))) {
            return false;
        }
        return true;
    }

    public boolean isTopFree() {
        for (int x = -1; x <= 1; x++) {
            for (int y = 1; y <= 2; y++) {
                for (int z = -1; z <= 1; z++) {
                    if (!checkBlockAir(worldPosition.offset(x, y, z))) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    private boolean checkBlockAir(BlockPos p) {
        return level.isEmptyBlock(p);
    }

    private boolean checkSideBlock(BlockPos p) {
        return level.getBlockState(p).getBlock().equals(ModBlocks.CAR_WORKSHOP_OUTTER.get());
    }

    @Override
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        ItemUtils.saveInventory(valueOutput, "crafting", craftingMatrix);
        ItemUtils.saveInventory(valueOutput, "repair", repairInventory);
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        ItemUtils.readInventory(valueInput, "crafting", craftingMatrix);
        ItemUtils.readInventory(valueInput, "repair", repairInventory);
        super.loadAdditional(valueInput);
    }

    public void updateRecipe() {
        List<ItemStack> items = new ArrayList<>();

        for (int i = 0; i < craftingMatrix.getContainerSize(); i++) {
            ItemStack stack = craftingMatrix.getItem(i);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ICarPart) {
                    if (((ICarPart) stack.getItem()).getPart(stack) != null) {
                        items.add(stack);
                    }
                } else {
                    currentCraftingCar = null;
                    messages = Arrays.asList(Component.translatable("message.parts.no_car_part", stack.getHoverName()));
                    return;
                }
            }
        }

        List<Component> messages = new ArrayList<>();

        currentCraftingCar = createCar(level, items, messages);

        this.messages = messages;
    }

    public static EntityGenericCar createCar(Level world, List<ItemStack> partStacks, List<Component> messages) {
        EntityGenericCar car = new EntityGenericCar(world);

        //Put keys in inventory
        car.setItem(0, ItemKey.getKeyForCar(car.getUUID()));
        car.setItem(1, ItemKey.getKeyForCar(car.getUUID()));

        car.setIsSpawned(false);


        for (int i = 0; i < partStacks.size(); i++) {
            car.getPartInventory().setItem(i, partStacks.get(i).copy().split(1));
        }

        car.initParts();

        boolean showable = PartRegistry.isValid(car, messages);

        if (!showable) {
            return null;
        }

        car.tryInitPartsAndModel();
        car.setPartSerializer();

        return car;
    }

    public static EntityGenericCar createCar(Level world, List<ItemStack> partStacks) {
        return createCar(world, partStacks, new ArrayList<>());
    }

    public void removeCraftItems() {
        for (int i = 0; i < craftingMatrix.getContainerSize(); i++) {
            ItemStack stack = craftingMatrix.getItem(i);
            if (!stack.isEmpty()) {
                stack.shrink(1);
            }
        }
    }

    @Override
    public int getContainerSize() {
        return craftingMatrix.getContainerSize();
    }

    @Override
    public ItemStack getItem(int index) {
        return craftingMatrix.getItem(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        ItemStack stack = craftingMatrix.removeItem(index, count);
        updateRecipe();
        return stack;
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        ItemStack stack = craftingMatrix.removeItemNoUpdate(index);
        updateRecipe();
        return stack;
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        craftingMatrix.setItem(index, stack);
        updateRecipe();
    }

    @Override
    public int getMaxStackSize() {
        return craftingMatrix.getMaxStackSize();
    }

    @Override
    public boolean stillValid(Player player) {
        return craftingMatrix.stillValid(player);
    }

    @Override
    public boolean isEmpty() {
        return craftingMatrix.isEmpty();
    }


    @Override
    public void startOpen(ContainerUser user) {
        craftingMatrix.startOpen(user);
    }

    @Override
    public void stopOpen(ContainerUser user) {
        craftingMatrix.stopOpen(user);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return craftingMatrix.canPlaceItem(index, stack);
    }

    @Override
    public void clearContent() {
        craftingMatrix.clearContent();
        updateRecipe();
    }

    public EntityGenericCar getCurrentCraftingCar() {
        return currentCraftingCar;
    }

    public boolean isCurrentCraftingCarValid() {
        return messages.size() <= 0;
    }

    public List<Component> getMessages() {
        return messages;
    }

    public void repairCar(ServerPlayer player) {
        if (!areBlocksAround()) {
            player.sendSystemMessage(Component.translatable("message.incomplete_structure"));
            return;
        }

        if (!areRepairItemsInside()) {
            player.sendSystemMessage(Component.translatable("message.no_repair_items"));
            return;
        }

        EntityCarBase carBase = getCarOnTop();

        if (!(carBase instanceof EntityCarDamageBase)) {
            player.sendSystemMessage(Component.translatable("message.no_car"));
            return;
        }

        EntityCarDamageBase car = (EntityCarDamageBase) carBase;

        if (car.getDamage() <= 0) {
            return;
        }

        damageRepairItemsInside(player);

        car.setDamage(car.getDamage() - 10F);

        ModSounds.playSound(ModSounds.RATCHET.get(), level, worldPosition, null, SoundSource.BLOCKS);
    }

    public boolean areRepairItemsInside() {
        for (int i = 0; i < repairInventory.getContainerSize(); i++) {
            if (repairInventory.getItem(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public void damageRepairItemsInside(Player player) {
        for (int i = 0; i < repairInventory.getContainerSize(); i++) {
            ItemStack stack = repairInventory.getItem(i);
            if (!stack.isEmpty()) {
                if (player.level() instanceof ServerLevel serverLevel) {
                    stack.hurtAndBreak(10, serverLevel, player, item -> {

                    });
                }

            }
        }
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable(ModBlocks.CAR_WORKSHOP.get().getDescriptionId());
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
    }

}
