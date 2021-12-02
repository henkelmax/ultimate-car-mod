package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.network.NetworkEvent;

public class MessageCarHorn implements Message<MessageCarHorn> {

    private boolean pressed;
    private UUID uuid;

    public MessageCarHorn() {

    }

    public MessageCarHorn(boolean pressed, Player player) {
        this.pressed = pressed;
        this.uuid = player.getUUID();
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!pressed) {
            return;
        }

        if (!context.getSender().getUUID().equals(uuid)) {
            Main.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        Entity riding = context.getSender().getVehicle();

        if (!(riding instanceof EntityCarBase)) {
            return;
        }

        EntityCarBase car = (EntityCarBase) riding;
        if (context.getSender().equals(car.getDriver())) {
            car.onHornPressed(context.getSender());
        }
    }

    @Override
    public MessageCarHorn fromBytes(FriendlyByteBuf buf) {
        this.pressed = buf.readBoolean();
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(pressed);
        buf.writeUUID(uuid);
    }

}
