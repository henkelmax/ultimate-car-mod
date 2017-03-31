package de.maxhenkel.car.blocks.tileentity;

import java.util.ArrayList;
import java.util.List;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;

public class TileEntityOilMill extends TileEntityEnergyFluidProducer {

	public TileEntityOilMill() {
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
	public List<Item> getInputItems() {
		List<Item> items=new ArrayList<Item>();
		items.add(ModItems.CANOLA);
		return items;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("tile.oilmill.name");
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
