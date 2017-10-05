package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

public class MessageStartFuel implements IMessage, IMessageHandler<MessageStartFuel, IMessage>{

	private boolean start;
	private int posX;
	private int posY;
	private int posZ;
	
	public MessageStartFuel() {
	}
	
	public MessageStartFuel(BlockPos pos, boolean start) {
		posX=pos.getX();
		posY=pos.getY();
		posZ=pos.getZ();
		this.start=start;
	}

	@Override
	public IMessage onMessage(MessageStartFuel message, MessageContext ctx) {
		if(ctx.side.equals(Side.SERVER)){
			EntityPlayer player=ctx.getServerHandler().player;
			
			TileEntity te=player.world.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));
			
			if(te instanceof TileEntityFuelStation){
				TileEntityFuelStation tank=(TileEntityFuelStation) te;
				
				tank.setFueling(message.start);
				
				tank.synchronize();
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.start=buf.readBoolean();
		this.posX=buf.readInt();
		this.posY=buf.readInt();
		this.posZ=buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(start);
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
	}

}
