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

public class MessageStartCar implements IMessage, IMessageHandler<MessageStartCar, IMessage>{

	private boolean start;
	private boolean playStopSound, playFailSound;
	private UUID uuid;
	
	public MessageStartCar() {
		this.start=true;
		this.playStopSound=false;
		this.playFailSound=false;
		this.uuid=new UUID(0, 0);
	}
	
	public MessageStartCar(boolean start, boolean playStopSound, boolean playFailSound, EntityPlayer player) {
		this.start=start;
		this.playStopSound=playStopSound;
        this.playFailSound=playFailSound;
		this.uuid=player.getUniqueID();
	}

    public MessageStartCar(boolean start, EntityPlayer player) {
        this.start=start;
        this.playStopSound=false;
        this.playFailSound=false;
        this.uuid=player.getUniqueID();
    }

	@Override
	public IMessage onMessage(MessageStartCar message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().player;
			
			if(!player.getUniqueID().equals(message.uuid)){
				return null;
			}
			
			Entity riding=player.getRidingEntity();
			
			if(!(riding instanceof EntityCarBase)){
				return null;
			}
			
			EntityCarBase car=(EntityCarBase) riding;
			if(player.equals(car.getDriver())){
				car.startCarEngineServerSide(message.start, message.playStopSound, message.playFailSound);
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.start=buf.readBoolean();
        this.playStopSound=buf.readBoolean();
        this.playFailSound=buf.readBoolean();

		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(start);
        buf.writeBoolean(playStopSound);
        buf.writeBoolean(playFailSound);

		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

}
