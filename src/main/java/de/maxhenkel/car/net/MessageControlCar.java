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

public class MessageControlCar implements IMessage, IMessageHandler<MessageControlCar, IMessage>{

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
	public IMessage onMessage(MessageControlCar message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			
			if(!player.getUniqueID().equals(message.uuid)){
				return null;
			}
			
			Entity e=player.getRidingEntity();
				
			if(!(e instanceof EntityCarBase)){
				return null;
			}
			
			EntityCarBase car=(EntityCarBase) e;
			
			car.updateControls(message.forward, message.backward, message.left, message.right, player);
		}
		return null;
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

	public boolean isForward() {
		return forward;
	}

	public boolean isBackward() {
		return backward;
	}

	public boolean isLeft() {
		return left;
	}

	public boolean isRight() {
		return right;
	}

}
