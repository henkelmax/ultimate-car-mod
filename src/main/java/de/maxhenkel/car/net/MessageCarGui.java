package de.maxhenkel.car.net;

import de.maxhenkel.car.entity.car.base.EntityCarBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageCarGui implements IMessage, IMessageHandler<MessageCarGui, IMessage>{

	private boolean open;
	
	public MessageCarGui() {
		this.open=false;
	}
	
	public MessageCarGui(boolean open) {
		this.open=open;
	}

	@Override
	public IMessage onMessage(MessageCarGui message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			Entity riding=player.getRidingEntity();
			
			if(!(riding instanceof EntityCarBase)){
				return null;
			}
			
			EntityCarBase car=(EntityCarBase) riding;
			if(message.open){
				car.openCarGUi(player);
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.open=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(open);
	}

}
