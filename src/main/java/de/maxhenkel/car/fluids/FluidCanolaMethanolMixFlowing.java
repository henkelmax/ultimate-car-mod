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

public class FluidCanolaMethanolMixFlowing extends CarFluidFlowing {

    protected FluidCanolaMethanolMixFlowing() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/canola_methanol_mix_still"),
                        new ResourceLocation(Main.MODID, "block/canola_methanol_mix_flowing")).sound(SoundEvents.BUCKET_FILL),
                () -> ModBlocks.CANOLA_METHANOL_MIX,
                () -> ModFluids.CANOLA_METHANOL_MIX,
                () -> ModFluids.CANOLA_METHANOL_MIX_FLOWING,
                () -> ModItems.CANOLA_METHANOL_MIX_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "canola_methanol_mix_flowing"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        ModFluids.CANOLA_METHANOL_MIX.applyEffects(entity, state, worldIn, pos);
    }
}
