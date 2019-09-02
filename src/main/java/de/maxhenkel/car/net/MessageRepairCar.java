package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageRepairCar implements Message<MessageRepairCar> {

    private BlockPos pos;
    private UUID uuid;

    public MessageRepairCar() {

    }

    public MessageRepairCar(BlockPos pos, PlayerEntity player) {
        this.pos = pos;
        this.uuid = player.getUniqueID();
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUniqueID().equals(uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }

        TileEntity te = context.getSender().world.getTileEntity(pos);

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).repairCar(context.getSender());
        }
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageRepairCar fromBytes(PacketBuffer buf) {
        pos = buf.readBlockPos();
        uuid = buf.readUniqueId();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeUniqueId(uuid);
    }
}
