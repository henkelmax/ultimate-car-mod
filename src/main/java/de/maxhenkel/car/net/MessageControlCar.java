package de.maxhenkel.car.net;

import java.util.UUID;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageControlCar implements Message<MessageControlCar>{

	private boolean forward, backward, left, right;
	private UUID uuid;
	
	public MessageControlCar() {
		this.forward=false;
		this.backward=false;
		this.left=false;
		this.right=false;
		this.uuid=new UUID(0, 0);
	}
	
	public MessageControlCar(boolean forward, boolean backward, boolean left, boolean right, PlayerEntity player) {
		this.forward = forward;
		this.backward = backward;
		this.left = left;
		this.right = right;
		this.uuid=player.getUniqueID();
	}

	@Override
	public void executeServerSide(NetworkEvent.Context context) {
		if(!context.getSender().getUniqueID().equals(uuid)){
			System.out.println("---------UUID was not the same-----------");
			return;
		}

		Entity e=context.getSender().getRidingEntity();

		if(!(e instanceof EntityCarBase)){
			return;
		}

		EntityCarBase car=(EntityCarBase) e;

		car.updateControls(forward, backward, left, right, context.getSender());
	}

	@Override
	public void executeClientSide(NetworkEvent.Context context) {

	}

	@Override
	public MessageControlCar fromBytes(PacketBuffer buf) {
		this.forward=buf.readBoolean();
		this.backward=buf.readBoolean();
		this.left=buf.readBoolean();
		this.right=buf.readBoolean();
		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
		return this;
	}

	@Override
	public void toBytes(PacketBuffer buf) {
		buf.writeBoolean(forward);
		buf.writeBoolean(backward);
		buf.writeBoolean(left);
		buf.writeBoolean(right);
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}
}
