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

public class FluidCanolaMethanolMix extends CarFluidSource {

    protected FluidCanolaMethanolMix() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/canola_methanol_mix_still"),
                        new ResourceLocation(Main.MODID, "block/canola_methanol_mix_flowing")).sound(SoundEvents.ITEM_BUCKET_FILL),
                () -> ModBlocks.CANOLA_METHANOL_MIX,
                () -> ModFluids.CANOLA_METHANOL_MIX,
                () -> ModFluids.CANOLA_METHANOL_MIX_FLOWING,
                () -> ModItems.CANOLA_METHANOL_MIX_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "canola_methanol_mix"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {

    }
}
