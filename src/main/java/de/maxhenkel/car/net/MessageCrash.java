package de.maxhenkel.car.net;

import java.util.List;
import java.util.UUID;
import de.maxhenkel.car.PredicateUUID;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageCrash implements IMessage, IMessageHandler<MessageCrash, IMessage>{

	private float speed;
	private UUID uuid;
	
	public MessageCrash() {
		this.speed=0;
		this.uuid=new UUID(0, 0);
	}
	
	public MessageCrash(float speed, EntityCarBase car) {
		this.speed=speed;
		this.uuid=car.getUniqueID();
	}

	@Override
	public IMessage onMessage(MessageCrash message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			
			List<EntityCarBase> list=player.world.getEntities(EntityCarBase.class, new PredicateUUID(message.uuid));
			
			if(list.isEmpty()){
				return null;
			}
			
			EntityCarBase car=list.get(0);	

			if(!car.getUniqueID().equals(message.uuid)){
				return null;
			}
			
			car.onCollision(message.speed);
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.speed=buf.readFloat();
		
		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeFloat(speed);
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

}
