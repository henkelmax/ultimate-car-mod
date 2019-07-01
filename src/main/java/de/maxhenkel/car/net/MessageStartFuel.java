package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageStartFuel implements Message<MessageStartFuel> {

    private boolean start;
    private int posX;
    private int posY;
    private int posZ;

    public MessageStartFuel() {
    }

    public MessageStartFuel(BlockPos pos, boolean start) {
        posX = pos.getX();
        posY = pos.getY();
        posZ = pos.getZ();
        this.start = start;
    }


    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        TileEntity te = context.getSender().world.getTileEntity(new BlockPos(posX, posY, posZ));

        if (te instanceof TileEntityFuelStation) {
            TileEntityFuelStation tank = (TileEntityFuelStation) te;
            tank.setFueling(start);
            tank.synchronize();
        }
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageStartFuel fromBytes(PacketBuffer buf) {
        this.start = buf.readBoolean();
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(start);
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }
}
