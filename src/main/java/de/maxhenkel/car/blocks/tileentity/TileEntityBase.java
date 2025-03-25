package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.net.MessageSyncTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Nameable;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.energy.IEnergyStorage;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.network.PacketDistributor;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends BlockEntity implements Nameable {

    private Component name;
    private CompoundTag compoundLast;

    public TileEntityBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public void synchronize() {
        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            CompoundTag last = getUpdateTag(serverLevel.registryAccess());
            if (compoundLast == null || !compoundLast.equals(last)) {
                PacketDistributor.sendToPlayersTrackingChunk(serverLevel, new ChunkPos(getBlockPos()), new MessageSyncTileEntity(worldPosition, last));
                compoundLast = last;
            }
        }
    }

    public void synchronize(int ticks) {
        if (level.getGameTime() % ticks == 0) {
            synchronize();
        }
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider provider) {
        CompoundTag updateTag = super.getUpdateTag(provider);
        saveAdditional(updateTag, provider);
        return updateTag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public abstract Component getTranslatedName();

    public void setCustomName(Component name) {
        this.name = name;
    }

    @Override
    public Component getName() {
        return name != null ? name : getTranslatedName();
    }

    @Override
    @Nullable
    public Component getCustomName() {
        return name;
    }

    public abstract ContainerData getFields();

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        if (name != null) {
            compound.putString("CustomName", Component.Serializer.toJson(name, provider));
        }
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        name = compound.getString("CustomName").map(s -> Component.Serializer.fromJson(s, provider)).orElse(null);
        super.loadAdditional(compound, provider);
    }

    @Nullable
    public IFluidHandler getFluidHandler() {
        if (this instanceof IFluidHandler fluidHandler) {
            return fluidHandler;
        }
        return null;
    }

    @Nullable
    public IEnergyStorage getEnergyStorage() {
        if (this instanceof IEnergyStorage energyStorage) {
            return energyStorage;
        }
        return null;
    }

    @Nullable
    public IItemHandler getItemHandler() {
        if (this instanceof IItemHandler itemHandler) {
            return itemHandler;
        }
        return null;
    }

}
