package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MessageGasStationAdminAmount implements Message<MessageGasStationAdminAmount> {

    public static final CustomPacketPayload.Type<MessageGasStationAdminAmount> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, "gas_station_amount"));

    private BlockPos pos;
    private int amount;

    public MessageGasStationAdminAmount() {

    }

    public MessageGasStationAdminAmount(BlockPos pos, int amount) {
        this.pos = pos;
        this.amount = amount;
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.SERVERBOUND;
    }

    @Override
    public void executeServerSide(IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer sender)) {
            return;
        }

        if (sender.level().getBlockEntity(pos) instanceof TileEntityGasStation gasStation) {
            gasStation.setTradeAmount(amount);
        }
    }

    @Override
    public MessageGasStationAdminAmount fromBytes(RegistryFriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.amount = buf.readInt();

        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(amount);
    }

    @Override
    public Type<MessageGasStationAdminAmount> type() {
        return TYPE;
    }

}
