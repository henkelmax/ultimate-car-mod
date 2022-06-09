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

public class FluidMethanol extends CarFluidSource {

    protected FluidMethanol() {
        super(
                FluidAttributes.builder(
                        new ResourceLocation(Main.MODID, "block/methanol_still"),
                        new ResourceLocation(Main.MODID, "block/methanol_flowing")).sound(SoundEvents.BUCKET_FILL),
                () -> ModBlocks.METHANOL.get(),
                () -> ModFluids.METHANOL.get(),
                () -> ModFluids.METHANOL_FLOWING.get(),
                () -> ModItems.METHANOL_BUCKET.get()
        );
    }

    @Override
    public void applyEffects(Entity entity, BlockState state, Level worldIn, BlockPos pos) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            if (!player.getAbilities().instabuild && player.isEyeInFluid(ModFluidTags.BLINDING)) {
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1200, 0, true, false));
            }
        }
    }

}
