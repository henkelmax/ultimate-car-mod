package de.maxhenkel.car.fluids;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FluidCanolaOilFlowing extends CarFluidFlowing {

    protected FluidCanolaOilFlowing() {
        super(new Properties(
                () -> ModFluids.CANOLA_OIL_TYPE.get(),
                () -> ModFluids.CANOLA_OIL.get(),
                () -> ModFluids.CANOLA_OIL_FLOWING.get())
                .block(() -> ModBlocks.CANOLA_OIL.get())
                .bucket(() -> ModItems.CANOLA_OIL_BUCKET.get())
        );
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        ModFluids.CANOLA_OIL.get().applyEffects(entity, state, worldIn, pos);
    }
}
