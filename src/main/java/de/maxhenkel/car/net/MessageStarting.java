package de.maxhenkel.car.net;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageStarting implements Message<MessageStarting> {

    public static final CustomPacketPayload.Type<MessageStarting> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "starting"));

    private boolean start;
    private boolean playSound;
    private UUID uuid;

    public MessageStarting() {

    }

    public MessageStarting(boolean start, boolean playSound, Player player) {
        this.start = start;
        this.playSound = playSound;
        this.uuid = player.getUUID();
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.SERVERBOUND;
    }

    @Override
    public void executeServerSide(IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer sender)) {
            return;
        }

        if (!sender.getUUID().equals(uuid)) {
            CarMod.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        if (!(sender.getVehicle() instanceof EntityCarBatteryBase car)) {
            return;
        }

        if (sender.equals(car.getDriver())) {
            car.setStarting(start, playSound);
        }
    }

    @Override
    public MessageStarting fromBytes(RegistryFriendlyByteBuf buf) {
        this.start = buf.readBoolean();
        this.playSound = buf.readBoolean();

        this.uuid = buf.readUUID();

        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBoolean(start);
        buf.writeBoolean(playSound);

        buf.writeUUID(uuid);
    }

    @Override
    public Type<MessageStarting> type() {
        return TYPE;
    }

}
