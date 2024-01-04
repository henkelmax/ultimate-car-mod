package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class MessageEditSign implements Message<MessageEditSign> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "edit_sign");

    private BlockPos pos;
    private String[] text;

    public MessageEditSign() {

    }

    public MessageEditSign(BlockPos pos, String[] text) {
        this.pos = pos;
        this.text = text;
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.SERVERBOUND;
    }

    @Override
    public void executeServerSide(PlayPayloadContext context) {
        if (!(context.player().orElse(null) instanceof ServerPlayer sender)) {
            return;
        }

        if (sender.level().getBlockEntity(pos) instanceof TileEntitySign sign) {
            sign.setText(text);
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

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
