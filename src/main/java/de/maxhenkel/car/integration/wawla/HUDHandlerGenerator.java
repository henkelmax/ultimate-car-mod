package de.maxhenkel.car.integration.wawla;

import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;

import java.util.List;

public class HUDHandlerGenerator implements IComponentProvider, IServerDataProvider<TileEntity> {

    static final HUDHandlerGenerator INSTANCE = new HUDHandlerGenerator();

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        int energy = accessor.getServerData().getInt("energy");
        int maxEnergy = accessor.getServerData().getInt("max_energy");
        int fluid = accessor.getServerData().getInt("fluid");
        int maxFluid = accessor.getServerData().getInt("max_fluid");

        ITextComponent energyComponent = new TranslationTextComponent("tooltip.waila.generator.energy", energy, maxEnergy);
        ITextComponent fluidComponent = new TranslationTextComponent("tooltip.waila.generator.fluid", fluid, maxFluid);
        tooltip.add(energyComponent);
        tooltip.add(fluidComponent);
    }

    @Override
    public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity blockEntity) {
        TileEntityGenerator generator = (TileEntityGenerator) blockEntity;

        data.putInt("energy", generator.getEnergyStored());
        data.putInt("max_energy", generator.getMaxEnergyStored());
        data.putInt("fluid", generator.getFluidInTank(0).getAmount());
        data.putInt("max_fluid", generator.getTankCapacity(0));
    }
}