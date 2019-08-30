package de.maxhenkel.car.fluids;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FluidBioDiesel extends CarFluid.Source {

    public FluidBioDiesel() {
        super(new ResourceLocation(Main.MODID, "bio_diesel"), new ResourceLocation(Main.MODID, "block/bio_diesel_still"), new ResourceLocation(Main.MODID, "block/bio_diesel_flowing"));
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

    @Override
    public Item getFilledBucket() {
        return ModItems.BIO_DIESEL_BUCKET;
    }

    @Override
    public Block getFluidBlock() {
        return ModBlocks.BIO_DIESEL;
    }

    @Override
    public Fluid getEquivalent() {
        return ModFluids.BIO_DIESEL_FLOWING;
    }
}
