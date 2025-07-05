package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;

public class TileEntityBlastFurnace extends TileEntityEnergyFluidProducer {

    public TileEntityBlastFurnace(BlockPos pos, BlockState state) {
        super(CarMod.BLAST_FURNACE_TILE_ENTITY_TYPE.get(), CarMod.RECIPE_TYPE_BLAST_FURNACE.get(), pos, state);
        this.maxEnergy = CarMod.SERVER_CONFIG.blastFurnaceEnergyStorage.get();
        this.storedEnergy = 0;
        this.fluidAmount = CarMod.SERVER_CONFIG.blastFurnaceFluidStorage.get();
        this.currentMillibuckets = 0;
    }

    @Override
    public BlockGui<TileEntityBlastFurnace> getOwnBlock() {
        return ModBlocks.BLAST_FURNACE.get();
    }

    @Override
    public Fluid getProducingFluid() {
        return ModFluids.METHANOL.get();
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.blastfurnace");
    }
}
