package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageFuelStationAdminAmount implements IMessage, IMessageHandler<MessageFuelStationAdminAmount, IMessage> {

	private int posX;
	private int posY;
	private int posZ;
	private int amount;

	public MessageFuelStationAdminAmount() {

	}

	public MessageFuelStationAdminAmount(BlockPos pos, int amount) {
		this.posX = pos.getX();
		this.posY = pos.getY();
		this.posZ = pos.getZ();
		this.amount=amount;
	}

	@Override
	public IMessage onMessage(MessageFuelStationAdminAmount message, MessageContext ctx) {
		if (ctx.side.equals(Side.SERVER)) {
			final EntityPlayer player = ctx.getServerHandler().player;

			TileEntity te = player.world.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));

			if (te instanceof TileEntityFuelStation) {
				final TileEntityFuelStation station = (TileEntityFuelStation) te;
				player.getServer().addScheduledTask(new Runnable() {
					public void run() {
						station.setField(2, message.amount);
					}
				});
			}

		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.posX = buf.readInt();
		this.posY = buf.readInt();
		this.posZ = buf.readInt();
		this.amount=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		buf.writeInt(amount);
	}

}
