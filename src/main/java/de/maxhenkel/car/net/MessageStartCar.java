package de.maxhenkel.car.net;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStartCar implements IMessage, IMessageHandler<MessageStartCar, IMessage>{

	private boolean failed;
	
	public MessageStartCar() {
		this.failed=false;
	}
	
	public MessageStartCar(boolean failed) {
		this.failed=failed;
	}

	@Override
	public IMessage onMessage(MessageStartCar message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			Entity riding=player.getRidingEntity();
			
			if(!(riding instanceof EntityCarBase)){
				return null;
			}
			
			EntityCarBase car=(EntityCarBase) riding;
			if(player.equals(car.getDriver())){
				car.startCarEngineServerSide(message.failed);
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.failed=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(failed);
	}

}
