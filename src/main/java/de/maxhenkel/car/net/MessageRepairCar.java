package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

import java.util.UUID;

public class MessageRepairCar implements Message<MessageRepairCar> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "repair_car");

    private BlockPos pos;
    private UUID uuid;

    public MessageRepairCar() {

    }

    public MessageRepairCar(BlockPos pos, Player player) {
        this.pos = pos;
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

        if (sender.level().getBlockEntity(pos) instanceof TileEntityCarWorkshop workshop) {
            workshop.repairCar(sender);
        }
    }

    @Override
    public MessageRepairCar fromBytes(FriendlyByteBuf buf) {
        pos = buf.readBlockPos();
        uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUUID(uuid);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
