package de.maxhenkel.car.net;

import java.util.UUID;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageRepairCar implements IMessage, IMessageHandler<MessageRepairCar, IMessage>{

	private int posX;
	private int posY;
	private int posZ;
	private UUID uuid;
	
	public MessageRepairCar() {
		this.uuid=new UUID(0, 0);
	}
	
	public MessageRepairCar(BlockPos pos, EntityPlayer player) {
		this.posX=pos.getX();
		this.posY=pos.getY();
		this.posZ=pos.getZ();
		this.uuid=player.getUniqueID();
	}

	@Override
	public IMessage onMessage(MessageRepairCar message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			
			if(!player.getUniqueID().equals(message.uuid)){
				return null;
			}
			
			TileEntity te=player.worldObj.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));
			
			if(te instanceof TileEntityCarWorkshop){
				TileEntityCarWorkshop carWorkshop=(TileEntityCarWorkshop) te;
				
				carWorkshop.repairCar(player);
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.posX=buf.readInt();
		this.posY=buf.readInt();
		this.posZ=buf.readInt();
		
		long l1=buf.readLong();
		long l2=buf.readLong();
		this.uuid=new UUID(l1, l2);
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}

}
