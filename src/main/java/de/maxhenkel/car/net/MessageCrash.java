package de.maxhenkel.car.net;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageCrash implements IMessage, IMessageHandler<MessageCrash, IMessage>{

	private float speed;
	
	public MessageCrash() {
		this.speed=0;
	}
	
	public MessageCrash(float speed) {
		this.speed=speed;
	}

	@Override
	public IMessage onMessage(MessageCrash message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			Entity riding=player.getRidingEntity();
			
			if(!(riding instanceof EntityCarBase)){
				return null;
			}
			
			EntityCarBase car=(EntityCarBase) riding;
			
			car.onCollision(message.speed);
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.speed=buf.readFloat();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(speed);
	}

}
