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

public class FluidBioDiesel extends CarFluidSource {

    protected FluidBioDiesel() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/bio_diesel_still"),
                        new ResourceLocation(Main.MODID, "block/bio_diesel_flowing")).sound(SoundEvents.ITEM_BUCKET_FILL),
                () -> ModBlocks.BIO_DIESEL,
                () -> ModFluids.BIO_DIESEL,
                () -> ModFluids.BIO_DIESEL_FLOWING,
                () -> ModItems.BIO_DIESEL_BUCKET
        );
        setRegistryName(new ResourceLocation(Main.MODID, "bio_diesel"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.abilities.isCreativeMode) {
                player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 0, true, false));
            }
        }
    }
}
