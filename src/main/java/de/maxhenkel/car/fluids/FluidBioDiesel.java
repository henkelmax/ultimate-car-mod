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

public class FluidBioDiesel extends CarFluidSource {

    protected FluidBioDiesel() {
        super(new Properties(
                () -> ModFluids.BIO_DIESEL_TYPE.get(),
                () -> ModFluids.BIO_DIESEL.get(),
                () -> ModFluids.BIO_DIESEL_FLOWING.get())
                .block(() -> ModBlocks.BIO_DIESEL.get())
                .bucket(() -> ModItems.BIO_DIESEL_BUCKET.get())
        );
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (!player.getAbilities().instabuild) {
                player.addEffect(new MobEffectInstance(MobEffects.NAUSEA, 100, 0, true, false));
            }
        }
    }
}
