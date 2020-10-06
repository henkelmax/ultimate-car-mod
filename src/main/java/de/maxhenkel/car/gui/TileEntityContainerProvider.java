package de.maxhenkel.car.gui;

import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.network.NetworkHooks;

import javax.annotation.Nullable;

public class TileEntityContainerProvider implements INamedContainerProvider {

    private ContainerCreator container;
    private TileEntityBase tileEntity;

    public TileEntityContainerProvider(ContainerCreator container, TileEntityBase tileEntity) {
        this.container = container;
        this.tileEntity = tileEntity;
    }

    @Override
    public ITextComponent getDisplayName() {
        return tileEntity.getDisplayName();
    }

    public static void openGui(ServerPlayerEntity player, TileEntityBase tileEntity, ContainerCreator containerCreator) {
        NetworkHooks.openGui(player, new TileEntityContainerProvider(containerCreator, tileEntity), packetBuffer -> packetBuffer.writeBlockPos(tileEntity.getPos()));
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return container.create(i, playerInventory, playerEntity);
    }

    public interface ContainerCreator {
        Container create(int i, PlayerInventory playerInventory, PlayerEntity playerEntity);
    }
}
