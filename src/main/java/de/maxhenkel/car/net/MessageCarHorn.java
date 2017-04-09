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

public class MessageCarHorn implements IMessage, IMessageHandler<MessageCarHorn, IMessage>{

	private boolean pressed;
	private UUID uuid;
	
	public MessageCarHorn() {
		this.pressed=false;
		this.uuid=new UUID(0, 0);
	}
	
	public MessageCarHorn(boolean pressed, EntityPlayer player) {
		this.pressed=pressed;
		this.uuid=player.getUniqueID();
	}

	@Override
	public IMessage onMessage(MessageCarHorn message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			
			if(!message.pressed){
				return null;
			}
			
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			
			if(!player.getUniqueID().equals(message.uuid)){
				return null;
			}
			
			Entity riding=player.getRidingEntity();
			
			if(!(riding instanceof EntityCarBase)){
				return null;
			}
			
			EntityCarBase car=(EntityCarBase) riding;
			if(player.equals(car.getDriver())){
				car.onHornPressed(player);
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.pressed=buf.readBoolean();
		
		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(pressed);
		
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

}
