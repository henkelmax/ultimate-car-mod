package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageSpawnCar implements IMessage, IMessageHandler<MessageSpawnCar, IMessage>{

	private int posX;
	private int posY;
	private int posZ;
	
	public MessageSpawnCar() {
	}
	
	public MessageSpawnCar(BlockPos pos) {
		this.posX=pos.getX();
		this.posY=pos.getY();
		this.posZ=pos.getZ();
	}

	@Override
	public IMessage onMessage(MessageSpawnCar message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().playerEntity;
			
			TileEntity te=player.worldObj.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));
			
			if(te instanceof TileEntityCarWorkshop){
				TileEntityCarWorkshop carWorkshop=(TileEntityCarWorkshop) te;
				
				carWorkshop.spawnCar(player);
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.posX=buf.readInt();
		this.posY=buf.readInt();
		this.posZ=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
	}

}
