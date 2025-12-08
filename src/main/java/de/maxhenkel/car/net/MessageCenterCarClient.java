package de.maxhenkel.car.net;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageCenterCarClient implements Message<MessageCenterCarClient> {

    public static final CustomPacketPayload.Type<MessageCenterCarClient> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(CarMod.MODID, "center_car_client"));

    private UUID uuid;

    public MessageCenterCarClient() {

    }

    public MessageCenterCarClient(Player player) {
        this.uuid = player.getUUID();
    }

    public MessageCenterCarClient(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.CLIENTBOUND;
    }

    @Override
    public void executeClientSide(IPayloadContext context) {
        ClientNetworking.centerCar(uuid);
    }

    @Override
    public MessageCenterCarClient fromBytes(RegistryFriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    @Override
    public Type<MessageCenterCarClient> type() {
        return TYPE;
    }

}
