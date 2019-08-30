package de.maxhenkel.tools;

import javax.annotation.Nullable;

import de.maxhenkel.car.blocks.BlockTank;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
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

    @Nullable
    public static FluidStack tryFluidTransfer(IFluidHandler fluidDestination, IFluidHandler fluidSource, int maxAmount, boolean doTransfer, Fluid filter) {
        FluidStack drainable = fluidSource.drain(new FluidStack(filter, maxAmount), IFluidHandler.FluidAction.SIMULATE);
        if (drainable != null && drainable.getAmount() > 0) {
            int fillableAmount = fluidDestination.fill(drainable, IFluidHandler.FluidAction.SIMULATE);
            if (fillableAmount > 0) {
                if (doTransfer) {
                    FluidStack drained = fluidSource.drain(new FluidStack(filter, fillableAmount), IFluidHandler.FluidAction.EXECUTE);
                    if (drained != null) {
                        drained.setAmount(fluidDestination.fill(drained, IFluidHandler.FluidAction.EXECUTE));
                        return drained;
                    }
                } else {
                    drainable.setAmount(fillableAmount);
                    return drainable;
                }
            }
        }
        return null;
    }

    public static boolean isEmpty(FluidStack stack) {
        if (stack.isEmpty() || stack == FluidStack.EMPTY || stack.getFluid() == Fluids.EMPTY) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean tryFluidInteraction(PlayerEntity player, Hand hand, World world, BlockPos pos) {
        ItemStack stack = player.getHeldItem(hand);

        FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(FluidStack.EMPTY);

        if (!FluidUtils.isEmpty(fluidStack)) {
            boolean success = BlockTank.handleEmpty(stack, world, pos, player, hand);
            if (success) {
                return true;
            }
        }
        IFluidHandler handler = FluidUtil.getFluidHandler(stack).orElse(null);

        if (handler != null) {
            boolean success1 = BlockTank.handleFill(stack, world, pos, player, hand);
            if (success1) {
                return true;
            }
        }
        return false;
    }

}
