package de.maxhenkel.car.net;

import de.maxhenkel.car.entity.car.base.EntityCarBatteryBase;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import java.util.UUID;

public class MessageStarting extends MessageToServer<MessageStarting>{

	private boolean start;
	private boolean playSound;
	private UUID uuid;

	public MessageStarting() {
		this.start=true;
		this.uuid=new UUID(0, 0);
	}

	public MessageStarting(boolean start, boolean playSound, EntityPlayer player) {
		this.start=start;
		this.playSound=playSound;
		this.uuid=player.getUniqueID();
	}

	@Override
	public void execute(EntityPlayer player, MessageStarting message) {
		if(!player.getUniqueID().equals(message.uuid)){
			System.out.println("---------UUID was not the same-----------");
			return;
		}

		Entity riding=player.getRidingEntity();

		if(!(riding instanceof EntityCarBatteryBase)){
			return;
		}

		EntityCarBatteryBase car=(EntityCarBatteryBase) riding;
		if(player.equals(car.getDriver())){
			car.setStarting(message.start, message.playSound);
		}
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.start=buf.readBoolean();
        this.playSound=buf.readBoolean();

		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(start);
        buf.writeBoolean(playSound);

		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

}
