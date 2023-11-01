package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.neoforge.network.NetworkEvent;

public class MessageRepairCar implements Message<MessageRepairCar> {

    private BlockPos pos;
    private UUID uuid;

    public MessageRepairCar() {

    }

    public MessageRepairCar(BlockPos pos, Player player) {
        this.pos = pos;
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

        BlockEntity te = context.getSender().level().getBlockEntity(pos);

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).repairCar(context.getSender());
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

}
