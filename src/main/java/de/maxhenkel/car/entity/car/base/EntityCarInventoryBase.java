package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.gui.ContainerCar;
import de.maxhenkel.car.gui.ContainerCarInventory;
import de.maxhenkel.car.items.ItemCanister;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.ContainerUser;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

import javax.annotation.Nullable;

public abstract class EntityCarInventoryBase extends EntityCarFuelBase implements Container {

    protected Container internalInventory;
    protected Container externalInventory;
    protected Container partInventory;

    protected FluidStack fluidInventory;

    private final SnapshotJournal<FluidStack> fluidJournal = new SnapshotJournal<>() {
        @Override
        protected FluidStack createSnapshot() {
            return fluidInventory.copy();
        }

        @Override
        protected void revertToSnapshot(FluidStack snapshot) {
            fluidInventory = snapshot.copy();
        }
    };

    public EntityCarInventoryBase(EntityType type, Level worldIn) {
        super(type, worldIn);

        internalInventory = new SimpleContainer(27);
        externalInventory = new SimpleContainer(0);
        partInventory = new SimpleContainer(15);
        fluidInventory = FluidStack.EMPTY;
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (canPlayerAccessInventoryExternal(player) && player.isShiftKeyDown()) {
            //Canister
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemCanister) {
                    boolean success = ItemCanister.fillCanister(ItemAccess.forPlayerInteraction(player, hand), this);
                    if (success) {
                        ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, level(), blockPosition(), null, SoundSource.BLOCKS);
                    }
                    return InteractionResult.CONSUME;
                }
                if (getFluidInventorySize() > 0) {
                    ItemAccess itemAccess = ItemAccess.forPlayerInteraction(player, hand);
                    ResourceHandler<FluidResource> capability = itemAccess.getCapability(Capabilities.Fluid.ITEM);
                    if (capability != null) {
                        try (Transaction transaction = Transaction.open(null)) {
                            ResourceHandler<FluidResource> handler = getInventoryFluidHandler();
                            if (ResourceHandlerUtil.isEmpty(capability)) {
                                ResourceHandlerUtil.move(handler, capability, f -> true, Integer.MAX_VALUE, transaction);
                            } else {
                                ResourceHandlerUtil.move(capability, handler, f -> true, Integer.MAX_VALUE, transaction);
                            }
                            transaction.commit();
                        }
                    }
                }
            }

            //Inv
            if (!level().isClientSide()) {
                if (externalInventory.getContainerSize() <= 0) {
                    openCarGUI(player);
                } else {
                    if (player instanceof ServerPlayer serverPlayer) {
                        serverPlayer.openMenu(new MenuProvider() {
                            @Override
                            public Component getDisplayName() {
                                return EntityCarInventoryBase.this.getDisplayName();
                            }

                            @Nullable
                            @Override
                            public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                                return new ContainerCarInventory(i, EntityCarInventoryBase.this, playerInventory);
                            }
                        }, packetBuffer -> packetBuffer.writeUUID(getUUID()));
                    }
                }
            }

            return InteractionResult.SUCCESS;
        }
        return super.interact(player, hand);
    }

    public abstract int getFluidInventorySize();

    public boolean canPlayerAccessInventoryExternal(Player player) {
        return true;
    }

    public Container getPartInventory() {
        return partInventory;
    }

    @Override
    public void destroyCar(Player player, boolean dropParts) {
        super.destroyCar(player, dropParts);

        Containers.dropContents(level(), this, this);
        Containers.dropContents(level(), this, externalInventory);
        if (dropParts) {
            Containers.dropContents(level(), this, partInventory);
        }
    }

    @Override
    public void openCarGUI(Player player) {
        super.openCarGUI(player);
        if (!level().isClientSide() && player instanceof ServerPlayer serverPlayer) {
            serverPlayer.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return EntityCarInventoryBase.this.getDisplayName();
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
                    return new ContainerCar(i, EntityCarInventoryBase.this, playerInventory);
                }
            }, packetBuffer -> packetBuffer.writeUUID(getUUID()));
        }
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        ItemUtils.readInventory(valueInput, "int_inventory", internalInventory);

        this.externalInventory = new SimpleContainer(valueInput.getIntOr("external_inventory_size", 0));
        ItemUtils.readInventory(valueInput, "external_inventory", externalInventory);

        ItemUtils.readInventory(valueInput, "parts", partInventory);

        valueInput.read("fluid_inventory", FluidStack.CODEC).ifPresent(stack -> fluidInventory = stack);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        ItemUtils.saveInventory(valueOutput, "int_inventory", internalInventory);

        valueOutput.putInt("external_inventory_size", externalInventory.getContainerSize());
        ItemUtils.saveInventory(valueOutput, "external_inventory", externalInventory);

        ItemUtils.saveInventory(valueOutput, "parts", partInventory);

        valueOutput.store("fluid_inventory", FluidStack.CODEC, fluidInventory);
    }

    public ResourceHandler<FluidResource> getInventoryFluidHandler() {
        return inventoryFluidHandler;
    }

    private final ResourceHandler<FluidResource> inventoryFluidHandler = new ResourceHandler<>() {

        @Override
        public int size() {
            return 1;
        }

        @Override
        public FluidResource getResource(int index) {
            return FluidResource.of(fluidInventory);
        }

        @Override
        public long getAmountAsLong(int index) {
            return fluidInventory.getAmount();
        }

        @Override
        public long getCapacityAsLong(int index, FluidResource resource) {
            return getFluidInventorySize();
        }

        @Override
        public boolean isValid(int index, FluidResource resource) {
            if (fluidInventory.isEmpty()) {
                return true;
            }
            if (!resource.is(fluidInventory.getFluid())) {
                return false;
            }
            return true;
        }

        @Override
        public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
            if (fluidInventory.isEmpty()) {
                int result = Math.min(amount, getFluidInventorySize());
                fluidJournal.updateSnapshots(transaction);
                fluidInventory = new FluidStack(resource.getFluid(), result);
                return result;
            } else if (resource.getFluid().equals(fluidInventory.getFluid())) {
                int result = Math.min(amount, getFluidInventorySize() - fluidInventory.getAmount());
                fluidJournal.updateSnapshots(transaction);
                fluidInventory.setAmount(fluidInventory.getAmount() + result);
                return result;
            }
            return 0;
        }

        @Override
        public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
            if (fluidInventory.isEmpty()) {
                return 0;
            }

            if (fluidInventory.is(resource.getFluid())) {
                int result = Math.min(amount, fluidInventory.getAmount());
                fluidJournal.updateSnapshots(transaction);
                fluidInventory.setAmount(fluidInventory.getAmount() - result);
                if (fluidInventory.getAmount() <= 0) {
                    fluidInventory = FluidStack.EMPTY;
                }

                return result;
            }

            return 0;
        }
    };

    @Override
    public int getContainerSize() {
        return internalInventory.getContainerSize();
    }

    @Override
    public ItemStack getItem(int index) {
        return internalInventory.getItem(index);
    }

    @Override
    public ItemStack removeItem(int index, int count) {
        return internalInventory.removeItem(index, count);
    }

    @Override
    public ItemStack removeItemNoUpdate(int index) {
        return internalInventory.removeItemNoUpdate(index);
    }

    @Override
    public void setItem(int index, ItemStack stack) {
        internalInventory.setItem(index, stack);
    }

    @Override
    public int getMaxStackSize() {
        return internalInventory.getMaxStackSize();
    }

    @Override
    public void setChanged() {
        internalInventory.setChanged();
    }

    @Override
    public boolean stillValid(Player player) {
        return internalInventory.stillValid(player);
    }

    @Override
    public boolean isEmpty() {
        return internalInventory.isEmpty();
    }

    @Override
    public void startOpen(ContainerUser user) {
        internalInventory.startOpen(user);
    }

    @Override
    public void stopOpen(ContainerUser user) {
        internalInventory.stopOpen(user);
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        return internalInventory.canPlaceItem(index, stack);
    }

    @Override
    public void clearContent() {
        internalInventory.clearContent();
    }

    public Container getExternalInventory() {
        return externalInventory;
    }

}
