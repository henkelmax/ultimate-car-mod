package de.maxhenkel.car.net;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MessageEditSign implements Message<MessageEditSign> {

    public static final CustomPacketPayload.Type<MessageEditSign> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(CarMod.MODID, "edit_sign"));

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
    public void executeServerSide(IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer sender)) {
            return;
        }

        if (sender.level().getBlockEntity(pos) instanceof TileEntitySign sign) {
            sign.setText(text);
        }
    }

    @Override
    public MessageEditSign fromBytes(RegistryFriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.text = new String[8];
        for (int i = 0; i < text.length; i++) {
            this.text[i] = buf.readUtf(64);
        }

        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        for (String s : text) {
            buf.writeUtf(s, 64);
        }
    }

    @Override
    public Type<MessageEditSign> type() {
        return TYPE;
    }

}
