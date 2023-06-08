package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.network.NetworkEvent;

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
        BlockEntity te = context.getSender().level().getBlockEntity(pos);

        if (te instanceof TileEntityGasStation) {
            ((TileEntityGasStation) te).setTradeAmount(amount);
        }
    }

    @Override
    public MessageGasStationAdminAmount fromBytes(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.amount = buf.readInt();

        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeInt(amount);
    }

}
