package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkHooks;

import javax.annotation.Nullable;

public class TileEntityContainerProvider implements MenuProvider {

    private ContainerCreator container;
    private TileEntityBase tileEntity;

    public TileEntityContainerProvider(ContainerCreator container, TileEntityBase tileEntity) {
        this.container = container;
        this.tileEntity = tileEntity;
    }

    @Override
    public Component getDisplayName() {
        return tileEntity.getDisplayName();
    }

    public static void openGui(ServerPlayer player, TileEntityBase tileEntity, ContainerCreator containerCreator) {
        NetworkHooks.openGui(player, new TileEntityContainerProvider(containerCreator, tileEntity), packetBuffer -> packetBuffer.writeBlockPos(tileEntity.getBlockPos()));
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int i, Inventory playerInventory, Player playerEntity) {
        return container.create(i, playerInventory, playerEntity);
    }

    public interface ContainerCreator {
        AbstractContainerMenu create(int i, Inventory playerInventory, Player playerEntity);
    }
}
