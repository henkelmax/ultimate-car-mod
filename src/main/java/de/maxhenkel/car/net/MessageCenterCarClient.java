package de.maxhenkel.car.net;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.NetworkEvent;

import java.util.UUID;

public class MessageCenterCarClient implements Message<MessageCenterCarClient> {

    private UUID uuid;

    public MessageCenterCarClient() {

    }

    public MessageCenterCarClient(Player player) {
        this.uuid = player.getUUID();
    }

    public MessageCenterCarClient(UUID uuid) {
        this.uuid = uuid;
    }

    @OnlyIn(Dist.CLIENT)
    public void centerClient() {
        Player player = Minecraft.getInstance().player;
        Player ridingPlayer = player.level().getPlayerByUUID(uuid);
        Entity riding = ridingPlayer.getVehicle();

        if (!(riding instanceof EntityCarBase)) {
            return;
        }

        EntityCarBase car = (EntityCarBase) riding;
        if (ridingPlayer.equals(car.getDriver())) {
            car.centerCar();
        }
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.CLIENT;
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {
        centerClient();
    }

    @Override
    public MessageCenterCarClient fromBytes(FriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

}
