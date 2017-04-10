package de.maxhenkel.car.net;

import de.maxhenkel.car.Config;
import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncConfig implements IMessage, IMessageHandler<MessageSyncConfig, IMessage>{

	private boolean carGroundSpeed;
	
	public MessageSyncConfig() {
		
	}
	
	public MessageSyncConfig(boolean carGroundSpeed) {
		this.carGroundSpeed=carGroundSpeed;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(MessageSyncConfig message, MessageContext ctx) {
		if(ctx.side.equals(Side.CLIENT)){
			Config.carGroundSpeed=message.carGroundSpeed;
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.carGroundSpeed=buf.readBoolean();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(carGroundSpeed);
	}

}
