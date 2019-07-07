package de.maxhenkel.car.net;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

public class MessageCenterCarClient implements Message<MessageCenterCarClient> {

    private UUID uuid;

    public MessageCenterCarClient() {
        this.uuid = new UUID(0, 0);
    }

    public MessageCenterCarClient(PlayerEntity player) {
        this.uuid = player.getUniqueID();
    }

    public MessageCenterCarClient(UUID uuid) {
        this.uuid = uuid;
    }

    @OnlyIn(Dist.CLIENT)
    public void centerClient() {
        PlayerEntity player = Minecraft.getInstance().player;
        PlayerEntity ridingPlayer = player.world.getPlayerByUuid(uuid);
        Entity riding = ridingPlayer.getRidingEntity();

        if (!(riding instanceof EntityCarBase)) {
            return;
        }

        EntityCarBase car = (EntityCarBase) riding;
        if (ridingPlayer.equals(car.getDriver())) {
            car.centerCar();
        }
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {

    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {
        centerClient();
    }

    @Override
    public MessageCenterCarClient fromBytes(PacketBuffer buf) {
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
