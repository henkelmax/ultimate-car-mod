package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.network.CustomPayloadEvent;

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
    public void executeServerSide(CustomPayloadEvent.Context context) {
        BlockEntity te = context.getSender().level().getBlockEntity(pos);

        if (te instanceof TileEntitySign) {
            ((TileEntitySign) te).setText(text);
        }
    }

    @Override
    public MessageEditSign fromBytes(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.text = new String[8];
        for (int i = 0; i < text.length; i++) {
            this.text[i] = buf.readUtf(64);
        }

        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        for (String s : text) {
            buf.writeUtf(s, 64);
        }
    }

}
