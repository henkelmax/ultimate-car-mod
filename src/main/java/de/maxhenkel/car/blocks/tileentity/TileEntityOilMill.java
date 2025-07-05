package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class TileEntityOilMill extends TileEntityEnergyFluidProducer {

    public TileEntityOilMill(BlockPos pos, BlockState state) {
        super(CarMod.OIL_MILL_TILE_ENTITY_TYPE.get(), CarMod.RECIPE_TYPE_OIL_MILL.get(), pos, state);
        this.maxEnergy = CarMod.SERVER_CONFIG.oilMillEnergyStorage.get();
        this.storedEnergy = 0;
        this.fluidAmount = CarMod.SERVER_CONFIG.oilMillFluidStorage.get();
        this.currentMillibuckets = 0;
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.oilmill");
    }

    @Override
    public BlockGui<TileEntityOilMill> getOwnBlock() {
        return ModBlocks.OIL_MILL.get();
    }

    @Override
    public Fluid getProducingFluid() {
        return ModFluids.CANOLA_OIL.get();
    }

}
