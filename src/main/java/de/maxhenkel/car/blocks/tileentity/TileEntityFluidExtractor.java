package de.maxhenkel.car.blocks.tileentity;

import java.util.LinkedList;
import java.util.List;
import javax.annotation.Nullable;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.blockentity.ITickableBlockEntity;
import de.maxhenkel.tools.BlockPosList;
import de.maxhenkel.car.blocks.BlockFluidExtractor;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.fluid.FluidUtil;
import net.neoforged.neoforge.transfer.transaction.Transaction;

public class TileEntityFluidExtractor extends TileEntityBase implements ITickableBlockEntity {

    private ResourceHandler<FluidResource> extractHandler;

    private final int drainSpeed;

    private ItemStack filter;

    public TileEntityFluidExtractor(BlockPos pos, BlockState state) {
        super(CarMod.FLUID_EXTRACTOR_TILE_ENTITY_TYPE.get(), pos, state);
        this.drainSpeed = CarMod.SERVER_CONFIG.fluidExtractorDrainSpeed.get();
        filter = ItemStack.EMPTY;
    }

    @Nullable
    public Fluid getFilterFluid() {
        if (filter.isEmpty()) {
            return null;
        }

        FluidStack stack = FluidUtil.getFirstStackContained(filter);

        if (stack.isEmpty() || stack.getAmount() <= 0) {
            return null;
        }

        return stack.getFluid();
    }

    @Override
    public void tick() {
        if (level.isClientSide()) {
            return;
        }
        updateExtractHandler();

        if (extractHandler == null) {
            return;
        }

        int drainSimulated;
        FluidResource toExtract = null;
        Fluid filterFluid = getFilterFluid();
        if (filterFluid == null) {
            for (int i = 0; i < extractHandler.size(); i++) {
                FluidResource resource = extractHandler.getResource(i);
                if (resource.isEmpty()) {
                    continue;
                }
                int simulatedExtract;
                try (Transaction transaction = Transaction.open(null)) {
                    simulatedExtract = extractHandler.extract(resource, drainSpeed, transaction);
                }
                if (simulatedExtract > 0) {
                    toExtract = resource;
                    break;
                }
            }
            if (toExtract == null) {
                return;
            }
        } else {
            toExtract = FluidResource.of(filterFluid);
        }
        try (Transaction transaction = Transaction.open(null)) {
            drainSimulated = extractHandler.extract(toExtract, drainSpeed, transaction);
        }

        if (drainSimulated <= 0) {
            return;
        }

        List<ResourceHandler<FluidResource>> handlers = new LinkedList<>();

        getConnectedHandlers(handlers, new BlockPosList(), worldPosition);

        List<ResourceHandler<FluidResource>> fillHandlers = new LinkedList<>();
        try (Transaction transaction = Transaction.open(null)) {
            for (ResourceHandler<FluidResource> handler : handlers) {
                int amount = handler.insert(toExtract, drainSimulated, transaction);
                if (amount > 0) {
                    fillHandlers.add(handler);
                }
            }
        }

        if (fillHandlers.isEmpty()) {
            return;
        }

        FluidResource extractFluid = toExtract;
        for (ResourceHandler<FluidResource> handler : fillHandlers) {
            ResourceHandlerUtil.move(extractHandler, handler, resource -> resource.is(extractFluid.getFluid()), drainSpeed, null);
        }
    }

    public void updateExtractHandler() {
        BlockState state = level.getBlockState(worldPosition);

        if (!state.getBlock().equals(ModBlocks.FLUID_EXTRACTOR.get())) {
            extractHandler = null;
            return;
        }

        Direction side = state.getValue(BlockFluidExtractor.FACING);

        extractHandler = level.getCapability(Capabilities.Fluid.BLOCK, worldPosition.relative(side), side.getOpposite());
    }

    public void getConnectedHandlers(List<ResourceHandler<FluidResource>> handlers, BlockPosList positions, BlockPos pos) {
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

            ResourceHandler<FluidResource> handler = level.getCapability(Capabilities.Fluid.BLOCK, pos.relative(side), side.getOpposite());

            if (handler == null || handler.equals(extractHandler)) {
                continue;
            }

            handlers.add(handler);
        }
    }

    @Override
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (!filter.isEmpty()) {
            valueOutput.store("filter", ItemStack.CODEC, filter);
        }
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        filter = valueInput.read("filter", ItemStack.CODEC).orElse(ItemStack.EMPTY);
        super.loadAdditional(valueInput);
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.fluid_extractor");
    }

    public ItemStack getFilter() {
        return filter.copy();
    }

    public void setFilter(ItemStack filter) {
        this.filter = filter.copy();
        setChanged();
        synchronize();
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
    }

}
