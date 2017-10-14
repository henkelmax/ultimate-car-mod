package de.maxhenkel.car;

import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = Main.MODID)
public class Registry {

	@CapabilityInject(IFluidHandler.class)
	public static Capability<IFluidHandler> FLUID_HANDLER_CAPABILITY = null;
	
	@SubscribeEvent
	public static void capabilityAttach(AttachCapabilitiesEvent<TileEntity> event) {
		if (event.getObject() instanceof TileEntityBase && event.getObject() instanceof IFluidHandler) {
			final IFluidHandler handler = (IFluidHandler) event.getObject();
			event.addCapability(new ResourceLocation(Main.MODID, "fluid_handler"), new ICapabilityProvider() {

				@Override
				public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
					if (capability.equals(FLUID_HANDLER_CAPABILITY)) {
						return true;
					}
					return false;
				}

				@SuppressWarnings("unchecked")
				@Override
				public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
					if (capability.equals(FLUID_HANDLER_CAPABILITY)) {
						return (T) handler;
					}
					return null;
				}
			});
		}
	}

	
}
