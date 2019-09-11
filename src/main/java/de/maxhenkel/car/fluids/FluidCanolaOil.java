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

public class FluidCanolaOil extends CarFluidSource {

    protected FluidCanolaOil() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/canola_oil_still"),
                        new ResourceLocation(Main.MODID, "block/canola_oil_flowing")).sound(SoundEvents.ITEM_BUCKET_FILL),
                () -> ModBlocks.CANOLA_OIL,
                () -> ModFluids.CANOLA_OIL,
                () -> ModFluids.CANOLA_OIL_FLOWING,
                () -> ModItems.CANOLA_OIL_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "canola_oil"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {

    }
}
