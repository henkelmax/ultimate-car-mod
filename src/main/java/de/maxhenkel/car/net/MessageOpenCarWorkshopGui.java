package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.ContainerCarWorkshopCrafting;
import de.maxhenkel.car.gui.ContainerCarWorkshopRepair;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageOpenCarWorkshopGui implements Message<MessageOpenCarWorkshopGui> {

    private BlockPos pos;
    private UUID uuid;
    private boolean repair;

    public MessageOpenCarWorkshopGui() {

    }

    public MessageOpenCarWorkshopGui(BlockPos pos, PlayerEntity player, boolean reapir) {
        this.pos = pos;
        this.uuid = player.getUniqueID();
        this.repair = reapir;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUniqueID().equals(uuid)) {
            Main.LOGGER.error("The UUID of the sender was not equal to the packet UUID");
            return;
        }

        TileEntity te = context.getSender().world.getTileEntity(pos);

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
    public MessageOpenCarWorkshopGui fromBytes(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.uuid = buf.readUniqueId();
        this.repair = buf.readBoolean();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeUniqueId(uuid);
        buf.writeBoolean(repair);
    }

}
