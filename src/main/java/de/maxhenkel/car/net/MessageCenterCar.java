package de.maxhenkel.car.net;

import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import java.util.UUID;

public class MessageCenterCar implements IMessage, IMessageHandler<MessageCenterCar, IMessage>{

	private UUID uuid;

	public MessageCenterCar() {
		this.uuid=new UUID(0, 0);
	}

	public MessageCenterCar(EntityPlayer player) {
		this.uuid=player.getUniqueID();
	}

	@Override
	public IMessage onMessage(MessageCenterCar message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().player;
			
			if(!player.getUniqueID().equals(message.uuid)){
				return null;
			}
			
			Entity riding=player.getRidingEntity();
			
			if(!(riding instanceof EntityCarBatteryBase)){
				return null;
			}

			EntityCarBatteryBase car=(EntityCarBatteryBase) riding;
			if(player.equals(car.getDriver())){
				car.centerCar();
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

}
