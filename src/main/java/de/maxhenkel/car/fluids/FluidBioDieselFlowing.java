package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidBioDieselFlowing extends CarFluidFlowing {

    protected FluidBioDieselFlowing() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/bio_diesel_still"),
                        new ResourceLocation(Main.MODID, "block/bio_diesel_flowing")).sound(SoundEvents.BUCKET_FILL),
                () -> ModBlocks.BIO_DIESEL.get(),
                () -> ModFluids.BIO_DIESEL.get(),
                () -> ModFluids.BIO_DIESEL_FLOWING.get(),
                () -> ModItems.BIO_DIESEL_BUCKET.get()
        );
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        ModFluids.BIO_DIESEL.get().applyEffects(entity, state, worldIn, pos);
    }
}
