package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.BlockGasStation;
import de.maxhenkel.car.blocks.BlockGasStationTop;
import de.maxhenkel.car.blocks.BlockOrientableHorizontal;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.net.MessageStartFuel;
import de.maxhenkel.car.sounds.ModClientSounds;
import de.maxhenkel.car.sounds.SoundLoopTileentity.ISoundLoopable;
import de.maxhenkel.corelib.CachedValue;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.core.*;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.permissions.Permissions;
import net.minecraft.util.Util;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.client.network.ClientPacketDistributor;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import javax.annotation.Nullable;
import java.util.*;

public class TileEntityGasStation extends TileEntityBase implements ITickableBlockEntity, ResourceHandler<FluidResource>, ISoundLoopable {

    private FluidStack storage;

    public int maxStorageAmount = 16000;

    private final int transferRate;

    private int fuelCounter;
    private boolean isFueling;
    private boolean wasFueling;

    private SimpleContainer inventory;
    private SimpleContainer trading;
    private int tradeAmount;

    private int freeAmountLeft;

    private UUID owner;

    @Nullable
    private ResourceHandler<FluidResource> fluidHandlerInFront;

    private final SnapshotJournal<FluidStack> fluidJournal = new SnapshotJournal<>() {
        @Override
        protected FluidStack createSnapshot() {
            return storage.copy();
        }

        @Override
        protected void revertToSnapshot(FluidStack snapshot) {
            storage = snapshot.copy();
            setChanged();
        }
    };

    public TileEntityGasStation(BlockPos pos, BlockState state) {
        super(CarMod.GAS_STATION_TILE_ENTITY_TYPE.get(), pos, state);
        this.transferRate = CarMod.SERVER_CONFIG.gasStationTransferRate.get();
        this.fuelCounter = 0;
        this.inventory = new SimpleContainer(27);
        this.trading = new SimpleContainer(2);
        this.owner = new UUID(0L, 0L);
        this.storage = FluidStack.EMPTY;
        this.tradeAmount = 1000;
    }

