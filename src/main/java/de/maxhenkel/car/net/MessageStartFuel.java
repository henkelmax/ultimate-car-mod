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

public class MessageStartFuel implements Message<MessageStartFuel> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "start_fuel");

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
    public void executeServerSide(PlayPayloadContext context) {
        if (!(context.player().orElse(null) instanceof ServerPlayer sender)) {
            return;
        }

        if (sender.level().getBlockEntity(pos) instanceof TileEntityGasStation gasStation) {
            gasStation.setFueling(start);
            gasStation.synchronize();
        }
    }

    @Override
    public MessageStartFuel fromBytes(FriendlyByteBuf buf) {
        this.start = buf.readBoolean();
        this.pos = buf.readBlockPos();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(start);
        buf.writeBlockPos(pos);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
