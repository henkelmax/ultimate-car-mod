package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSpawnCar implements Message<MessageSpawnCar> {

    private BlockPos pos;

    public MessageSpawnCar() {
    }

    public MessageSpawnCar(BlockPos pos) {
        this.pos = pos;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        TileEntity te = context.getSender().world.getTileEntity(pos);

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).spawnCar(context.getSender());
        }
    }

    @Override
    public MessageSpawnCar fromBytes(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
    }

}
