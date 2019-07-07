package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkDirection;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

public class MessageCenterCar implements Message<MessageCenterCar> {

    private UUID uuid;

    public MessageCenterCar() {
        this.uuid = new UUID(0, 0);
    }

    public MessageCenterCar(PlayerEntity player) {
        this.uuid = player.getUniqueID();
    }

    public MessageCenterCar(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUniqueID().equals(uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }

        Entity riding = context.getSender().getRidingEntity();

        if (!(riding instanceof EntityCarBatteryBase)) {
            return;
        }

        EntityCarBatteryBase car = (EntityCarBatteryBase) riding;
        if (context.getSender().equals(car.getDriver())) {
            car.centerCar();
        }

        MessageCenterCarClient msg = new MessageCenterCarClient(uuid);
        context.getSender().getServerWorld().getPlayers(player -> player.getDistance(car) <= 128F).forEach(player -> Main.SIMPLE_CHANNEL.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT));
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageCenterCar fromBytes(PacketBuffer buf) {
        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }
}
