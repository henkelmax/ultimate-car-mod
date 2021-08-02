package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.gui.ContainerCar;
import de.maxhenkel.car.gui.ContainerCarInventory;
import de.maxhenkel.car.items.ItemCanister;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.*;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fmllegacy.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class EntityCarInventoryBase extends EntityCarFuelBase implements Container {

    protected Container internalInventory;
    protected Container externalInventory;
    protected Container partInventory;

    protected FluidStack fluidInventory;

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
                    boolean success = ItemCanister.fillCanister(stack, this);

                    if (success) {
                        ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, level, blockPosition(), null, SoundSource.BLOCKS);
                    }
                    return InteractionResult.CONSUME;
                }
                if (getFluidInventorySize() > 0) {
                    IFluidHandler handler = FluidUtil.getFluidHandler(stack).orElse(null);
                    if (handler != null) {
                        FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(FluidStack.EMPTY);

                        if (!fluidStack.isEmpty()) {
                            if (handleEmpty(stack, getInventoryFluidHandler(), player, hand)) {
                                return InteractionResult.CONSUME;
                            }
                        }

                        if (handleFill(stack, getInventoryFluidHandler(), player, hand)) {
                            return InteractionResult.CONSUME;
                        }
                    }
                }
            }

            //Inv
            if (!level.isClientSide) {
                if (externalInventory.getContainerSize() <= 0) {
                    openCarGUI(player);
                } else {
                    NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
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

            return InteractionResult.SUCCESS;
        }
        return super.interact(player, hand);
    }

    public static boolean handleEmpty(ItemStack stack, IFluidHandler handler, Player playerIn, InteractionHand hand) {
        IItemHandler inv = new InvWrapper(playerIn.getInventory());

        FluidActionResult res = FluidUtil.tryEmptyContainerAndStow(stack, handler, inv, Integer.MAX_VALUE, playerIn, true);

        if (res.isSuccess()) {
            playerIn.setItemInHand(hand, res.result);
            return true;
        }

        return false;
    }

    public static boolean handleFill(ItemStack stack, IFluidHandler handler, Player playerIn, InteractionHand hand) {
        IItemHandler inv = new InvWrapper(playerIn.getInventory());

        FluidActionResult result = FluidUtil.tryFillContainerAndStow(stack, handler, inv, Integer.MAX_VALUE,
                playerIn, true);

        if (result.isSuccess()) {
            playerIn.setItemInHand(hand, result.result);
            return true;
        }

        return false;
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

        Containers.dropContents(level, this, this);
        Containers.dropContents(level, this, externalInventory);
        if (dropParts) {
            Containers.dropContents(level, this, partInventory);
        }
    }

    @Override
    public void openCarGUI(Player player) {
        super.openCarGUI(player);
        if (!level.isClientSide && player instanceof ServerPlayer) {
            NetworkHooks.openGui((ServerPlayer) player, new MenuProvider() {
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
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        ItemUtils.readInventory(compound, "int_inventory", internalInventory);

        this.externalInventory = new SimpleContainer(compound.getInt("external_inventory_size"));
        ItemUtils.readInventory(compound, "external_inventory", externalInventory);

        ItemUtils.readInventory(compound, "parts", partInventory);

        if (compound.contains("fluid_inventory")) {
            fluidInventory = FluidStack.loadFluidStackFromNBT(compound.getCompound("fluid_inventory"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);

        ItemUtils.saveInventory(compound, "int_inventory", internalInventory);

        compound.putInt("external_inventory_size", externalInventory.getContainerSize());
        ItemUtils.saveInventory(compound, "external_inventory", externalInventory);

        ItemUtils.saveInventory(compound, "parts", partInventory);

        if (!fluidInventory.isEmpty()) {
            compound.put("fluid_inventory", fluidInventory.writeToNBT(new CompoundTag()));
        }
    }

    public IFluidHandler getInventoryFluidHandler() {
        return inventoryFluidHandler;
    }

    private IFluidHandler inventoryFluidHandler = new IFluidHandler() {
        @Override
        public int getTanks() {
            return 1;
        }

        @Nonnull
        @Override
        public FluidStack getFluidInTank(int tank) {
            return fluidInventory;
        }

        @Override
        public int getTankCapacity(int tank) {
            return getFluidInventorySize();
        }

        @Override
        public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
            return true;
        }

        @Override
        public int fill(FluidStack resource, FluidAction action) {
            if (fluidInventory.isEmpty()) {
                int amount = Math.min(resource.getAmount(), getFluidInventorySize());

                if (action.execute()) {
                    fluidInventory = new FluidStack(resource.getFluid(), amount);
                }

                return amount;
            } else if (resource.getFluid().equals(fluidInventory.getFluid())) {
                int amount = Math.min(resource.getAmount(), getFluidInventorySize() - fluidInventory.getAmount());

                if (action.execute()) {
                    fluidInventory.setAmount(fluidInventory.getAmount() + amount);
                }

                return amount;
            }
            return 0;
        }

        @Nonnull
        @Override
        public FluidStack drain(FluidStack resource, FluidAction action) {
            if (fluidInventory.isEmpty()) {
                return FluidStack.EMPTY;
            }

            if (fluidInventory.getFluid().equals(resource.getFluid())) {
                int amount = Math.min(resource.getAmount(), fluidInventory.getAmount());

                Fluid f = fluidInventory.getFluid();

                if (action.execute()) {
                    fluidInventory.setAmount(fluidInventory.getAmount() - amount);
                    if (fluidInventory.getAmount() <= 0) {
                        fluidInventory = FluidStack.EMPTY;
                    }
                }

                return new FluidStack(f, amount);
            }

            return FluidStack.EMPTY;
        }

        @Nonnull
        @Override
        public FluidStack drain(int maxDrain, FluidAction action) {
            if (fluidInventory.isEmpty()) {
                return FluidStack.EMPTY;
            }

            int amount = Math.min(maxDrain, fluidInventory.getAmount());

            Fluid f = fluidInventory.getFluid();

            if (action.execute()) {
                fluidInventory.setAmount(fluidInventory.getAmount() - amount);
                if (fluidInventory.getAmount() <= 0) {
                    fluidInventory = FluidStack.EMPTY;
                }
            }

            return new FluidStack(f, amount);
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
    public void startOpen(Player player) {
        internalInventory.startOpen(player);
    }

    @Override
    public void stopOpen(Player player) {
        internalInventory.stopOpen(player);
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
