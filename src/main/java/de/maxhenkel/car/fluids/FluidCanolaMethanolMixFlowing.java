package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;

public class FluidCanolaMethanolMixFlowing extends CarFluid.Flowing {

    public FluidCanolaMethanolMixFlowing() {
        super(new ResourceLocation(Main.MODID, "canola_methanol_mix_flowing"), new ResourceLocation(Main.MODID, "block/canola_methanol_mix_still"), new ResourceLocation(Main.MODID, "block/canola_methanol_mix_flowing"));
    }

    @Override
    public Item getFilledBucket() {
        return ModItems.CANOLA_METHANOL_MIX_BUCKET;
    }

    @Override
    public Block getFluidBlock() {
        return ModBlocks.CANOLA_METHANOL_MIX;
    }

    @Override
    public Fluid getEquivalent() {
        return ModFluids.CANOLA_METHANOL_MIX;
    }
}
