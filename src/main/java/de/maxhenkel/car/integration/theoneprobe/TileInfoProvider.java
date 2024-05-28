package de.maxhenkel.car.integration.theoneprobe;

import de.maxhenkel.car.Main;
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

public class TileInfoProvider implements IProbeInfoProvider {

    public static final ResourceLocation ID = new ResourceLocation(Main.MODID, "probeinfoprovider");

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
            FluidStack fluid = generator.getFluidInTank(0);
            if (!fluid.isEmpty()) {
                iProbeInfo.tankHandler(generator, new ProgressStyle().suffix(" mb"));
            }
        } else if (te instanceof TileEntityTank tank) {
            FluidStack fluid = tank.getFluidInTank(0);
            if (!fluid.isEmpty()) {
                iProbeInfo.text(fluid.getHoverName());
                iProbeInfo.tankHandler(tank, new ProgressStyle().suffix(" mb"));
            }
        }
    }
}