    public final ContainerData FIELDS = new ContainerData() {
        @Override
        public int get(int index) {
            switch (index) {
                case 0:
                    return fuelCounter;
                case 1:
                    if (!storage.isEmpty()) {
                        return storage.getAmount();
                    }
                    return 0;
                case 2:
                    return tradeAmount;
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0:
                    fuelCounter = value;
                    break;
                case 1:
                    if (!storage.isEmpty()) {
                        storage.setAmount(value);
                    }
                    break;
                case 2:
                    tradeAmount = value;
                    setChanged();
                    break;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.fuel_station");
    }

    private void fixTop() {
        BlockState top = level.getBlockState(worldPosition.above());
        BlockState bottom = getBlockState();
        Direction facing = bottom.getValue(BlockOrientableHorizontal.FACING);
        if (top.getBlock().equals(ModBlocks.GAS_STATION_TOP.get())) {
            if (!top.getValue(BlockGasStationTop.FACING).equals(facing)) {
                level.setBlockAndUpdate(worldPosition.above(), ModBlocks.GAS_STATION_TOP.get().defaultBlockState().setValue(BlockGasStationTop.FACING, facing));
            }
        } else if (level.isEmptyBlock(worldPosition.above())) {
            level.setBlockAndUpdate(worldPosition.above(), ModBlocks.GAS_STATION_TOP.get().defaultBlockState().setValue(BlockGasStationTop.FACING, facing));
        }

    }

    @Override
    public void tick() {
        if (level.getGameTime() % 100 == 0) {
            fixTop();
        }

        fluidHandlerInFront = searchFluidHandlerInFront();

        if (fluidHandlerInFront == null) {
            if (fuelCounter > 0 || isFueling) {
                fuelCounter = 0;
                isFueling = false;
                synchronize();
                setChanged();
            }
            return;
        }

        if (!isFueling) {
            return;
        }

        if (storage.isEmpty()) {
            return;
        }

        int moved;
        try (Transaction transaction = Transaction.open(null)) {
            moved = ResourceHandlerUtil.move(this, fluidHandlerInFront, resource -> true, transferRate, transaction);
        }
        int amountCarCanTake = 0;
        if (moved > 0) {
            amountCarCanTake = moved;
        }

        if (amountCarCanTake <= 0) {
            return;
        }

        if (freeAmountLeft <= 0) {
            if (tradeAmount <= 0) {
                freeAmountLeft = transferRate;
                setChanged();
            } else if (removeTradeItem()) {
                freeAmountLeft = tradeAmount;
                setChanged();
            } else {
                isFueling = false;
                synchronize();
                return;
            }
        }

        int result;
        try (Transaction transaction = Transaction.open(null)) {
            result = ResourceHandlerUtil.move(this, fluidHandlerInFront, resource -> true, Math.min(transferRate, freeAmountLeft), transaction);
            transaction.commit();
        }

        if (result > 0) {
            fuelCounter += result;
            freeAmountLeft -= result;
            synchronize(100);

            setChanged();
            if (!wasFueling) {
                synchronize();
            }
            wasFueling = true;
        } else {
            if (wasFueling) {
                synchronize();
            }
            wasFueling = false;
        }
    }

    /**
     * @return true if the item was successfully removed
     */
    public boolean removeTradeItem() {
        ItemStack tradeTemplate = trading.getItem(0);
        ItemStack tradingStack = trading.getItem(1);

        if (tradeTemplate.isEmpty()) {
            return true;
        }

        if (tradingStack.isEmpty()) {
            return false;
        }

        if (!tradeTemplate.getItem().equals(tradingStack.getItem())) {
            return false;
        }

        if (tradeTemplate.getDamageValue() != tradingStack.getDamageValue()) {
            return false;
        }

        if (tradingStack.getCount() < tradeTemplate.getCount()) {
            return false;
        }

        ItemStack addStack = tradingStack.copy();
        addStack.setCount(tradeTemplate.getCount());
        ItemStack add = inventory.addItem(addStack);
        if (add.getCount() > 0) {
            return false;
        }
        tradingStack.setCount(tradingStack.getCount() - tradeTemplate.getCount());
        trading.setItem(1, tradingStack);

        return true;
    }

    public Container getInventory() {
        return inventory;
    }

    public Container getTradingInventory() {
        return trading;
    }

    public boolean isValidFluid(Fluid f) {
        return CarMod.SERVER_CONFIG.gasStationValidFuelList.stream().anyMatch(fluidTag -> fluidTag.contains(f));
    }

    public void setOwner(UUID owner) {
        this.owner = owner;
        setChanged();
    }

    public void setOwner(Player player) {
        this.owner = new UUID(player.getUUID().getMostSignificantBits(), player.getUUID().getLeastSignificantBits());
        setChanged();
    }

    /**
     * OPs are also owners
     */
    public boolean isOwner(Player player) {
        if (player instanceof ServerPlayer p) {

            boolean isOp = p.permissions().hasPermission(Permissions.COMMANDS_ADMIN);
            if (isOp) {
                return true;
            }
        }
        return player.getUUID().equals(owner);
    }

    public boolean hasTrade() {
        return !trading.getItem(0).isEmpty();
    }

    @Override
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        valueOutput.putInt("counter", fuelCounter);

        if (!storage.isEmpty()) {
            valueOutput.store("fluid", FluidStack.CODEC, storage);
        }

        ItemUtils.saveInventory(valueOutput, "inventory", inventory);
        ItemUtils.saveInventory(valueOutput, "trading", inventory);

        valueOutput.putInt("trade_amount", tradeAmount);
        valueOutput.putInt("free_amount", freeAmountLeft);

        valueOutput.store("owner", UUIDUtil.CODEC, owner);
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        fuelCounter = valueInput.getIntOr("counter", 0);

        storage = valueInput.read("fluid", FluidStack.CODEC).orElse(FluidStack.EMPTY);

        ItemUtils.readInventory(valueInput, "inventory", inventory);
        ItemUtils.readInventory(valueInput, "trading", trading);

        tradeAmount = valueInput.getIntOr("trade_amount", 1000);
        freeAmountLeft = valueInput.getIntOr("free_amount", 0);

        owner = valueInput.read("owner", UUIDUtil.CODEC).orElse(Util.NIL_UUID);
        super.loadAdditional(valueInput);
    }

    public boolean isFueling() {
        return isFueling;
    }

    public int getFuelCounter() {
        return this.fuelCounter;
    }

    public void setStorage(FluidStack storage) {
        this.storage = storage;
        setChanged();
        synchronize();
    }

    public FluidStack getStorage() {
        return storage;
    }

    public void setFuelCounter(int fuelCounter) {
        this.fuelCounter = fuelCounter;
        setChanged();
        synchronize();
    }

    public void setFueling(boolean isFueling) {
        if (fluidHandlerInFront == null) {
            return;
        }

        if (isFueling && !this.isFueling) {
            if (level.isClientSide()) {
                ModClientSounds.playGasStationSound(this);
            }
        }
        this.isFueling = isFueling;
        synchronize();
    }

    public Component getRenderText() {
        if (fluidHandlerInFront == null) {
            return Component.translatable("gas_station.no_vehicle");
        } else if (fuelCounter <= 0) {
            return Component.translatable("gas_station.ready");
        } else {
            return Component.translatable("gas_station.fuel_amount", fuelCounter);
        }
    }

    private CachedValue<Vec3> center = new CachedValue<>(() -> new Vec3(worldPosition.getX() + 0.5D, worldPosition.getY() + 1.5D, worldPosition.getZ() + 0.5D));

    @Nullable
    private ResourceHandler<FluidResource> searchFluidHandlerInFront() {
        if (level == null) {
            return null;
        }
        return level.getEntitiesOfClass(Entity.class, getDetectionBox())
                .stream()
                .sorted(Comparator.comparingDouble(o -> o.distanceToSqr(center.get())))
                .map(entity -> entity.getCapability(Capabilities.Fluid.ENTITY, null))
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    public ResourceHandler<FluidResource> getFluidHandlerInFront() {
        return fluidHandlerInFront;
    }

    private CachedValue<AABB> detectionBox = new CachedValue<>(this::createDetectionBox);

    private AABB createDetectionBox() {
        BlockState ownState = level.getBlockState(worldPosition);

        if (!ownState.getBlock().equals(ModBlocks.GAS_STATION.get())) {
            return null;
        }
        Direction facing = ownState.getValue(BlockGasStation.FACING);
        BlockPos start = worldPosition.relative(facing);
        return new AABB(start.getX(), start.getY(), start.getZ(), start.getX() + 1D, start.getY() + 2.5D, start.getZ() + 1D)
                .expandTowards(facing.getStepX(), 0D, facing.getStepZ())
                .inflate(facing.getStepX() == 0 ? 0.5D : 0D, 0D, facing.getStepZ() == 0 ? 0.5D : 0D);
    }

    public AABB getDetectionBox() {
        return detectionBox.get();
    }

    public boolean canEntityBeFueled() {
        if (fluidHandlerInFront == null) {
            return false;
        }
        try (Transaction transaction = Transaction.open(null)) {
            int moved = ResourceHandlerUtil.move(this, fluidHandlerInFront, resource -> true, transferRate, transaction);
            return moved > 0;
        }
    }

    public Direction getDirection() {
        return getBlockState().getValue(BlockGasStation.FACING);
    }

    public void sendStartFuelPacket(boolean start) {
        if (level.isClientSide()) {
            ClientPacketDistributor.sendToServer(new MessageStartFuel(worldPosition, start));
        }
    }

    @Override
    public boolean shouldSoundBePlayed() {
        if (!isFueling) {
            return false;
        }

        return canEntityBeFueled();
    }

    @Override
    public void play() {

    }

    public AABB getRenderBoundingBox() {
        return new AABB(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(), worldPosition.getX() + 1, worldPosition.getY() + 2, worldPosition.getZ() + 1);
    }

    public int getTradeAmount() {
        return tradeAmount;
    }

    public void setTradeAmount(int tradeAmount) {
        this.tradeAmount = tradeAmount;
        synchronize();
    }

    public int getFuelAmount() {
        if (!storage.isEmpty()) {
            return storage.getAmount();
        }
        return 0;
    }

    @Override
    public ContainerData getFields() {
        return FIELDS;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidResource getResource(int index) {
        return FluidResource.of(storage);
    }

    @Override
    public long getAmountAsLong(int index) {
        return storage.getAmount();
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return maxStorageAmount;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return isValidFluid(resource.getFluid());
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (!isValidFluid(resource.getFluid())) {
            return 0;
        }

        if (storage.isEmpty()) {
            int result = Math.min(amount, maxStorageAmount);

            fluidJournal.updateSnapshots(transaction);
            storage = new FluidStack(resource.getFluid(), result);
            synchronize();
            setChanged();

            return result;
        } else if (resource.is(storage.getFluid())) {
            int result = Math.min(amount, maxStorageAmount - storage.getAmount());

            fluidJournal.updateSnapshots(transaction);
            storage.setAmount(storage.getAmount() + result);
            synchronize();
            setChanged();

            return result;
        }
        return 0;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (storage.isEmpty()) {
            return 0;
        }

        if (storage.getFluid().equals(resource.getFluid())) {
            int result = Math.min(amount, storage.getAmount());

            fluidJournal.updateSnapshots(transaction);
            storage.setAmount(storage.getAmount() - result);
            if (storage.getAmount() <= 0) {
                storage = FluidStack.EMPTY;
                synchronize();
            }

            setChanged();

            return result;
        }

        return 0;
    }

    @Override
    public ResourceHandler<FluidResource> getFluidHandler() {
        return this;
    }

}
