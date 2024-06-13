package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageCarHorn implements Message<MessageCarHorn> {

    public static final CustomPacketPayload.Type<MessageCarHorn> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, "car_horn"));

    private boolean pressed;
    private UUID uuid;

    public MessageCarHorn() {

    }

    public MessageCarHorn(boolean pressed, Player player) {
        this.pressed = pressed;
        this.uuid = player.getUUID();
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.SERVERBOUND;
    }

    @Override
    public void executeServerSide(IPayloadContext context) {
        if (!pressed) {
            return;
        }

        if (!(context.player() instanceof ServerPlayer sender)) {
            return;
        }

        if (!sender.getUUID().equals(uuid)) {
            Main.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        if (!(sender.getVehicle() instanceof EntityCarBase car)) {
            return;
        }

        if (sender.equals(car.getDriver())) {
            car.onHornPressed(sender);
        }
    }

    @Override
    public MessageCarHorn fromBytes(RegistryFriendlyByteBuf buf) {
        this.pressed = buf.readBoolean();
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(pressed);
        buf.writeUUID(uuid);
    }

    @Override
    public Type<MessageCarHorn> type() {
        return TYPE;
    }

}
