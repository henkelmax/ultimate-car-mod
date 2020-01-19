package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityBlastFurnace extends TileEntityEnergyFluidProducer {

    public TileEntityBlastFurnace() {
        super(Main.BLAST_FURNACE_TILE_ENTITY_TYPE, Main.RECIPE_TYPE_BLAST_FURNACE);
        this.maxEnergy = Config.blastFurnaceEnergyStorage.get();
        this.storedEnergy = 0;
        this.fluidAmount = Config.blastFurnaceFluidStorage.get();
        this.currentMillibuckets = 0;
    }

    @Override
    public BlockGui getOwnBlock() {
        return ModBlocks.BLAST_FURNACE;
    }

    @Override
    public Fluid getProducingFluid() {
        return ModFluids.METHANOL;
    }

    @Override
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.blastfurnace");
    }
}
