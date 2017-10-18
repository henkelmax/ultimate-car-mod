package de.maxhenkel.car.net;

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

public class MessageEditSign implements IMessage, IMessageHandler<MessageEditSign, IMessage> {

	private int posX;
	private int posY;
	private int posZ;
	private String[] text;

	public MessageEditSign() {
		
	}

	public MessageEditSign(BlockPos pos, String[] text) {
		this.posX = pos.getX();
		this.posY = pos.getY();
		this.posZ = pos.getZ();
		this.text=text;
	}

	@Override
	public IMessage onMessage(MessageEditSign message, MessageContext ctx) {
		if (ctx.side.equals(Side.SERVER)) {
			final EntityPlayer player = ctx.getServerHandler().player;

			TileEntity te = player.world.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));

			if (te instanceof TileEntitySign) {
				final TileEntitySign carSign = (TileEntitySign) te;
				player.getServer().addScheduledTask(new Runnable() {
					public void run() {
						carSign.setText(message.text);
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
		this.text=new String[4];
		for(int i=0; i<text.length; i++) {
			this.text[i]=ByteBufUtils.readUTF8String(buf);
		}
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		for(int i=0; i<text.length; i++) {
			ByteBufUtils.writeUTF8String(buf, text[i]);
		}
	}

}
