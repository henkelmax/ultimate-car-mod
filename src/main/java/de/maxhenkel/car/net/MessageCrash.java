package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageCrash implements Message<MessageCrash> {

    private float speed;
    private UUID uuid;

    public MessageCrash() {
        this.speed = 0;
        this.uuid = new UUID(0, 0);
    }

    public MessageCrash(float speed, EntityCarBase car) {
        this.speed = speed;
        this.uuid = car.getUniqueID();
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        Entity riding = context.getSender().getRidingEntity();

        if (!(riding instanceof EntityCarBase)) {
            return;
        }

        EntityCarBase car = (EntityCarBase) riding;

        if (!car.getUniqueID().equals(uuid)) {
            return;
        }

        car.onCollision(speed);
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageCrash fromBytes(PacketBuffer buf) {
        this.speed = buf.readFloat();

        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(speed);
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }
}
