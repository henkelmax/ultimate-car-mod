package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;

public class MessageSpawnCar extends MessageToServer<MessageSpawnCar> {

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
    public void execute(EntityPlayer player, MessageSpawnCar message) {
        TileEntity te = player.world.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));

        if (te instanceof TileEntityCarWorkshop) {
            ((TileEntityCarWorkshop) te).spawnCar(player);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }

}
