package de.maxhenkel.car.net;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MessageSpawnCar implements Message<MessageSpawnCar> {

    public static final CustomPacketPayload.Type<MessageSpawnCar> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(CarMod.MODID, "spawn_car"));

    private BlockPos pos;

    public MessageSpawnCar() {
    }

    public MessageSpawnCar(BlockPos pos) {
        this.pos = pos;
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

        if (sender.level().getBlockEntity(pos) instanceof TileEntityCarWorkshop workshop) {
            workshop.spawnCar(sender);
        }
    }

    @Override
    public MessageSpawnCar fromBytes(RegistryFriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public Type<MessageSpawnCar> type() {
        return TYPE;
    }

}
