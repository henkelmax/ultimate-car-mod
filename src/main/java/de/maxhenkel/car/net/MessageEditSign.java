package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageEditSign implements Message<MessageEditSign> {

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
    public void executeServerSide(NetworkEvent.Context context) {
        TileEntity te = context.getSender().world.getTileEntity(new BlockPos(posX, posY, posZ));

        if (te instanceof TileEntitySign) {
            ((TileEntitySign) te).setText(text);
        }
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageEditSign fromBytes(PacketBuffer buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();
        this.text = new String[8];
        for (int i = 0; i < text.length; i++) {
            this.text[i] = buf.readString();
        }

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
        for (int i = 0; i < text.length; i++) {
            buf.writeString(text[i]);
        }
    }
}
