package de.maxhenkel.car.mixins;

import de.maxhenkel.car.items.ModItems;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin extends AbstractVillager {

    public VillagerEntityMixin(EntityType<? extends AbstractVillager> type, Level world) {
        super(type, world);
    }

    @Inject(method = "hasFarmSeeds", at = @At("HEAD"), cancellable = true)
    public void hasFarmSeeds(CallbackInfoReturnable<Boolean> cir) {
        if (getInventory().hasAnyOf(Set.of(ModItems.CANOLA_SEEDS.get()))) {
            cir.setReturnValue(true);
        }
    }

}
