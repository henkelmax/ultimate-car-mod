package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageCarGui implements Message<MessageCarGui> {

    private boolean open;
    private UUID uuid;

    public MessageCarGui() {
        this.open = false;
        this.uuid = new UUID(0, 0);
    }

    public MessageCarGui(boolean open, PlayerEntity player) {
        this.open = open;
        this.uuid = player.getUniqueID();
    }


    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUniqueID().equals(uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }

        if (!open) {
            return;
        }

        Entity e = context.getSender().getRidingEntity();
        if (e instanceof EntityCarBase) {
            ((EntityCarBase) e).openCarGUi(context.getSender());
        }
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageCarGui fromBytes(PacketBuffer buf) {
        this.open = buf.readBoolean();
        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(open);
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }
}
