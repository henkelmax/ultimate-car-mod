package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageRepairCar implements Message<MessageRepairCar> {

    private BlockPos pos;
    private UUID uuid;

    public MessageRepairCar() {

    }

    public MessageRepairCar(BlockPos pos, PlayerEntity player) {
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

        TileEntity te = context.getSender().level.getBlockEntity(pos);

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).repairCar(context.getSender());
        }
    }

    @Override
    public MessageRepairCar fromBytes(PacketBuffer buf) {
        pos = buf.readBlockPos();
        uuid = buf.readUUID();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeUUID(uuid);
    }

}
