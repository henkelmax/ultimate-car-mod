package de.maxhenkel.car.mixins;

import com.google.common.collect.ImmutableSet;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.merchant.villager.AbstractVillagerEntity;
import net.minecraft.entity.merchant.villager.VillagerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends AbstractVillagerEntity {

    public VillagerEntityMixin(EntityType<? extends AbstractVillagerEntity> type, World world) {
        super(type, world);
    }

    @Inject(method = "hasFarmSeeds", at = @At("HEAD"), cancellable = true)
    public void hasFarmSeeds(CallbackInfoReturnable<Boolean> cir) {
        if (getInventory().hasAnyOf(ImmutableSet.of(ModItems.CANOLA_SEEDS))) {
            cir.setReturnValue(true);
        }
    }

}
