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

public class FluidGlycerinFlowing extends CarFluidFlowing {

    protected FluidGlycerinFlowing() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/glycerin_still"),
                        new ResourceLocation(Main.MODID, "block/glycerin_flowing")).sound(SoundEvents.ITEM_BUCKET_FILL).density(5000).viscosity(100),
                () -> ModBlocks.GLYCERIN,
                () -> ModFluids.GLYCERIN,
                () -> ModFluids.GLYCERIN_FLOWING,
                () -> ModItems.GLYCERIN_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "glycerin_flowing"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {
        ModFluids.GLYCERIN.applyEffects(entity, state, worldIn, pos);
    }
}
