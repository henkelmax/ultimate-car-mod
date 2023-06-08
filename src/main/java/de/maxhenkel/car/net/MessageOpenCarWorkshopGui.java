package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.ContainerCarWorkshopCrafting;
import de.maxhenkel.car.gui.ContainerCarWorkshopRepair;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.network.NetworkEvent;

public class MessageOpenCarWorkshopGui implements Message<MessageOpenCarWorkshopGui> {

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
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUUID().equals(uuid)) {
            Main.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        BlockEntity te = context.getSender().level().getBlockEntity(pos);

        if (!(te instanceof TileEntityCarWorkshop)) {
            return;
        }
        TileEntityCarWorkshop carWorkshop = (TileEntityCarWorkshop) te;

        if (repair) {
            TileEntityContainerProvider.openGui(context.getSender(), carWorkshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopRepair(i, carWorkshop, playerInventory));
        } else {
            TileEntityContainerProvider.openGui(context.getSender(), carWorkshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopCrafting(i, carWorkshop, playerInventory));
        }
    }

    @Override
    public MessageOpenCarWorkshopGui fromBytes(FriendlyByteBuf buf) {
        this.pos = buf.readBlockPos();
        this.uuid = buf.readUUID();
        this.repair = buf.readBoolean();

        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBlockPos(pos);
        buf.writeUUID(uuid);
        buf.writeBoolean(repair);
    }

}
