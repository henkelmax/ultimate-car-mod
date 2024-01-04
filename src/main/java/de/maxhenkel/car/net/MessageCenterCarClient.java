package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.UUID;

public class MessageCenterCarClient implements Message<MessageCenterCarClient> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "center_car_client");

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

        if (!(ridingPlayer.getVehicle() instanceof EntityCarBase car)) {
            return;
        }

        if (ridingPlayer.equals(car.getDriver())) {
            car.centerCar();
        }
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.CLIENTBOUND;
    }

    @Override
    public void executeClientSide(PlayPayloadContext context) {
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

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
