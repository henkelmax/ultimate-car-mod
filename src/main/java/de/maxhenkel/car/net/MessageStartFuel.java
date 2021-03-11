package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageStartFuel implements Message<MessageStartFuel> {

    private boolean start;
    private BlockPos pos;

    public MessageStartFuel() {
    }

    public MessageStartFuel(BlockPos pos, boolean start) {
        this.pos = pos;
        this.start = start;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        TileEntity te = context.getSender().level.getBlockEntity(pos);

        if (te instanceof TileEntityGasStation) {
            TileEntityGasStation tank = (TileEntityGasStation) te;
            tank.setFueling(start);
            tank.synchronize();
        }
    }

    @Override
    public MessageStartFuel fromBytes(PacketBuffer buf) {
        this.start = buf.readBoolean();
        this.pos = buf.readBlockPos();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBoolean(start);
        buf.writeBlockPos(pos);
    }

}
