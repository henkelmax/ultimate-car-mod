package de.maxhenkel.car.net;

import de.maxhenkel.car.sounds.SoundLoopTileentity.ISoundLoopable;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessagePlaySoundLoop implements IMessage, IMessageHandler<MessagePlaySoundLoop, IMessage>{

	private int posX;
	private int posY;
	private int posZ;
	
	public MessagePlaySoundLoop() {
		
	}
	
	public MessagePlaySoundLoop(TileEntity tileEntity) {
		this.posX=tileEntity.getPos().getX();
		this.posY=tileEntity.getPos().getY();
		this.posZ=tileEntity.getPos().getZ();

	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(MessagePlaySoundLoop message, MessageContext ctx) {
		if(ctx.side.equals(Side.CLIENT)){
			EntityPlayer player=Minecraft.getMinecraft().thePlayer;
			
			TileEntity te=player.worldObj.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));
			
			if(te instanceof ISoundLoopable){
				ISoundLoopable loop=(ISoundLoopable) te;
				loop.play();
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
