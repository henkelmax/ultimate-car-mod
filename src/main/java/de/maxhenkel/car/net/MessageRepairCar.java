package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageRepairCar implements Message<MessageRepairCar> {

    private int posX;
    private int posY;
    private int posZ;
    private UUID uuid;

    public MessageRepairCar() {
        this.uuid = new UUID(0, 0);
    }

    public MessageRepairCar(BlockPos pos, PlayerEntity player) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
        this.uuid = player.getUniqueID();
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUniqueID().equals(uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }

        TileEntity te = context.getSender().world.getTileEntity(new BlockPos(posX, posY, posZ));

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).repairCar(context.getSender());
        }
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageRepairCar fromBytes(PacketBuffer buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();

        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);

        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }
}
