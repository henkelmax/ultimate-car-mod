package de.maxhenkel.car.net;

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

    private int posX;
    private int posY;
    private int posZ;
    private CompoundNBT tag;

    public MessageSyncTileEntity() {

    }

    public MessageSyncTileEntity(BlockPos pos, CompoundNBT tileTag) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
        this.tag = tileTag;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {

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

        TileEntity te = player.world.getTileEntity(new BlockPos(posX, posY, posZ));

        if (te != null) {
            te.read(tag);
        }
    }

    @Override
    public MessageSyncTileEntity fromBytes(PacketBuffer buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.tag = buf.readCompoundTag();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        buf.writeCompoundTag(tag);
    }
}
