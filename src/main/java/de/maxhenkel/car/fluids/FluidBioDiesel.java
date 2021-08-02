package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidAttributes;

public class FluidBioDiesel extends CarFluidSource {

    protected FluidBioDiesel() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/bio_diesel_still"),
                        new ResourceLocation(Main.MODID, "block/bio_diesel_flowing")).sound(SoundEvents.BUCKET_FILL),
                () -> ModBlocks.BIO_DIESEL,
                () -> ModFluids.BIO_DIESEL,
                () -> ModFluids.BIO_DIESEL_FLOWING,
                () -> ModItems.BIO_DIESEL_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "bio_diesel"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (!player.getAbilities().instabuild) {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0, true, false));
            }
        }
    }
}
