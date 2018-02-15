package de.maxhenkel.car.net;

import java.util.UUID;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MessageCarHorn extends MessageToServer<MessageCarHorn>{

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
	public void execute(EntityPlayer player, MessageCarHorn message) {
		if(!message.pressed){
			return;
		}

		if(!player.getUniqueID().equals(message.uuid)){
			System.out.println("---------UUID was not the same-----------");
			return;
		}

		Entity riding=player.getRidingEntity();

		if(!(riding instanceof EntityCarBase)){
			return;
		}

		EntityCarBase car=(EntityCarBase) riding;
		if(player.equals(car.getDriver())){
			car.onHornPressed(player);
		}
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
