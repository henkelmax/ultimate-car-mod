package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class MessageGasStationAdminAmount implements Message<MessageGasStationAdminAmount> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "gas_station_amount");

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
    public void executeServerSide(PlayPayloadContext context) {
        if (!(context.player().orElse(null) instanceof ServerPlayer sender)) {
            return;
        }

        if (sender.level().getBlockEntity(pos) instanceof TileEntityGasStation gasStation) {
            gasStation.setTradeAmount(amount);
        }
    }

    @Override
    public MessageGasStationAdminAmount fromBytes(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.amount = buf.readInt();

        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(amount);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
