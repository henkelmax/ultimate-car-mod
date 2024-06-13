package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageRepairCar implements Message<MessageRepairCar> {

    public static final CustomPacketPayload.Type<MessageRepairCar> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, "repair_car"));

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
    public void executeServerSide(IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer sender)) {
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
    public MessageRepairCar fromBytes(RegistryFriendlyByteBuf buf) {
        pos = buf.readBlockPos();
        uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUUID(uuid);
    }

    @Override
    public Type<MessageRepairCar> type() {
        return TYPE;
    }

}
