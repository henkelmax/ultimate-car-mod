package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSpawnCar implements Message<MessageSpawnCar> {

    private int posX;
    private int posY;
    private int posZ;

    public MessageSpawnCar() {
    }

    public MessageSpawnCar(BlockPos pos) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        TileEntity te = context.getSender().world.getTileEntity(new BlockPos(posX, posY, posZ));

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).spawnCar(context.getSender());
        }
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageSpawnCar fromBytes(PacketBuffer buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }
}
