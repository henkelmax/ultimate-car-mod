package de.maxhenkel.car.net;

import de.maxhenkel.corelib.net.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageSyncTileEntity implements Message<MessageSyncTileEntity> {

    private BlockPos pos;
    private CompoundNBT tag;

    public MessageSyncTileEntity() {

    }

    public MessageSyncTileEntity(BlockPos pos, CompoundNBT tileTag) {
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
        PlayerEntity player = Minecraft.getInstance().player;

        if (player == null || player.world == null) {
            return;
        }

        TileEntity te = player.world.getTileEntity(pos);

        if (te != null) {
            te.func_230337_a_(te.getBlockState(), tag);
        }
    }

    @Override
    public MessageSyncTileEntity fromBytes(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.tag = buf.readCompoundTag();
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeCompoundTag(tag);
    }

}
