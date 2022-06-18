package de.maxhenkel.car.fluids;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class FluidGlycerin extends CarFluidSource {

    protected FluidGlycerin() {
        super(new Properties(
                () -> ModFluids.GLYCERIN_TYPE.get(),
                () -> ModFluids.GLYCERIN.get(),
                () -> ModFluids.GLYCERIN_FLOWING.get())
                .block(() -> ModBlocks.GLYCERIN.get())
                .bucket(() -> ModItems.GLYCERIN_BUCKET.get())
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
