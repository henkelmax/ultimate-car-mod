package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import de.maxhenkel.corelib.codec.ValueInputOutputUtils;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueInput;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MessageSyncTileEntity implements Message<MessageSyncTileEntity> {

    public static final CustomPacketPayload.Type<MessageSyncTileEntity> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, "sync_block_entity"));

    private BlockPos pos;
    private CompoundTag tag;

    public MessageSyncTileEntity() {

    }

    public MessageSyncTileEntity(BlockPos pos, CompoundTag tileTag) {
        this.pos = pos;
        this.tag = tileTag;
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.CLIENTBOUND;
    }

    @Override
    public void executeClientSide(IPayloadContext context) {
        sync();
    }

    @OnlyIn(Dist.CLIENT)
    private void sync() {
        Player player = Minecraft.getInstance().player;

        if (player == null || player.level() == null) {
            return;
        }

        BlockEntity te = player.level().getBlockEntity(pos);

        if (te instanceof TileEntityBase tileEntityBase) {
            TagValueInput valueInput = ValueInputOutputUtils.createValueInput(tileEntityBase, player.registryAccess(), tag);
            tileEntityBase.loadAdditional(valueInput);
        }
    }

    @Override
    public MessageSyncTileEntity fromBytes(RegistryFriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.tag = buf.readNbt();
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeNbt(tag);
    }

    @Override
    public Type<MessageSyncTileEntity> type() {
        return TYPE;
    }

}
