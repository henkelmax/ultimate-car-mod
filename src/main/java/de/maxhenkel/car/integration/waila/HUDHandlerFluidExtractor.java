package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.blocks.tileentity.TileEntityFluidExtractor;
import mcp.mobius.waila.api.IComponentProvider;
import mcp.mobius.waila.api.IDataAccessor;
import mcp.mobius.waila.api.IPluginConfig;
import mcp.mobius.waila.api.IServerDataProvider;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

public class HUDHandlerFluidExtractor implements IComponentProvider, IServerDataProvider<TileEntity> {

    static final HUDHandlerFluidExtractor INSTANCE = new HUDHandlerFluidExtractor();

    @Override
    public void appendBody(List<ITextComponent> tooltip, IDataAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("filter")) {
            Fluid fluid = ForgeRegistries.FLUIDS.getValue(new ResourceLocation(accessor.getServerData().getString("filter")));

            ITextComponent fluidComponent = new TranslationTextComponent("tooltip.waila.fluid_extractor.filter", new FluidStack(fluid, 1).getDisplayName());
            tooltip.add(fluidComponent);
        }
    }

    @Override
    public void appendServerData(CompoundNBT data, ServerPlayerEntity player, World world, TileEntity blockEntity) {
        TileEntityFluidExtractor extractor = (TileEntityFluidExtractor) blockEntity;

        Fluid filter = extractor.getFilterFluid();
        if (filter != null) {
            data.putString("filter", extractor.getFilterFluid().getRegistryName().toString());
        }
    }
}