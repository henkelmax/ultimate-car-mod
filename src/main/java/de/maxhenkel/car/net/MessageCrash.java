package de.maxhenkel.car.net;

import java.util.UUID;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MessageCrash extends MessageToServer<MessageCrash>{

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
	public void execute(EntityPlayer player, MessageCrash message) {
		Entity riding=player.getRidingEntity();

		if(!(riding instanceof EntityCarBase)){
			return;
		}

		EntityCarBase car=(EntityCarBase) riding;

		if(!car.getUniqueID().equals(message.uuid)){
			return;
		}

		car.onCollision(message.speed);
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
