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

public class FluidGlycerin extends CarFluidSource {

    protected FluidGlycerin() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/glycerin_still"),
                        new ResourceLocation(Main.MODID, "block/glycerin_flowing")).sound(SoundEvents.BUCKET_FILL).density(5000).viscosity(100),
                () -> ModBlocks.GLYCERIN,
                () -> ModFluids.GLYCERIN,
                () -> ModFluids.GLYCERIN_FLOWING,
                () -> ModItems.GLYCERIN_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "glycerin"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.abilities.instabuild) {
                player.addEffect(new EffectInstance(Effects.CONFUSION, 100, 0, true, false));
            }
        }
    }
}
