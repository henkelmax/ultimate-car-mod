package de.maxhenkel.tools;

import java.util.List;
import javax.annotation.Nullable;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class FluidUtils {

    public static boolean isFluidHandler(IBlockReader world, BlockPos pos, Direction side) {
        TileEntity te = world.getTileEntity(pos.offset(side));

        if (te == null || !te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite()).isPresent()) {
            return false;
        }
        return true;
    }

    public static IFluidHandler getFluidHandler(IWorldReader world, BlockPos pos, Direction side) {
        TileEntity te = world.getTileEntity(pos.offset(side));

        if (te == null) {
            return null;
        }
        return te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, side.getOpposite()).orElse(null);
    }

    public static boolean containsFluid(List<FluidSelector> list, Fluid fluid) {
        for (FluidSelector sel : list) {
            if (sel.isValid(fluid)) {
                return true;
            }
        }

        return false;
    }

    @Nullable
    public static FluidStack tryFluidTransfer(IFluidHandler fluidDestination, IFluidHandler fluidSource, int maxAmount,
                                              boolean doTransfer, Fluid filter) {
        FluidStack drainable = fluidSource.drain(new FluidStackWrapper(filter, maxAmount), false);
        if (drainable != null && drainable.amount > 0) {
            int fillableAmount = fluidDestination.fill(drainable, false);
            if (fillableAmount > 0) {
                if (doTransfer) {
                    FluidStack drained = fluidSource.drain(new FluidStackWrapper(filter, fillableAmount), true);
                    if (drained != null) {
                        drained.amount = fluidDestination.fill(drained, true);
                        return drained;
                    }
                } else {
                    drainable.amount = fillableAmount;
                    return drainable;
                }
            }
        }
        return null;
    }

}
