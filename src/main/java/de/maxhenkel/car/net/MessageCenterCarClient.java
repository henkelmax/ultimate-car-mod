package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageCenterCarClient implements Message<MessageCenterCarClient> {

    public static final CustomPacketPayload.Type<MessageCenterCarClient> TYPE = new CustomPacketPayload.Type<>(new ResourceLocation(Main.MODID, "center_car_client"));

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
    public void executeClientSide(IPayloadContext context) {
        centerClient();
    }

    @Override
    public MessageCenterCarClient fromBytes(RegistryFriendlyByteBuf buf) {
        this.uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(uuid);
    }

    @Override
    public Type<MessageCenterCarClient> type() {
        return TYPE;
    }

}
