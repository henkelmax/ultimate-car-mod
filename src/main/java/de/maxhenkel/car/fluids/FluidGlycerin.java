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

public class FluidGlycerin extends CarFluidSource {

    protected FluidGlycerin() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/glycerin_still"),
                        new ResourceLocation(Main.MODID, "block/glycerin_flowing")).sound(SoundEvents.BUCKET_FILL).density(5000).viscosity(100),
                () -> ModBlocks.GLYCERIN.get(),
                () -> ModFluids.GLYCERIN.get(),
                () -> ModFluids.GLYCERIN_FLOWING.get(),
                () -> ModItems.GLYCERIN_BUCKET.get()
        );
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
