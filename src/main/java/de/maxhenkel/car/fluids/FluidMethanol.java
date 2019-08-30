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

public class FluidMethanol extends CarFluid.Source {

    public FluidMethanol() {
        super(new ResourceLocation(Main.MODID, "methanol"), new ResourceLocation(Main.MODID, "block/methanol_still"), new ResourceLocation(Main.MODID, "block/methanol_flowing"));
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos) {
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            if (!player.abilities.isCreativeMode && player.areEyesInFluid(ModFluidTags.BLINDING)) {
                player.addPotionEffect(new EffectInstance(Effects.BLINDNESS, 1200, 0, true, false));
            }
        }
    }

    @Override
    public Item getFilledBucket() {
        return ModItems.METHANOL_BUCKET;
    }

    @Override
    public Block getFluidBlock() {
        return ModBlocks.METHANOL;
    }

    @Override
    public Fluid getEquivalent() {
        return ModFluids.METHANOL_FLOWING;
    }
}
