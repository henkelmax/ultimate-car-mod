package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidMethanolFlowing extends CarFluidFlowing {

    protected FluidMethanolFlowing() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/methanol_still"),
                        new ResourceLocation(Main.MODID, "block/methanol_flowing")).sound(SoundEvents.ITEM_BUCKET_FILL),
                () -> ModBlocks.METHANOL,
                () -> ModFluids.METHANOL,
                () -> ModFluids.METHANOL_FLOWING,
                () -> ModItems.METHANOL_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "methanol_flowing"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {
        ModFluids.METHANOL.applyEffects(entity, state, worldIn, pos);
    }
}
