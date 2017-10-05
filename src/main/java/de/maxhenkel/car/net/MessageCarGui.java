package de.maxhenkel.car.net;

import java.util.UUID;
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
	private UUID uuid;
	
	public MessageCarGui() {
		this.open=false;
		this.uuid=new UUID(0, 0);
	}
	
	public MessageCarGui(boolean open, EntityPlayer player) {
		this.open=open;
		this.uuid=player.getUniqueID();
	}

	@Override
	public IMessage onMessage(MessageCarGui message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().player;
			
			if(!player.getUniqueID().equals(message.uuid)){
				return null;
			}
			
			Entity e=player.getRidingEntity();
			if(!(e instanceof EntityCarBase)){
				return null;
			}
			
			EntityCarBase car=(EntityCarBase) e;
			
			if(message.open){
				car.openCarGUi(player);
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.open=buf.readBoolean();
		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(open);
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

}
