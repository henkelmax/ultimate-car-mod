package de.maxhenkel.car.net;

import java.util.UUID;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.network.NetworkEvent;

public class MessageOpenGui implements Message<MessageOpenGui> {

	private int posX;
	private int posY;
	private int posZ;
	private int guiID;
	private UUID uuid;
	
	public MessageOpenGui() {
		this.uuid=new UUID(0, 0);
	}
	
	public MessageOpenGui(BlockPos pos, int guiID, PlayerEntity player) {
		this.posX=pos.getX();
		this.posY=pos.getY();
		this.posZ=pos.getZ();
		this.guiID=guiID;
		this.uuid=player.getUniqueID();
	}

	public MessageOpenGui open(PlayerEntity player){
		if(player.world.isRemote){
			//TODO gui
			//player.openGui(Main.instance(), guiID, player.world, posX, posY, posZ);
		}
		return this;
	}

	@Override
	public void executeServerSide(NetworkEvent.Context context) {
		if(!context.getSender().getUniqueID().equals(uuid)){
			System.out.println("---------UUID was not the same-----------");
			return;
		}

		//TODO gui
		//player.openGui(Main.instance(), message.guiID, player.world, message.posX, message.posY, message.posZ);
	}

	@Override
	public void executeClientSide(NetworkEvent.Context context) {

	}

	@Override
	public MessageOpenGui fromBytes(PacketBuffer buf) {
		this.posX=buf.readInt();
		this.posY=buf.readInt();
		this.posZ=buf.readInt();
		this.guiID=buf.readInt();

		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);

		return this;
	}

	@Override
	public void toBytes(PacketBuffer buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		buf.writeInt(guiID);

		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}
}
