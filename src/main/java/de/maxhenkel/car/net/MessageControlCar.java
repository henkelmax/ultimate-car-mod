package de.maxhenkel.car.net;

import java.util.UUID;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;

public class MessageControlCar extends MessageToServer<MessageControlCar>{

	private boolean forward, backward, left, right;
	private UUID uuid;
	
	public MessageControlCar() {
		this.forward=false;
		this.backward=false;
		this.left=false;
		this.right=false;
		this.uuid=new UUID(0, 0);
	}
	
	public MessageControlCar(boolean forward, boolean backward, boolean left, boolean right, EntityPlayer player) {
		this.forward = forward;
		this.backward = backward;
		this.left = left;
		this.right = right;
		this.uuid=player.getUniqueID();
	}

	@Override
	public void execute(EntityPlayer player, MessageControlCar message) {
		if(!player.getUniqueID().equals(message.uuid)){
			System.out.println("---------UUID was not the same-----------");
			return;
		}

		Entity e=player.getRidingEntity();

		if(!(e instanceof EntityCarBase)){
			return;
		}

		EntityCarBase car=(EntityCarBase) e;

		car.updateControls(message.forward, message.backward, message.left, message.right, player);
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.forward=buf.readBoolean();
		this.backward=buf.readBoolean();
		this.left=buf.readBoolean();
		this.right=buf.readBoolean();
		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(forward);
		buf.writeBoolean(backward);
		buf.writeBoolean(left);
		buf.writeBoolean(right);
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}
}
