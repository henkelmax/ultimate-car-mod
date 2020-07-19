package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageGasStationAdminAmount implements Message<MessageGasStationAdminAmount> {

    private BlockPos pos;
    private int amount;

    public MessageGasStationAdminAmount() {

    }

    public MessageGasStationAdminAmount(BlockPos pos, int amount) {
        this.pos = pos;
        this.amount = amount;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        TileEntity te = context.getSender().world.getTileEntity(pos);

        if (te instanceof TileEntityGasStation) {
            ((TileEntityGasStation) te).setTradeAmount(amount);
        }
    }

    @Override
    public MessageGasStationAdminAmount fromBytes(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.amount = buf.readInt();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(amount);
    }

}
