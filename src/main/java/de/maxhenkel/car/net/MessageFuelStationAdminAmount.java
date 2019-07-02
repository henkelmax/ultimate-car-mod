package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageFuelStationAdminAmount implements Message<MessageFuelStationAdminAmount> {

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
    public void executeServerSide(NetworkEvent.Context context) {
        TileEntity te = context.getSender().world.getTileEntity(new BlockPos(posX, posY, posZ));

        if (te instanceof TileEntityFuelStation) {
            ((TileEntityFuelStation) te).setTradeAmount(amount);
        }
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageFuelStationAdminAmount fromBytes(PacketBuffer buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.amount = buf.readInt();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        buf.writeInt(amount);
    }
}
