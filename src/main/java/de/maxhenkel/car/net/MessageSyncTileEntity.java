package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MessageSyncTileEntity implements Message<MessageSyncTileEntity> {

    public static final CustomPacketPayload.Type<MessageSyncTileEntity> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, "sync_block_entity"));

    private BlockPos pos;
    private CompoundTag tag;

    public MessageSyncTileEntity() {

    }

    public MessageSyncTileEntity(BlockPos pos, CompoundTag tileTag) {
        this.pos = pos;
        this.tag = tileTag;
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.CLIENTBOUND;
    }

    @Override
    public void executeClientSide(IPayloadContext context) {
        ClientNetworking.syncTileEntity(pos, tag);
    }

    @Override
    public MessageSyncTileEntity fromBytes(RegistryFriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.tag = buf.readNbt();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }

    @Override
    public Type<MessageSyncTileEntity> type() {
        return TYPE;
    }

}
