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

public class FluidCanolaOilFlowing extends CarFluidFlowing {

    protected FluidCanolaOilFlowing() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/canola_oil_still"),
                        new ResourceLocation(Main.MODID, "block/canola_oil_flowing")).sound(SoundEvents.BUCKET_FILL),
                () -> ModBlocks.CANOLA_OIL,
                () -> ModFluids.CANOLA_OIL,
                () -> ModFluids.CANOLA_OIL_FLOWING,
                () -> ModItems.CANOLA_OIL_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "canola_oil_flowing"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        ModFluids.CANOLA_OIL.applyEffects(entity, state, worldIn, pos);
    }
}
