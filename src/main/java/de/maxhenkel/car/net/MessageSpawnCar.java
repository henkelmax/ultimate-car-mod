package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

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
        BlockEntity te = context.getSender().level.getBlockEntity(pos);

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).spawnCar(context.getSender());
        }
    }

    @Override
    public MessageSpawnCar fromBytes(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

}
