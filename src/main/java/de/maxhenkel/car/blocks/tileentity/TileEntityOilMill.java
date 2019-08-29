package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.items.ItemCanola;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntityOilMill extends TileEntityEnergyFluidProducer {

    public TileEntityOilMill() {
        super(Main.OIL_MILL_TILE_ENTITY_TYPE);
        this.maxStorage = Config.oilMillEnergyStorage.get();
        this.storedEnergy = 0;
        this.timeToGenerate = 0;
        this.generatingTime = Config.oilMillGeneratingTime.get();
        this.maxMillibuckets = Config.oilMillFluidStorage.get();
        this.currentMillibuckets = 0;
        this.energyUsage = Config.oilMillEnergyUsage.get();
        this.millibucketsPerUse = Config.oilMillFluidGeneration.get();
    }

    @Override
    public ItemStack getOutputItem() {
        return new ItemStack(ModItems.CANOLA_CAKE);
    }

    @Override
    public boolean isValidItem(ItemStack stack) {
        return stack.getItem() instanceof ItemCanola;
    }

    @Override
    public ITextComponent getTranslatedName() {
        return new TranslationTextComponent("block.car.oilmill");
    }

    @Override
    public BlockGui getOwnBlock() {
        return ModBlocks.OIL_MILL;
    }

    @Override
    public Fluid getProducingFluid() {
        return ModFluids.CANOLA_OIL;
    }

}
