package de.maxhenkel.car.integration.waila;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.fluids.FluidStack;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public class HUDHandlerTank implements IBlockComponentProvider, IServerDataProvider<BlockEntity> {

    public static final HUDHandlerTank INSTANCE = new HUDHandlerTank();

    private static final ResourceLocation UID = new ResourceLocation(Main.MODID, "tank");

    @Override
    public void appendTooltip(ITooltip iTooltip, BlockAccessor blockAccessor, IPluginConfig iPluginConfig) {
        FluidStack stack = FluidStack.loadFluidStackFromNBT(blockAccessor.getServerData().getCompound("fluid"));

        if (stack.isEmpty()) {
            iTooltip.add(Component.translatable("tooltip.waila.tank.no_fluid"));
        } else {
            iTooltip.add(Component.translatable("tooltip.waila.tank.fluid", stack.getDisplayName()));
            iTooltip.add(Component.translatable("tooltip.waila.tank.amount", stack.getAmount(), TileEntityTank.CAPACITY));
        }
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, ServerPlayer serverPlayer, Level level, BlockEntity blockEntity, boolean b) {
        TileEntityTank tank = (TileEntityTank) blockEntity;
        compoundTag.put("fluid", tank.getFluid().writeToNBT(new CompoundTag()));
    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }
}