package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.math.MathUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;
import snownee.jade.api.EntityAccessor;
import snownee.jade.api.IEntityComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HUDHandlerCars implements IEntityComponentProvider {

    public static final HUDHandlerCars INSTANCE = new HUDHandlerCars();

    private static final ResourceLocation UID = new ResourceLocation(Main.MODID, "car");

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        iTooltip.remove(PluginCar.OBJECT_NAME_TAG);
        iTooltip.add(entityAccessor.getEntity().getDisplayName().copy().withStyle(ChatFormatting.WHITE));

        if (entityAccessor.getEntity() instanceof EntityGenericCar car) {
            FluidStack carFluid = car.getFluidInTank(0);
            if (!carFluid.isEmpty()) {
                iTooltip.add(Component.translatable("tooltip.waila.car.fuel", carFluid.getDisplayName()));
                iTooltip.add(Component.translatable("tooltip.waila.car.fuel_amount", carFluid.getAmount(), car.getMaxFuel()));
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