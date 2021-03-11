package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidMethanol extends CarFluidSource {

    protected FluidMethanol() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/methanol_still"),
                        new ResourceLocation(Main.MODID, "block/methanol_flowing")).sound(SoundEvents.BUCKET_FILL),
                () -> ModBlocks.METHANOL,
                () -> ModFluids.METHANOL,
                () -> ModFluids.METHANOL_FLOWING,
                () -> ModItems.METHANOL_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "methanol"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.abilities.instabuild && player.isEyeInFluid(ModFluidTags.BLINDING)) {
                player.addEffect(new EffectInstance(Effects.BLINDNESS, 1200, 0, true, false));
            }
        }
    }
}
