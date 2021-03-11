package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageCarGui implements Message<MessageCarGui> {

    private UUID uuid;

    public MessageCarGui() {

    }

    public MessageCarGui(PlayerEntity player) {
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
    public MessageCarGui fromBytes(PacketBuffer buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(uuid);
    }

}
