package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.tools.FluidStackWrapper;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import java.util.Iterator;

public class TileEntityTank extends TileEntityBase implements IFluidHandler, ITickableTileEntity {

    private FluidStack fluid;
    public static final int CAPACITY = 16000;

    public TileEntityTank() {
        super(Main.TANK_TILE_ENTITY_TYPE);
        this.fluid = null;
    }


    @Override
    public void tick() {
        if (!world.isRemote) {
            if (world.getGameTime() % 20 == 0) {
                synchronize();
            }
        } else {
            updateClientSide();
        }

        if (fluid == null) {
            return;
        }

        checkDown();

        Iterator<Direction> i = Direction.Plane.HORIZONTAL.iterator();
        while (i.hasNext()) {
            Direction facing = i.next();
            if (fluid == null) {
                return;
            }
            checkSide(facing);
        }
    }

    public void checkSide(Direction side) {
        TileEntity te = world.getTileEntity(pos.offset(side));

        if (!(te instanceof TileEntityTank)) {
            return;
        }

        TileEntityTank otherTank = (TileEntityTank) te;

        FluidStack other = otherTank.getFluid();

        if (other == null) {
            otherTank.setFluid(new FluidStackWrapper(fluid.getFluid(), 0));
            other = otherTank.getFluid();
        }

        if (!other.getFluid().equals(fluid.getFluid())) {
            return;
        }

        int dif = other.amount - fluid.amount;

        if (dif >= -2) {
            return;
        }

        FluidUtil.tryFluidTransfer(otherTank, this, (-dif) / 2, true);
    }

    public void checkDown() {
        TileEntity te = world.getTileEntity(pos.down());

        if (!(te instanceof TileEntityTank)) {
            return;
        }

        TileEntityTank otherTank = (TileEntityTank) te;

        FluidStack stack = FluidUtil.tryFluidTransfer(otherTank, this, Integer.MAX_VALUE, true);

        if (stack == null) {
            return;
        }

        if (fluid != null && fluid.amount <= 0) {
            fluid = null;
        }
    }

    public float getFillPercent() {
        if (fluid == null) {
            return 0F;
        }

        return ((float) fluid.amount) / ((float) CAPACITY);
    }

    public FluidStack getFluid() {
        return fluid;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (fluid != null && fluid.amount > 0) {
            CompoundNBT comp = new CompoundNBT();

            fluid.writeToNBT(comp);

            compound.put("fluid", comp);
        }
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("fluid")) {
            CompoundNBT comp = compound.getCompound("fluid");
            fluid = FluidStackWrapper.loadFluidStackFromNBT(comp);
        } else {
            fluid = null;
        }
        super.read(compound);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[]{new IFluidTankProperties() {

            @Override
            public FluidStack getContents() {
                return fluid;
            }

            @Override
            public int getCapacity() {
                return CAPACITY;
            }

            @Override
            public boolean canFillFluidType(FluidStack fluidStack) {
                return true;
            }

            @Override
            public boolean canFill() {
                return true;
            }

            @Override
            public boolean canDrainFluidType(FluidStack fluidStack) {
                return true;
            }

            @Override
            public boolean canDrain() {
                return true;
            }
        }};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (fluid == null) {
            int amount = Math.min(resource.amount, CAPACITY);

            if (doFill) {
                fluid = new FluidStackWrapper(resource.getFluid(), amount);
                // synchronize();
                markDirty();
            }

            return amount;
        } else if (resource.getFluid().equals(fluid.getFluid())) {
            int amount = Math.min(resource.amount, CAPACITY - fluid.amount);

            if (doFill) {
                fluid.amount += amount;
                // synchronize();
                markDirty();
            }

            return amount;
        }
        return 0;
    }

    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {

        if (fluid == null) {
            return null;
        }

        if (fluid.getFluid().equals(resource.getFluid())) {
            int amount = Math.min(resource.amount, fluid.amount);

            Fluid f = fluid.getFluid();

            if (doDrain) {
                fluid.amount -= amount;
                if (fluid.amount <= 0) {
                    fluid = null;
                }
                // synchronize();
                markDirty();
            }

            return new FluidStackWrapper(f, amount);
        }

        return null;
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (fluid == null) {
            return null;
        }

        int amount = Math.min(maxDrain, fluid.amount);

        Fluid f = fluid.getFluid();

        if (doDrain) {
            fluid.amount -= amount;
            if (fluid.amount <= 0) {
                fluid = null;
            }
            // synchronize();
            markDirty();
        }

        return new FluidStackWrapper(f, amount);
    }

    public void setFluid(FluidStack fluid) {
        this.fluid = fluid;
    }

    /* **********************RENDERING************************ */

    //@SideOnly(Side.CLIENT)
    private boolean[] sides = new boolean[Direction.values().length];

    //@SideOnly(Side.CLIENT)
    private boolean[] sidesFluid = new boolean[Direction.values().length];

    @OnlyIn(Dist.CLIENT)
    private void recalculateSides() {
        for (Direction facing : Direction.values()) {
            sides[facing.getIndex()] = isTankConnectedCalc(facing);
            sidesFluid[facing.getIndex()] = isFluidConnectedCalc(facing);
        }
    }

    @OnlyIn(Dist.CLIENT)
    private boolean isFluidConnectedCalc(Direction facing) {
        TileEntity te = world.getTileEntity(pos.offset(facing));
        if (te instanceof TileEntityTank) {
            TileEntityTank tank = (TileEntityTank) te;
            if (tank.fluid == null || fluid == null || tank.fluid.amount == 0 || fluid.amount == 0) {
                return false;
            }

            if (tank.fluid.getFluid().equals(fluid.getFluid())) {
                return true;
            }
        }
        return false;
    }


    @OnlyIn(Dist.CLIENT)
    private boolean isTankConnectedCalc(Direction facing) {
        TileEntity te = world.getTileEntity(pos.offset(facing));
        if (te instanceof TileEntityTank) {
            TileEntityTank tank = (TileEntityTank) te;
            if (tank.fluid == null && fluid == null) {
                return true;
            }

            if (tank.fluid == null || fluid == null) {
                return true;//Check TODO
            }

            if (tank.fluid.getFluid().equals(fluid.getFluid())) {
                return true;
            }
        }
        return false;
    }

    @OnlyIn(Dist.CLIENT)
    private void updateClientSide() {
        recalculateSides();
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isTankConnectedTo(Direction facing) {
        return sides[facing.getIndex()];
    }

    @OnlyIn(Dist.CLIENT)
    public boolean isFluidConnected(Direction facing) {
        return sidesFluid[facing.getIndex()];
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.car.tank");
    }

    @Override
    public IIntArray getFields() {
        return new IntArray(0);
    }
}
