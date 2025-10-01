package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HUDHandlerCars implements IEntityComponentProvider {

    public static final HUDHandlerCars INSTANCE = new HUDHandlerCars();

    private static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "car");

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        iTooltip.remove(PluginCar.OBJECT_NAME_TAG);
        iTooltip.add(entityAccessor.getEntity().getDisplayName().copy().withStyle(ChatFormatting.WHITE));

        if (entityAccessor.getEntity() instanceof EntityGenericCar car) {
            FluidResource carFluid = car.getResource(0);
            int carFluidAmount = car.getAmountAsInt(0);
            if (carFluidAmount > 0) {
                iTooltip.add(Component.translatable("tooltip.waila.car.fuel", carFluid.getHoverName()));
                iTooltip.add(Component.translatable("tooltip.waila.car.fuel_amount", carFluidAmount, car.getMaxFuel()));
            }

            float damage = car.getDamage();
            if (damage > 0F) {
                iTooltip.add(Component.translatable("tooltip.waila.car.damage", MathUtils.round(damage, 2)));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}