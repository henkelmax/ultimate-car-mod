package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import de.maxhenkel.corelib.net.Message;
import de.maxhenkel.corelib.net.NetUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.UUID;

public class MessageCenterCar implements Message<MessageCenterCar> {

    private UUID uuid;

    public MessageCenterCar() {

    }

    public MessageCenterCar(Player player) {
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

        Entity riding = context.getSender().getVehicle();

        if (!(riding instanceof EntityCarBatteryBase)) {
            return;
        }

        EntityCarBatteryBase car = (EntityCarBatteryBase) riding;
        if (context.getSender().equals(car.getDriver())) {
            car.centerCar();
        }

        MessageCenterCarClient msg = new MessageCenterCarClient(uuid);

        context.getSender().serverLevel().getPlayers(player -> player.distanceTo(car) <= 128F).forEach(player -> NetUtils.sendTo(Main.SIMPLE_CHANNEL, player, msg));
    }

    @Override
    public MessageCenterCar fromBytes(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

}
