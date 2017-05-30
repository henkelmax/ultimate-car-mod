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
	private float carStepHeight;
	
	public MessageSyncConfig() {
		
	}
	
	public MessageSyncConfig(boolean carGroundSpeed, float carStepHeight) {
		this.carGroundSpeed=carGroundSpeed;
		this.carStepHeight=carStepHeight;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(MessageSyncConfig message, MessageContext ctx) {
		if(ctx.side.equals(Side.CLIENT)){
			Config.carGroundSpeed=message.carGroundSpeed;
			Config.carStepHeight=message.carStepHeight;
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.carGroundSpeed=buf.readBoolean();
		this.carStepHeight=buf.readFloat();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(carGroundSpeed);
		buf.writeFloat(carStepHeight);
	}

}
