package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.UUID;

public class MessageCarGui implements Message<MessageCarGui> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "car_gui");

    private UUID uuid;

    public MessageCarGui() {

    }

    public MessageCarGui(Player player) {
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
        if (sender.getVehicle() instanceof EntityCarBase car) {
            car.openCarGUI(sender);
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

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
