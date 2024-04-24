package de.maxhenkel.car.blocks.tileentity;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.corelib.fluid.FluidUtils;
import de.maxhenkel.tools.BlockPosList;
import de.maxhenkel.car.blocks.BlockFluidExtractor;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class TileEntityFluidExtractor extends TileEntityBase implements ITickableBlockEntity {

    private IFluidHandler extractHandler;

    private final int drainSpeed;

    private ItemStack filter;

    public TileEntityFluidExtractor(BlockPos pos, BlockState state) {
        super(Main.FLUID_EXTRACTOR_TILE_ENTITY_TYPE.get(), pos, state);
        this.drainSpeed = Main.SERVER_CONFIG.fluidExtractorDrainSpeed.get();
        filter = null;
    }

    @Nullable
    public Fluid getFilterFluid() {
        if (filter == null) {
            return null;
        }

        FluidStack stack = FluidUtil.getFluidContained(filter).orElse(null);

        if (stack == null || stack.getAmount() <= 0) {
            return null;
        }

        return stack.getFluid();
    }

    @Override
    public void tick() {
        if (level.isClientSide) {
            return;
        }
        updateExtractHandler();

        if (extractHandler == null) {
            return;
        }

        FluidStack drainSimulated;
        if (getFilterFluid() == null) {
            drainSimulated = extractHandler.drain(drainSpeed, IFluidHandler.FluidAction.SIMULATE);
        } else {
            drainSimulated = extractHandler.drain(new FluidStack(getFilterFluid(), drainSpeed), IFluidHandler.FluidAction.SIMULATE);
        }

        if (drainSimulated.getAmount() <= 0) {
            return;
        }

        List<IFluidHandler> handlers = new LinkedList<>();

        getConnectedHandlers(handlers, new BlockPosList(), worldPosition);

        List<IFluidHandler> fillHandlers = new LinkedList<>();

        for (IFluidHandler handler : handlers) {
            int amount = handler.fill(drainSimulated, IFluidHandler.FluidAction.SIMULATE);
            if (amount > 0) {
                fillHandlers.add(handler);
            }
        }

        if (fillHandlers.isEmpty()) {
            return;
        }

        for (IFluidHandler handler : fillHandlers) {
            if (getFilterFluid() == null) {
                FluidUtils.tryFluidTransfer(handler, extractHandler, drainSpeed, true);
            } else {
                FluidUtils.tryFluidTransfer(handler, extractHandler, drainSpeed, true, getFilterFluid());
            }
        }
    }

    public void updateExtractHandler() {
        BlockState state = level.getBlockState(worldPosition);

        if (!state.getBlock().equals(ModBlocks.FLUID_EXTRACTOR.get())) {
            extractHandler = null;
            return;
        }

        Direction side = state.getValue(BlockFluidExtractor.FACING);

        extractHandler = FluidUtils.getFluidHandlerOffset(level, worldPosition, side);
    }

    public void getConnectedHandlers(List<IFluidHandler> handlers, BlockPosList positions, BlockPos pos) {
        for (Direction side : Direction.values()) {
            BlockPos p = pos.relative(side);

            if (positions.contains(p)) {
                continue;
            }

            BlockState state = level.getBlockState(p);

            if (state.getBlock().equals(ModBlocks.FLUID_PIPE.get()) || state.getBlock().equals(ModBlocks.FLUID_EXTRACTOR.get())) {
                positions.add(p);
                getConnectedHandlers(handlers, positions, p);
                continue;
            }

            IFluidHandler handler = FluidUtils.getFluidHandlerOffset(level, pos, side);

            if (handler == null || handler.equals(extractHandler)) {
                continue;
            }

            handlers.add(handler);
        }
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        if (filter != null) {
            compound.put("filter", filter.saveOptional(provider));
        }
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        if (compound.contains("filter")) {
            CompoundTag tag = compound.getCompound("filter");
            filter = ItemStack.parseOptional(provider, tag);
        } else {
            filter = null;
        }
        super.loadAdditional(compound, provider);
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.fluid_extractor");
    }

    public ItemStack getFilter() {
        if (filter == null) {
            return null;
        }
        return filter.copy();
    }

    public void setFilter(ItemStack filter) {
        if (filter == null) {
            this.filter = null;
            setChanged();
            synchronize();
            return;
        }
        this.filter = filter.copy();
        setChanged();
        synchronize();
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
    }

}
