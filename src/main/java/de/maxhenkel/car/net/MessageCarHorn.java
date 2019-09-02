package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageCarHorn implements Message<MessageCarHorn> {

    private boolean pressed;
    private UUID uuid;

    public MessageCarHorn() {

    }

    public MessageCarHorn(boolean pressed, PlayerEntity player) {
        this.pressed = pressed;
        this.uuid = player.getUniqueID();
    }


    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!pressed) {
            return;
        }

        if (!context.getSender().getUniqueID().equals(uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }

        Entity riding = context.getSender().getRidingEntity();

        if (!(riding instanceof EntityCarBase)) {
            return;
        }

        EntityCarBase car = (EntityCarBase) riding;
        if (context.getSender().equals(car.getDriver())) {
            car.onHornPressed(context.getSender());
        }
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageCarHorn fromBytes(PacketBuffer buf) {
        this.pressed = buf.readBoolean();
        this.uuid = buf.readUniqueId();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(pressed);
        buf.writeUniqueId(uuid);
    }
}
