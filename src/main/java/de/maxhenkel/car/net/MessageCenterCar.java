package de.maxhenkel.car.net;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageCenterCar implements Message<MessageCenterCar> {

    public static final CustomPacketPayload.Type<MessageCenterCar> TYPE = new CustomPacketPayload.Type<>(Identifier.fromNamespaceAndPath(CarMod.MODID, "center_car"));

    private UUID uuid;

    public MessageCenterCar() {

    }

    public MessageCenterCar(Player player) {
        this.uuid = player.getUUID();
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

        if (!sender.getUUID().equals(uuid)) {
            CarMod.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        if (!(sender.getVehicle() instanceof EntityCarBatteryBase car)) {
            return;
        }

        if (sender.equals(car.getDriver())) {
            car.centerCar();
        }

        PacketDistributor.sendToPlayersTrackingEntity(car, new MessageCenterCarClient(uuid));
    }

    @Override
    public MessageCenterCar fromBytes(RegistryFriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    @Override
    public Type<MessageCenterCar> type() {
        return TYPE;
    }

}
