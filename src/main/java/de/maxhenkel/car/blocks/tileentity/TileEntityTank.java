package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.transaction.SnapshotJournal;
import net.neoforged.neoforge.transfer.transaction.TransactionContext;

public class TileEntityTank extends TileEntityBase implements ResourceHandler<FluidResource>, ITickableBlockEntity {

    private FluidStack fluid;
    public static final int CAPACITY = 16000;

    private final SnapshotJournal<FluidStack> fluidJournal = new SnapshotJournal<>() {
        @Override
        protected FluidStack createSnapshot() {
            return fluid.copy();
        }

        @Override
        protected void revertToSnapshot(FluidStack snapshot) {
            fluid = snapshot.copy();
            setChanged();
        }
    };

    public TileEntityTank(BlockPos pos, BlockState state) {
        super(CarMod.TANK_TILE_ENTITY_TYPE.get(), pos, state);
        this.fluid = FluidStack.EMPTY;
    }

    @Override
    public void tick() {
        if (!level.isClientSide()) {
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

        ResourceHandlerUtil.move(this, otherTank, resource -> true, (-dif) / 2, null);
    }

    public void checkDown() {
        BlockEntity te = level.getBlockEntity(worldPosition.below());

        if (!(te instanceof TileEntityTank otherTank)) {
            return;
        }

        int moved = ResourceHandlerUtil.move(this, otherTank, resource -> true, Integer.MAX_VALUE, null);

        if (moved <= 0) {
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
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (!fluid.isEmpty() && fluid.getAmount() > 0) {
            valueOutput.store("fluid", FluidStack.CODEC, fluid);
        }
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        fluid = valueInput.read("fluid", FluidStack.CODEC).orElse(FluidStack.EMPTY);
        super.loadAdditional(valueInput);
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

    private void recalculateSides() {
        for (Direction facing : Direction.values()) {
            sides[facing.get3DDataValue()] = isTankConnectedCalc(facing);
            sidesFluid[facing.get3DDataValue()] = isFluidConnectedCalc(facing);
        }
    }

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

    private boolean isTankConnectedCalc(Direction facing) {
        BlockEntity te = level.getBlockEntity(worldPosition.relative(facing));
        if (te instanceof TileEntityTank tank) {
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

    private void updateClientSide() {
        recalculateSides();
    }

    public boolean isTankConnectedTo(Direction facing) {
        return sides[facing.get3DDataValue()];
    }

    public boolean isFluidConnected(Direction facing) {
        return sidesFluid[facing.get3DDataValue()];
    }

    @Override
    public ResourceHandler<FluidResource> getFluidHandler() {
        return this;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public FluidResource getResource(int index) {
        return FluidResource.of(fluid);
    }

    @Override
    public long getAmountAsLong(int index) {
        return fluid.getAmount();
    }

    @Override
    public long getCapacityAsLong(int index, FluidResource resource) {
        return CAPACITY;
    }

    @Override
    public boolean isValid(int index, FluidResource resource) {
        return true;
    }

    @Override
    public int insert(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (fluid.isEmpty()) {
            int result = Math.min(amount, CAPACITY);

            fluidJournal.updateSnapshots(transaction);
            fluid = new FluidStack(resource.getFluid(), result);
            setChanged();

            return result;
        } else if (resource.is(fluid.getFluid())) {
            int result = Math.min(amount, CAPACITY - fluid.getAmount());

            fluidJournal.updateSnapshots(transaction);
            fluid.setAmount(fluid.getAmount() + result);
            setChanged();

            return result;
        }
        return 0;
    }

    @Override
    public int extract(int index, FluidResource resource, int amount, TransactionContext transaction) {
        if (fluid.isEmpty()) {
            return 0;
        }

        if (resource.is(fluid.getFluid())) {
            int result = Math.min(amount, fluid.getAmount());

            fluidJournal.updateSnapshots(transaction);
            fluid.setAmount(fluid.getAmount() - result);
            if (fluid.getAmount() <= 0) {
                fluid = FluidStack.EMPTY;
            }
            setChanged();

            return result;
        }

        return 0;
    }
}
