package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.entity.Entity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

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
    public void executeServerSide(NetworkEvent.Context context) {
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
    public MessageCrash fromBytes(PacketBuffer buf) {
        this.speed = buf.readFloat();
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeFloat(speed);
        buf.writeUUID(uuid);
    }

}
