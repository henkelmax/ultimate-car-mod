package de.maxhenkel.car.net;

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
	
	public MessageControlCar() {
		this.forward=false;
		this.backward=false;
		this.left=false;
		this.right=false;
	}
	
	public MessageControlCar(boolean forward, boolean backward, boolean left, boolean right) {
		this.forward = forward;
		this.backward = backward;
		this.left = left;
		this.right = right;
	}

	@Override
	public IMessage onMessage(MessageControlCar message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			Entity riding=player.getRidingEntity();
			
			if(!(riding instanceof EntityCarBase)){
				return null;
			}
			
			EntityCarBase car=(EntityCarBase) riding;
			
			car.updateControls(message.forward, message.backward, message.left, message.right);
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.forward=buf.readBoolean();
		this.backward=buf.readBoolean();
		this.left=buf.readBoolean();
		this.right=buf.readBoolean();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(forward);
		buf.writeBoolean(backward);
		buf.writeBoolean(left);
		buf.writeBoolean(right);
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
