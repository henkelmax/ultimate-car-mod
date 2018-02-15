package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class MessageFuelStationAdminAmount extends MessageToServer<MessageFuelStationAdminAmount> {

    private int posX;
    private int posY;
    private int posZ;
    private int amount;

    public MessageFuelStationAdminAmount() {

    }

    public MessageFuelStationAdminAmount(BlockPos pos, int amount) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
        this.amount = amount;
    }

    @Override
    public void execute(EntityPlayer player, MessageFuelStationAdminAmount message) {
        TileEntity te = player.world.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));

        if (te instanceof TileEntityFuelStation) {
            ((TileEntityFuelStation) te).setField(2, message.amount);

        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.amount = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        buf.writeInt(amount);
    }

}
