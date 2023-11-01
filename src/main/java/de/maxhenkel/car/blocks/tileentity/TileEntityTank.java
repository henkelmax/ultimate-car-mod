package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import javax.annotation.Nonnull;

public class TileEntityTank extends TileEntityBase implements IFluidHandler, ITickableBlockEntity {

    private FluidStack fluid;
    public static final int CAPACITY = 16000;

    public TileEntityTank(BlockPos pos, BlockState state) {
        super(Main.TANK_TILE_ENTITY_TYPE.get(), pos, state);
        this.fluid = FluidStack.EMPTY;
    }

    @Override
    public void tick() {
        if (!level.isClientSide) {
            if (level.getGameTime() % 20 == 0) {
                synchronize();
            }
        } else {
            updateClientSide();
        }

        if (fluid.isEmpty()) {
            return;
        }

        checkDown();

        for (Direction facing : Direction.Plane.HORIZONTAL) {
            if (fluid.isEmpty()) {
                return;
            }
            checkSide(facing);
        }
    }

    public void checkSide(Direction side) {
        BlockEntity te = level.getBlockEntity(worldPosition.relative(side));

        if (!(te instanceof TileEntityTank)) {
            return;
        }

        TileEntityTank otherTank = (TileEntityTank) te;

        FluidStack other = otherTank.getFluid();

        if (!(other.getFluid().equals(fluid.getFluid()) || other.isEmpty())) {
            return;
        }

        int dif = other.getAmount() - fluid.getAmount();

        if (dif >= -2) {
            return;
        }

        FluidUtil.tryFluidTransfer(otherTank, this, (-dif) / 2, true);
    }

    public void checkDown() {
        BlockEntity te = level.getBlockEntity(worldPosition.below());

        if (!(te instanceof TileEntityTank)) {
            return;
        }

        TileEntityTank otherTank = (TileEntityTank) te;

        FluidStack stack = FluidUtil.tryFluidTransfer(otherTank, this, Integer.MAX_VALUE, true);

        if (stack.isEmpty()) {
            return;
        }

        if (!fluid.isEmpty() && fluid.getAmount() <= 0) {
            fluid = FluidStack.EMPTY;
        }
    }

    public float getFillPercent() {
        if (fluid.isEmpty()) {
            return 0F;
        }

        return ((float) fluid.getAmount()) / ((float) CAPACITY);
    }

    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    protected void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        if (!fluid.isEmpty() && fluid.getAmount() > 0) {
            CompoundTag comp = new CompoundTag();

            fluid.writeToNBT(comp);

            compound.put("fluid", comp);
        }
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("fluid")) {
            CompoundTag comp = compound.getCompound("fluid");
            fluid = FluidStack.loadFluidStackFromNBT(comp);
        } else {
            fluid = FluidStack.EMPTY;
        }
        super.load(compound);
    }

    public void setFluid(FluidStack fluid) {
        this.fluid = fluid;
    }


    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.tank");
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
    }

    private boolean[] sides = new boolean[Direction.values().length];

    private boolean[] sidesFluid = new boolean[Direction.values().length];

    @OnlyIn(Dist.CLIENT)
    private void recalculateSides() {
        for (Direction facing : Direction.values()) {
            sides[facing.get3DDataValue()] = isTankConnectedCalc(facing);
            sidesFluid[facing.get3DDataValue()] = isFluidConnectedCalc(facing);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private boolean isFluidConnectedCalc(Direction facing) {
        BlockEntity te = level.getBlockEntity(worldPosition.relative(facing));
        if (te instanceof TileEntityTank) {
            TileEntityTank tank = (TileEntityTank) te;
            if (tank.fluid.isEmpty() || fluid.isEmpty()) {
                return false;
            }

            if (tank.fluid.getFluid().equals(fluid.getFluid())) {
                if (facing.equals(Direction.UP)) {
                    if (fluid.getAmount() >= CAPACITY) {
                        return true;
                    } else {
                        return false;
                    }
                }
                return true;
            }
        }

        BlockState s = level.getBlockState(worldPosition.relative(facing));
        if (s.isRedstoneConductor(level, worldPosition.relative(facing))) {
            if (facing.equals(Direction.UP)) {
                return fluid.getAmount() >= CAPACITY;
            }
            return true;
        }
        return false;
    }


    @OnlyIn(Dist.CLIENT)
    private boolean isTankConnectedCalc(Direction facing) {
        BlockEntity te = level.getBlockEntity(worldPosition.relative(facing));
        if (te instanceof TileEntityTank) {
            TileEntityTank tank = (TileEntityTank) te;
            if (tank.fluid.isEmpty() && fluid.isEmpty()) {
                return true;
            }

            if (tank.fluid.isEmpty() || fluid.isEmpty()) {
                return true;
            }

            return tank.fluid.getFluid().equals(fluid.getFluid());
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    private void updateClientSide() {
        recalculateSides();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isTankConnectedTo(Direction facing) {
        return sides[facing.get3DDataValue()];
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFluidConnected(Direction facing) {
        return sidesFluid[facing.get3DDataValue()];
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return fluid;
    }

    @Override
    public int getTankCapacity(int tank) {
        return CAPACITY;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return true;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (fluid.isEmpty()) {
            int amount = Math.min(resource.getAmount(), CAPACITY);

            if (action.execute()) {
                fluid = new FluidStack(resource.getFluid(), amount);
                setChanged();
            }

            return amount;
        } else if (resource.getFluid().equals(fluid.getFluid())) {
            int amount = Math.min(resource.getAmount(), CAPACITY - fluid.getAmount());

            if (action.execute()) {
                fluid.setAmount(fluid.getAmount() + amount);
                setChanged();
            }

            return amount;
        }
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (fluid.isEmpty()) {
            return FluidStack.EMPTY;
        }

        if (fluid.getFluid().equals(resource.getFluid())) {
            int amount = Math.min(resource.getAmount(), fluid.getAmount());

            Fluid f = fluid.getFluid();

            if (action.execute()) {
                fluid.setAmount(fluid.getAmount() - amount);
                if (fluid.getAmount() <= 0) {
                    fluid = FluidStack.EMPTY;
                }
                setChanged();
            }

            return new FluidStack(f, amount);
        }

        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (fluid.isEmpty()) {
            return FluidStack.EMPTY;
        }

        int amount = Math.min(maxDrain, fluid.getAmount());

        Fluid f = fluid.getFluid();

        if (action.execute()) {
            fluid.setAmount(fluid.getAmount() - amount);
            if (fluid.getAmount() <= 0) {
                fluid = FluidStack.EMPTY;
            }
            setChanged();
        }

        return new FluidStack(f, amount);
    }

}
