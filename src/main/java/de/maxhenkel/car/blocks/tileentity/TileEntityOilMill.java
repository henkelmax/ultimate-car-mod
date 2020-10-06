package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityOilMill extends TileEntityEnergyFluidProducer {

    public TileEntityOilMill() {
        super(Main.OIL_MILL_TILE_ENTITY_TYPE, Main.RECIPE_TYPE_OIL_MILL);
        this.maxEnergy = Main.SERVER_CONFIG.oilMillEnergyStorage.get();
        this.storedEnergy = 0;
        this.fluidAmount = Main.SERVER_CONFIG.oilMillFluidStorage.get();
        this.currentMillibuckets = 0;
    }

    @Override
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.oilmill");
    }

    @Override
    public BlockGui<TileEntityOilMill> getOwnBlock() {
        return ModBlocks.OIL_MILL;
    }

    @Override
    public Fluid getProducingFluid() {
        return ModFluids.CANOLA_OIL;
    }

}
