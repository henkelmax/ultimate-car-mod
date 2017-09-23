package de.maxhenkel.car.blocks.tileentity;

import java.util.ArrayList;
import java.util.List;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fluids.Fluid;

public class TileEntityBlastFurnace extends TileEntityEnergyFluidProducer{

	public TileEntityBlastFurnace() {
		this.maxStorage = Config.blastFurnaceEnergyStorage;
		this.storedEnergy = 0;
		this.timeToGenerate = 0;
		this.generatingTime = Config.blastFurnaceGeneratingTime;
		this.maxMillibuckets = Config.blastFurnaceFluidStorage;
		this.currentMillibuckets = 0;
		this.energyUsage = Config.blastFurnaceEnergyUsage;
		this.millibucketsPerUse = Config.blastFurnaceFluidGeneration;
	}
	
	@Override
	public BlockGui getOwnBlock() {
		return ModBlocks.BLAST_FURNACE;
	}

	@Override
	public ItemStack getOutputItem() {
		return new ItemStack(Items.COAL, 1, 1);
	}

	@Override
	public List<ItemStack> getInputItems() {
		List<ItemStack> items=new ArrayList<ItemStack>();
		items.add(new ItemStack(Item.getItemFromBlock(Blocks.LOG), 1, 0));
		items.add(new ItemStack(Item.getItemFromBlock(Blocks.LOG), 1, 1));
		items.add(new ItemStack(Item.getItemFromBlock(Blocks.LOG), 1, 2));
		items.add(new ItemStack(Item.getItemFromBlock(Blocks.LOG), 1, 3));
		items.add(new ItemStack(Item.getItemFromBlock(Blocks.LOG2), 1, 0));
		items.add(new ItemStack(Item.getItemFromBlock(Blocks.LOG2), 1, 1));
		return items;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation("tile.blastfurnace.name");
	}
	
	@Override
	public Fluid getProducingFluid() {
		return ModFluids.METHANOL;
	}

}
