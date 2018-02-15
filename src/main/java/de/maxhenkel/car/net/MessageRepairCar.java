package de.maxhenkel.car.net;

import java.util.UUID;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class MessageRepairCar extends MessageToServer<MessageRepairCar> {

    private int posX;
    private int posY;
    private int posZ;
    private UUID uuid;

    public MessageRepairCar() {
        this.uuid = new UUID(0, 0);
    }

    public MessageRepairCar(BlockPos pos, EntityPlayer player) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
        this.uuid = player.getUniqueID();
    }

    @Override
    public void execute(EntityPlayer player, MessageRepairCar message) {
        if (!player.getUniqueID().equals(message.uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }

        TileEntity te = player.world.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).repairCar(player);

        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();

        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);

        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
    }

}
