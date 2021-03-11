package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.gui.ContainerCar;
import de.maxhenkel.car.gui.ContainerCarInventory;
import de.maxhenkel.car.items.ItemCanister;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.network.NetworkHooks;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class EntityCarInventoryBase extends EntityCarFuelBase implements IInventory {

    protected IInventory internalInventory;
    protected IInventory externalInventory;
    protected IInventory partInventory;

    protected FluidStack fluidInventory;

    public EntityCarInventoryBase(EntityType type, World worldIn) {
        super(type, worldIn);

        internalInventory = new Inventory(27);
        externalInventory = new Inventory(0);
        partInventory = new Inventory(15);
        fluidInventory = FluidStack.EMPTY;
    }

    @Override
    public ActionResultType interact(PlayerEntity player, Hand hand) {
        if (canPlayerAccessInventoryExternal(player) && player.isShiftKeyDown()) {
            //Canister
            ItemStack stack = player.getItemInHand(hand);
            if (!stack.isEmpty()) {
                if (stack.getItem() instanceof ItemCanister) {
                    boolean success = ItemCanister.fillCanister(stack, this);

                    if (success) {
                        ModSounds.playSound(SoundEvents.BREWING_STAND_BREW, level, blockPosition(), null, SoundCategory.BLOCKS);
                    }
                    return ActionResultType.CONSUME;
                }
                if (getFluidInventorySize() > 0) {
                    IFluidHandler handler = FluidUtil.getFluidHandler(stack).orElse(null);
                    if (handler != null) {
                        FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(FluidStack.EMPTY);

                        if (!fluidStack.isEmpty()) {
                            if (handleEmpty(stack, getInventoryFluidHandler(), player, hand)) {
                                return ActionResultType.CONSUME;
                            }
                        }

                        if (handleFill(stack, getInventoryFluidHandler(), player, hand)) {
                            return ActionResultType.CONSUME;
                        }
                    }
                }
            }

            //Inv
            if (!level.isClientSide) {
                if (externalInventory.getContainerSize() <= 0) {
                    openCarGUI(player);
                } else {
                    NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                        @Override
                        public ITextComponent getDisplayName() {
                            return EntityCarInventoryBase.this.getDisplayName();
                        }

                        @Nullable
                        @Override
                        public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                            return new ContainerCarInventory(i, EntityCarInventoryBase.this, playerInventory);
                        }
                    }, packetBuffer -> packetBuffer.writeUUID(getUUID()));
                }
            }

            return ActionResultType.SUCCESS;
        }
        return super.interact(player, hand);
    }

    public static boolean handleEmpty(ItemStack stack, IFluidHandler handler, PlayerEntity playerIn, Hand hand) {
        IItemHandler inv = new InvWrapper(playerIn.inventory);

        FluidActionResult res = FluidUtil.tryEmptyContainerAndStow(stack, handler, inv, Integer.MAX_VALUE, playerIn, true);

        if (res.isSuccess()) {
            playerIn.setItemInHand(hand, res.result);
            return true;
        }

        return false;
    }

    public static boolean handleFill(ItemStack stack, IFluidHandler handler, PlayerEntity playerIn, Hand hand) {
        IItemHandler inv = new InvWrapper(playerIn.inventory);

        FluidActionResult result = FluidUtil.tryFillContainerAndStow(stack, handler, inv, Integer.MAX_VALUE,
                playerIn, true);

        if (result.isSuccess()) {
            playerIn.setItemInHand(hand, result.result);
            return true;
        }

        return false;
    }

    public abstract int getFluidInventorySize();

    public boolean canPlayerAccessInventoryExternal(PlayerEntity player) {
        return true;
    }

    public IInventory getPartInventory() {
        return partInventory;
    }

    @Override
    public void destroyCar(PlayerEntity player, boolean dropParts) {
        super.destroyCar(player, dropParts);

        InventoryHelper.dropContents(level, this, this);
        InventoryHelper.dropContents(level, this, externalInventory);
        if (dropParts) {
            InventoryHelper.dropContents(level, this, partInventory);
        }
    }

    @Override
    public void openCarGUI(PlayerEntity player) {
        super.openCarGUI(player);
        if (!level.isClientSide && player instanceof ServerPlayerEntity) {
            NetworkHooks.openGui((ServerPlayerEntity) player, new INamedContainerProvider() {
                @Override
                public ITextComponent getDisplayName() {
                    return EntityCarInventoryBase.this.getDisplayName();
                }

                @Nullable
                @Override
                public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
                    return new ContainerCar(i, EntityCarInventoryBase.this, playerInventory);
                }
            }, packetBuffer -> packetBuffer.writeUUID(getUUID()));
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundNBT compound) {
        super.readAdditionalSaveData(compound);
        ItemUtils.readInventory(compound, "int_inventory", internalInventory);

        this.externalInventory = new Inventory(compound.getInt("external_inventory_size"));
        ItemUtils.readInventory(compound, "external_inventory", externalInventory);

        ItemUtils.readInventory(compound, "parts", partInventory);

        if (compound.contains("fluid_inventory")) {
            fluidInventory = FluidStack.loadFluidStackFromNBT(compound.getCompound("fluid_inventory"));
        }
    }

    @Override
    protected void addAdditionalSaveData(CompoundNBT compound) {
        super.addAdditionalSaveData(compound);

        ItemUtils.saveInventory(compound, "int_inventory", internalInventory);

        compound.putInt("external_inventory_size", externalInventory.getContainerSize());
        ItemUtils.saveInventory(compound, "external_inventory", externalInventory);

        ItemUtils.saveInventory(compound, "parts", partInventory);

        if (!fluidInventory.isEmpty()) {
            compound.put("fluid_inventory", fluidInventory.writeToNBT(new CompoundNBT()));
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
    public boolean stillValid(PlayerEntity player) {
        return internalInventory.stillValid(player);
    }

    @Override
    public boolean isEmpty() {
        return internalInventory.isEmpty();
    }

    @Override
    public void startOpen(PlayerEntity player) {
        internalInventory.startOpen(player);
    }

    @Override
    public void stopOpen(PlayerEntity player) {
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

    public IInventory getExternalInventory() {
        return externalInventory;
    }

}
