package de.maxhenkel.car.fluids;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FluidCanolaMethanolMixFlowing extends CarFluidFlowing {

    protected FluidCanolaMethanolMixFlowing() {
        super(new Properties(
                () -> ModFluids.CANOLA_METHANOL_MIX_TYPE.get(),
                () -> ModFluids.CANOLA_METHANOL_MIX.get(),
                () -> ModFluids.CANOLA_METHANOL_MIX_FLOWING.get())
                .block(() -> ModBlocks.CANOLA_METHANOL_MIX.get())
                .bucket(() -> ModItems.CANOLA_METHANOL_MIX_BUCKET.get())
        );
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        ModFluids.CANOLA_METHANOL_MIX.get().applyEffects(entity, state, worldIn, pos);
    }
}
