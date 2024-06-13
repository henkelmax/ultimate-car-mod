package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.ContainerCarWorkshopCrafting;
import de.maxhenkel.car.gui.ContainerCarWorkshopRepair;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageOpenCarWorkshopGui implements Message<MessageOpenCarWorkshopGui> {

    public static final CustomPacketPayload.Type<MessageOpenCarWorkshopGui> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, "open_car_workshop"));

    private BlockPos pos;
    private UUID uuid;
    private boolean repair;

    public MessageOpenCarWorkshopGui() {

    }

    public MessageOpenCarWorkshopGui(BlockPos pos, Player player, boolean reapir) {
        this.pos = pos;
        this.uuid = player.getUUID();
        this.repair = reapir;
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

        if (!sender.getUUID().equals(uuid)) {
            Main.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        if (!(sender.level().getBlockEntity(pos) instanceof TileEntityCarWorkshop workshop)) {
            return;
        }

        if (repair) {
            TileEntityContainerProvider.openGui(sender, workshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopRepair(i, workshop, playerInventory));
        } else {
            TileEntityContainerProvider.openGui(sender, workshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopCrafting(i, workshop, playerInventory));
        }
    }

    @Override
    public MessageOpenCarWorkshopGui fromBytes(RegistryFriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.uuid = buf.readUUID();
        this.repair = buf.readBoolean();

        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUUID(uuid);
        buf.writeBoolean(repair);
    }

    @Override
    public Type<MessageOpenCarWorkshopGui> type() {
        return TYPE;
    }

}
