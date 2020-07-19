package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageEditSign implements Message<MessageEditSign> {

    private BlockPos pos;
    private String[] text;

    public MessageEditSign() {

    }

    public MessageEditSign(BlockPos pos, String[] text) {
        this.pos = pos;
        this.text = text;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        TileEntity te = context.getSender().world.getTileEntity(pos);

        if (te instanceof TileEntitySign) {
            ((TileEntitySign) te).setText(text);
        }
    }

    @Override
    public MessageEditSign fromBytes(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.text = new String[8];
        for (int i = 0; i < text.length; i++) {
            this.text[i] = buf.readString(20);
        }

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        for (int i = 0; i < text.length; i++) {
            buf.writeString(text[i], 20);
        }
    }

}
