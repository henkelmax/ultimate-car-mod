package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public class MessageSpawnCar implements Message<MessageSpawnCar> {

    public static ResourceLocation ID = new ResourceLocation(Main.MODID, "spawn_car");

    private BlockPos pos;

    public MessageSpawnCar() {
    }

    public MessageSpawnCar(BlockPos pos) {
        this.pos = pos;
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

        if (sender.level().getBlockEntity(pos) instanceof TileEntityCarWorkshop workshop) {
            workshop.spawnCar(sender);
        }
    }

    @Override
    public MessageSpawnCar fromBytes(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }

}
