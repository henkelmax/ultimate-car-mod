package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageEditSign extends MessageToServer<MessageEditSign> {

    private int posX;
    private int posY;
    private int posZ;
    private String[] text;

    public MessageEditSign() {

    }

    public MessageEditSign(BlockPos pos, String[] text) {
        this.posX = pos.getX();
        this.posY = pos.getY();
        this.posZ = pos.getZ();
        this.text = text;
    }

    @Override
    public void execute(EntityPlayer player, MessageEditSign message) {
        TileEntity te = player.world.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));

        if (te instanceof TileEntitySign) {
            ((TileEntitySign) te).setText(message.text);
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.text = new String[8];
        for (int i = 0; i < text.length; i++) {
            this.text[i] = ByteBufUtils.readUTF8String(buf);
        }

    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        for (int i = 0; i < text.length; i++) {
            ByteBufUtils.writeUTF8String(buf, text[i]);
        }
    }

}
