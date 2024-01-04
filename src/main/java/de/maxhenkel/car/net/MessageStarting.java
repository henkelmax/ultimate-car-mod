package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.UUID;

public class MessageStarting implements Message<MessageStarting> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "starting");

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
    public void executeServerSide(PlayPayloadContext context) {
        if (!(context.player().orElse(null) instanceof ServerPlayer sender)) {
            return;
        }

        if (!sender.getUUID().equals(uuid)) {
            Main.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
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
    public MessageStarting fromBytes(FriendlyByteBuf buf) {
        this.start = buf.readBoolean();
        this.playSound = buf.readBoolean();

        this.uuid = buf.readUUID();

        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(start);
        buf.writeBoolean(playSound);

        buf.writeUUID(uuid);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
