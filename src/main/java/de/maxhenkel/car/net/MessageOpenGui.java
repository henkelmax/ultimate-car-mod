package de.maxhenkel.car.net;

import java.util.UUID;
import de.maxhenkel.car.Main;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;

public class MessageOpenGui extends MessageToServer<MessageOpenGui>{

	private int posX;
	private int posY;
	private int posZ;
	private int guiID;
	private UUID uuid;
	
	public MessageOpenGui() {
		this.uuid=new UUID(0, 0);
	}
	
	public MessageOpenGui(BlockPos pos, int guiID, EntityPlayer player) {
		this.posX=pos.getX();
		this.posY=pos.getY();
		this.posZ=pos.getZ();
		this.guiID=guiID;
		this.uuid=player.getUniqueID();
	}

	@Override
	public void execute(EntityPlayer player, MessageOpenGui message) {
		if(!player.getUniqueID().equals(message.uuid)){
			System.out.println("---------UUID was not the same-----------");
			return;
		}

		player.openGui(Main.instance(), message.guiID, player.world, message.posX, message.posY, message.posZ);
	}

	public MessageOpenGui open(EntityPlayer player){
		if(player.world.isRemote){
			player.openGui(Main.instance(), guiID, player.world, posX, posY, posZ);
		}
		return this;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.posX=buf.readInt();
		this.posY=buf.readInt();
		this.posZ=buf.readInt();
		this.guiID=buf.readInt();
		
		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		buf.writeInt(guiID);
		
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

}
