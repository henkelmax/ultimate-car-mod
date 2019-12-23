package de.maxhenkel.car.integration.wawla;
/*
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
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
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class HUDHandlerTank implements IComponentProvider, IServerDataProvider<TileEntity> {

    static final HUDHandlerTank INSTANCE = new HUDHandlerTank();

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        FluidStack stack = FluidStack.loadFluidStackFromNBT(accessor.getServerData().getCompound("fluid"));

        if (stack.isEmpty()) {
            ITextComponent componentEmpty = new TranslationTextComponent("tooltip.waila.tank.no_fluid");
            tooltip.add(componentEmpty);
        } else {
            ITextComponent componentFluid = new TranslationTextComponent("tooltip.waila.tank.fluid", stack.getDisplayName());
            ITextComponent componentAmount = new TranslationTextComponent("tooltip.waila.tank.amount", stack.getAmount(), TileEntityTank.CAPACITY);
            tooltip.add(componentFluid);
            tooltip.add(componentAmount);
        }
    }

    @Override
    public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity blockEntity) {
        TileEntityTank tank = (TileEntityTank) blockEntity;

        data.put("fluid", tank.getFluid().writeToNBT(new CompoundNBT()));
    }
}*/