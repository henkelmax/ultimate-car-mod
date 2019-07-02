package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.car.gui.ContainerCarWorkshopRepair;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageOpenRepairGui implements Message<MessageOpenRepairGui> {

    private BlockPos pos;
    private UUID uuid;

    public MessageOpenRepairGui() {
        this.uuid = new UUID(0, 0);
    }

    public MessageOpenRepairGui(BlockPos pos, PlayerEntity player) {
        this.pos = pos;
        this.uuid = player.getUniqueID();
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUniqueID().equals(uuid)) {
            System.out.println("---------UUID was not the same-----------");
            return;
        }

        TileEntity te = context.getSender().world.getTileEntity(pos);

        if (!(te instanceof TileEntityCarWorkshop)) {
            return;
        }
        TileEntityCarWorkshop carWorkshop = (TileEntityCarWorkshop) te;
        TileEntityContainerProvider.openGui(context.getSender(), carWorkshop, (i, playerInventory, playerEntity) -> new ContainerCarWorkshopRepair(i, carWorkshop, playerInventory));
    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageOpenRepairGui fromBytes(PacketBuffer buf) {
        this.pos = buf.readBlockPos();
        this.uuid = buf.readUniqueId();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeBlockPos(pos);
        buf.writeUniqueId(uuid);
    }
}
