package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.net.MessageSyncTileEntity;
import de.maxhenkel.corelib.codec.CodecUtils;
import de.maxhenkel.corelib.codec.ValueInputOutputUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentSerialization;
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
import net.minecraft.world.level.storage.TagValueOutput;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.energy.EnergyHandler;
import net.neoforged.neoforge.transfer.fluid.FluidResource;
import net.neoforged.neoforge.transfer.item.ItemResource;

import javax.annotation.Nullable;
import java.util.Optional;

public abstract class TileEntityBase extends BlockEntity implements Nameable {

    private Component name;
    private CompoundTag compoundLast;

    public TileEntityBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public void synchronize() {
        if (!level.isClientSide() && level instanceof ServerLevel serverLevel) {
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
        TagValueOutput valueOutput = ValueInputOutputUtils.createValueOutput(this, provider);
        saveAdditional(valueOutput);
        return ValueInputOutputUtils.toTag(valueOutput);
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
    public void saveAdditional(ValueOutput valueOutput) {
        super.saveAdditional(valueOutput);
        if (name != null) {
            CodecUtils.toJsonString(ComponentSerialization.CODEC, name).ifPresent(s -> valueOutput.putString("CustomName", s));
        }
    }

    @Override
    public void loadAdditional(ValueInput valueInput) {
        Optional<String> optionalName = valueInput.getString("CustomName");
        optionalName.ifPresent(s -> name = CodecUtils.fromJson(ComponentSerialization.CODEC, s).orElse(null));
        super.loadAdditional(valueInput);
    }

    @Nullable
    public ResourceHandler<FluidResource> getFluidHandler() {
        return null;
    }

    @Nullable
    public EnergyHandler getEnergyStorage() {
        return null;
    }

    @Nullable
    public ResourceHandler<ItemResource> getItemHandler() {
        return null;
    }

}
