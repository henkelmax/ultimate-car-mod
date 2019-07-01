package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fluids.Fluid;

public class TileEntityOilMill extends TileEntityEnergyFluidProducer {

    public TileEntityOilMill() {
        super(Main.OIL_MILL_TILE_ENTITY_TYPE);
        this.maxStorage = Config.oilMillEnergyStorage;
        this.storedEnergy = 0;
        this.timeToGenerate = 0;
        this.generatingTime = Config.oilMillGeneratingTime;
        this.maxMillibuckets = Config.oilMillFluidStorage;
        this.currentMillibuckets = 0;
        this.energyUsage = Config.oilMillEnergyUsage;
        this.millibucketsPerUse = Config.oilMillFluidGeneration;
    }

    @Override
    public ItemStack getOutputItem() {
        return new ItemStack(ModItems.RAPECAKE);
    }

    @Override
    public boolean isValidItem(ItemStack stack) {
        return ItemTools.matchesOredict(stack, "cropCanola");
    }
/*
	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("tile.oilmill.name");
	}*/

    @Override
    public BlockGui getOwnBlock() {
        return ModBlocks.OIL_MILL;
    }

    @Override
    public Fluid getProducingFluid() {
        return ModFluids.CANOLA_OIL;
    }

}
