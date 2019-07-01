package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityEvents {

    @SubscribeEvent
    public void capabilityAttach(AttachCapabilitiesEvent<TileEntity> event) {
        if (!(event.getObject() instanceof TileEntityBase)) {
            return;
        }
        if (event.getObject() instanceof IFluidHandler) {
            IFluidHandler handler = (IFluidHandler) event.getObject();
            event.addCapability(new ResourceLocation(Main.MODID, "fluid"), new ICapabilityProvider() {

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap.equals(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY)) {
                        return LazyOptional.<T>of(() -> (T) handler);
                    }
                    return null;
                }
            });
        }
        if (event.getObject() instanceof IEnergyStorage) {
            IEnergyStorage handler = (IEnergyStorage) event.getObject();
            event.addCapability(new ResourceLocation(Main.MODID, "energy"), new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap.equals(CapabilityEnergy.ENERGY)) {
                        return LazyOptional.<T>of(() -> (T) handler);
                    }
                    return null;
                }
            });
        }
    }

}
