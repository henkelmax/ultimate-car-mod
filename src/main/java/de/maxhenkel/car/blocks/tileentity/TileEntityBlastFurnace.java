package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.Tags;

public class TileEntityBlastFurnace extends TileEntityEnergyFluidProducer {

    public TileEntityBlastFurnace() {
        super(Main.BLAST_FURNACE_TILE_ENTITY_TYPE);
        this.maxStorage = Config.blastFurnaceEnergyStorage.get();
        this.storedEnergy = 0;
        this.timeToGenerate = 0;
        this.generatingTime = Config.blastFurnaceGeneratingTime.get();
        this.maxMillibuckets = Config.blastFurnaceFluidStorage.get();
        this.currentMillibuckets = 0;
        this.energyUsage = Config.blastFurnaceEnergyUsage.get();
        this.millibucketsPerUse = Config.blastFurnaceFluidGeneration.get();
    }

    @Override
    public BlockGui getOwnBlock() {
        return ModBlocks.BLAST_FURNACE;
    }

    @Override
    public ItemStack getOutputItem() {
        return new ItemStack(Items.CHARCOAL, 1);
    }

    @Override
    public boolean isValidItem(ItemStack stack) {
        return ItemTools.matchesTag(stack, BlockTags.LOGS.getId());
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
