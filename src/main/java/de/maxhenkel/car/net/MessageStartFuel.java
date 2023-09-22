package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;

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
    public void executeServerSide(CustomPayloadEvent.Context context) {
        BlockEntity te = context.getSender().level().getBlockEntity(pos);

        if (te instanceof TileEntityGasStation) {
            TileEntityGasStation tank = (TileEntityGasStation) te;
            tank.setFueling(start);
            tank.synchronize();
        }
    }

    @Override
    public MessageStartFuel fromBytes(FriendlyByteBuf buf) {
        this.start = buf.readBoolean();
        this.pos = buf.readBlockPos();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(start);
        buf.writeBlockPos(pos);
    }

}
