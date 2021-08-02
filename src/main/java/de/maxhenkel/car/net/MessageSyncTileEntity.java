package de.maxhenkel.car.net;

import de.maxhenkel.corelib.net.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

public class MessageSyncTileEntity implements Message<MessageSyncTileEntity> {

    private BlockPos pos;
    private CompoundTag tag;

    public MessageSyncTileEntity() {

    }

    public MessageSyncTileEntity(BlockPos pos, CompoundTag tileTag) {
        this.pos = pos;
        this.tag = tileTag;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.CLIENT;
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {
        sync();
    }

    @OnlyIn(Dist.CLIENT)
    private void sync() {
        Player player = Minecraft.getInstance().player;

        if (player == null || player.level == null) {
            return;
        }

        BlockEntity te = player.level.getBlockEntity(pos);

        if (te != null) {
            te.load(tag);
        }
    }

    @Override
    public MessageSyncTileEntity fromBytes(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.tag = buf.readNbt();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }

}
