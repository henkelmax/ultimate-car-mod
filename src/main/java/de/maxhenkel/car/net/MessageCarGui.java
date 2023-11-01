package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.network.NetworkEvent;

public class MessageCarGui implements Message<MessageCarGui> {

    private UUID uuid;

    public MessageCarGui() {

    }

    public MessageCarGui(Player player) {
        this.uuid = player.getUUID();
    }


    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUUID().equals(uuid)) {
            Main.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        Entity e = context.getSender().getVehicle();
        if (e instanceof EntityCarBase) {
            ((EntityCarBase) e).openCarGUI(context.getSender());
        }
    }

    @Override
    public MessageCarGui fromBytes(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

}
