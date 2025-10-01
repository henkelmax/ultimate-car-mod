package de.maxhenkel.car.integration.theoneprobe;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import mcjty.theoneprobe.api.IProbeHitData;
import mcjty.theoneprobe.api.IProbeInfo;
import mcjty.theoneprobe.api.IProbeInfoProvider;
import mcjty.theoneprobe.api.ProbeMode;
import mcjty.theoneprobe.apiimpl.styles.ProgressStyle;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;

public class TileInfoProvider implements IProbeInfoProvider {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "probeinfoprovider");

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public void addProbeInfo(ProbeMode probeMode, IProbeInfo iProbeInfo, Player playerEntity, Level world, BlockState blockState, IProbeHitData iProbeHitData) {
        BlockEntity te = world.getBlockEntity(iProbeHitData.getPos());

        if (te instanceof TileEntityFluidExtractor extractor) {
            Fluid fluid = extractor.getFilterFluid();
            if (fluid != null) {
                iProbeInfo.text(Component.translatable("tooltip.waila.fluid_extractor.filter", new FluidStack(fluid, 1).getHoverName()));
            }
        } else if (te instanceof TileEntityGenerator generator) {
            if (generator.getAmountAsLong(0) > 0L) {
                iProbeInfo.tankHandler(IFluidHandler.of(generator), new ProgressStyle().suffix(" mb"));
            }
        } else if (te instanceof TileEntityTank tank) {
            FluidStack fluid = tank.getFluid();
            if (!fluid.isEmpty()) {
                iProbeInfo.text(fluid.getHoverName());
                iProbeInfo.tankHandler(IFluidHandler.of(tank), new ProgressStyle().suffix(" mb"));
            }
        }
    }
}
