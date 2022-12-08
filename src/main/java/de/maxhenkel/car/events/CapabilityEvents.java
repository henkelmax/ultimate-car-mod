package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class CapabilityEvents {

    @SubscribeEvent
    public void capabilityAttachTileEntity(AttachCapabilitiesEvent<BlockEntity> event) {
        if (event.getObject() == null) {
            return;
        }
        ResourceLocation key = ForgeRegistries.BLOCK_ENTITY_TYPES.getKey(event.getObject().getType());
        if (key == null || !key.getNamespace().equals(Main.MODID)) {
            return;
        }
        if (event.getObject() instanceof IFluidHandler) {
            IFluidHandler handler = (IFluidHandler) event.getObject();
            event.addCapability(new ResourceLocation(Main.MODID, "fluid"), new ICapabilityProvider() {

                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap.equals(ForgeCapabilities.FLUID_HANDLER)) {
                        return LazyOptional.of(() -> (T) handler);
                    }
                    return LazyOptional.empty();
                }
            });
        }
        if (event.getObject() instanceof IEnergyStorage) {
            IEnergyStorage handler = (IEnergyStorage) event.getObject();
            event.addCapability(new ResourceLocation(Main.MODID, "energy"), new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap.equals(ForgeCapabilities.ENERGY)) {
                        return LazyOptional.of(() -> (T) handler);
                    }
                    return LazyOptional.empty();
                }
            });
        }
    }

    @SubscribeEvent
    public void capabilityAttachEntity(AttachCapabilitiesEvent<Entity> event) {
        if (event.getObject() == null) {
            return;
        }
        ResourceLocation key = ForgeRegistries.ENTITY_TYPES.getKey(event.getObject().getType());
        if (key == null || !key.getNamespace().equals(Main.MODID)) {
            return;
        }
        if (event.getObject() instanceof IFluidHandler) {
            IFluidHandler handler = (IFluidHandler) event.getObject();
            event.addCapability(new ResourceLocation(Main.MODID, "fluid"), new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap.equals(ForgeCapabilities.FLUID_HANDLER)) {
                        return LazyOptional.of(() -> (T) handler);
                    }
                    return LazyOptional.empty();
                }
            });
        }
        if (event.getObject() instanceof IEnergyStorage) {
            IEnergyStorage handler = (IEnergyStorage) event.getObject();
            event.addCapability(new ResourceLocation(Main.MODID, "energy"), new ICapabilityProvider() {
                @Nonnull
                @Override
                public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
                    if (cap.equals(ForgeCapabilities.ENERGY)) {
                        return LazyOptional.of(() -> (T) handler);
                    }
                    return LazyOptional.empty();
                }
            });
        }
    }
}
