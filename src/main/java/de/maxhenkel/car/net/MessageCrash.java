package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;

public class MessageCrash implements Message<MessageCrash> {

    private float speed;
    private UUID uuid;

    public MessageCrash() {

    }

    public MessageCrash(float speed, EntityCarBase car) {
        this.speed = speed;
        this.uuid = car.getUUID();
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(CustomPayloadEvent.Context context) {
        Entity riding = context.getSender().getVehicle();

        if (!(riding instanceof EntityCarBase)) {
            return;
        }

        EntityCarBase car = (EntityCarBase) riding;

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

}
