package de.maxhenkel.car.net;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MessageStartFuel implements Message<MessageStartFuel> {

    public static final CustomPacketPayload.Type<MessageStartFuel> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(CarMod.MODID, "start_fuel"));

    private boolean start;
    private BlockPos pos;

    public MessageStartFuel() {
    }

    public MessageStartFuel(BlockPos pos, boolean start) {
        this.pos = pos;
        this.start = start;
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
            gasStation.setFueling(start);
            gasStation.synchronize();
        }
    }

    @Override
    public MessageStartFuel fromBytes(RegistryFriendlyByteBuf buf) {
        this.start = buf.readBoolean();
        this.pos = buf.readBlockPos();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(start);
        buf.writeBlockPos(pos);
    }

    @Override
    public Type<MessageStartFuel> type() {
        return TYPE;
    }

}
