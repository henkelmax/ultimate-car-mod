package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.BlockGui;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import de.maxhenkel.car.items.ItemCanola;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
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


    public static boolean isValidItem(ItemStack stack) {
        //return ItemTools.matchesOredict(stack, "cropCanola");//TODO oredict
        //return stack.getItem().getTags().contains(new ResourceLocation("minecraft", "log"));//TODO check if it works
        return stack.getItem() instanceof ItemCanola;
    }

    @Override
    public ITextComponent getDisplayName() {
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
