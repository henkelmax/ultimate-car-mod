package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.math.MathUtils;
import mcp.mobius.waila.Waila;
import mcp.mobius.waila.api.EntityAccessor;
import mcp.mobius.waila.api.IEntityComponentProvider;
import mcp.mobius.waila.api.ITooltip;
import mcp.mobius.waila.api.TooltipPosition;
import mcp.mobius.waila.api.config.IPluginConfig;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraftforge.fluids.FluidStack;

public class HUDHandlerCars implements IEntityComponentProvider {

    public static final HUDHandlerCars INSTANCE = new HUDHandlerCars();

    @Override
    public void appendTooltip(ITooltip iTooltip, EntityAccessor entityAccessor, IPluginConfig iPluginConfig) {
        if (entityAccessor.getTooltipPosition().equals(TooltipPosition.HEAD)) {
            iTooltip.remove(PluginCar.OBJECT_NAME_TAG);
            iTooltip.add(new TextComponent(String.format(Waila.CONFIG.get().getFormatting().getBlockName(), entityAccessor.getEntity().getDisplayName().getString())).withStyle(ChatFormatting.WHITE));
        } else if (entityAccessor.getTooltipPosition().equals(TooltipPosition.BODY)) {
            if (entityAccessor.getEntity() instanceof EntityGenericCar car) {
                FluidStack carFluid = car.getFluidInTank(0);
                if (!carFluid.isEmpty()) {
                    iTooltip.add(new TranslatableComponent("tooltip.waila.car.fuel", carFluid.getDisplayName()));
                    iTooltip.add(new TranslatableComponent("tooltip.waila.car.fuel_amount", carFluid.getAmount(), car.getMaxFuel()));
                }

                float damage = car.getDamage();
                if (damage > 0F) {
                    iTooltip.add(new TranslatableComponent("tooltip.waila.car.damage", MathUtils.round(damage, 2)));
                }
            }
        }
    }

}