package de.maxhenkel.car.fluids;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FluidGlycerinFlowing extends CarFluidFlowing {

    protected FluidGlycerinFlowing() {
        super(new Properties(
                () -> ModFluids.GLYCERIN_TYPE.get(),
                () -> ModFluids.GLYCERIN.get(),
                () -> ModFluids.GLYCERIN_FLOWING.get())
                .block(() -> ModBlocks.GLYCERIN.get())
                .bucket(() -> ModItems.GLYCERIN_BUCKET.get())
        );
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        ModFluids.GLYCERIN.get().applyEffects(entity, state, worldIn, pos);
    }
}
