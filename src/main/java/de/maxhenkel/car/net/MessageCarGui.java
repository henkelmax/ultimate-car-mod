package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MessageCarGui extends MessageToServer<MessageCarGui> {

    private boolean open;
    private UUID uuid;

    public MessageCarGui() {
        this.open = false;
        this.uuid = new UUID(0, 0);
    }

    public MessageCarGui(boolean open, EntityPlayer player) {
        this.open = open;
        this.uuid = player.getUniqueID();
    }

    @Override
    public void execute(EntityPlayer player, MessageCarGui message) {
        if (!player.getUniqueID().equals(message.uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }

        if (!message.open) {
            return;
        }

        Entity e = player.getRidingEntity();
        if (e instanceof EntityCarBase) {
            ((EntityCarBase) e).openCarGUi(player);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.open = buf.readBoolean();
        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeBoolean(open);
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

}
