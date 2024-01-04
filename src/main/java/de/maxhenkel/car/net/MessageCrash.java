package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.UUID;

public class MessageCrash implements Message<MessageCrash> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "crash");

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
    public void executeServerSide(PlayPayloadContext context) {
        if (!(context.player().orElse(null) instanceof ServerPlayer sender)) {
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
    public MessageCrash fromBytes(FriendlyByteBuf buf) {
        this.speed = buf.readFloat();
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeFloat(speed);
        buf.writeUUID(uuid);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
