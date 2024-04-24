package de.maxhenkel.car.gui;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.network.IContainerFactory;

public class ContainerFactoryTileEntity<T extends AbstractContainerMenu, U extends BlockEntity> implements IContainerFactory<T> {

    private final ContainerCreator<T, U> containerCreator;

    public ContainerFactoryTileEntity(ContainerCreator<T, U> containerCreator) {
        this.containerCreator = containerCreator;
    }

    @Override
    public T create(int windowId, Inventory inv, RegistryFriendlyByteBuf data) {
        BlockEntity te = inv.player.level().getBlockEntity(data.readBlockPos());
        try {
            return containerCreator.create(windowId, (U) te, inv);
        } catch (ClassCastException e) {
            return null;
        }
    }

    public interface ContainerCreator<T extends AbstractContainerMenu, U extends BlockEntity> {
        T create(int windowId, U tileEntity, Inventory inv);
    }
}
