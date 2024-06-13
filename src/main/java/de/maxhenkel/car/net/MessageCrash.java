package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageCrash implements Message<MessageCrash> {

    public static final CustomPacketPayload.Type<MessageCrash> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, "crash"));

    private float speed;
    private UUID uuid;

    public MessageCrash() {

    }

    public MessageCrash(float speed, EntityCarBase car) {
        this.speed = speed;
        this.uuid = car.getUUID();
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

        if (!(sender.getVehicle() instanceof EntityCarBase car)) {
            return;
        }

        if (!car.getUUID().equals(uuid)) {
            return;
        }

        car.onCollision(speed);
    }

    @Override
    public MessageCrash fromBytes(RegistryFriendlyByteBuf buf) {
        this.speed = buf.readFloat();
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeFloat(speed);
        buf.writeUUID(uuid);
    }

    @Override
    public Type<MessageCrash> type() {
        return TYPE;
    }

}
