package de.maxhenkel.car.net;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class MessageSyncTileEntity implements IMessage, IMessageHandler<MessageSyncTileEntity, IMessage>{

	private int posX;
	private int posY;
	private int posZ;
	private NBTTagCompound tag;
	
	public MessageSyncTileEntity() {
		
	}
	
	public MessageSyncTileEntity(BlockPos pos, TileEntity tileEntity) {
		this.posX=pos.getX();
		this.posY=pos.getY();
		this.posZ=pos.getZ();
		this.tag=tileEntity.writeToNBT(new NBTTagCompound());
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(MessageSyncTileEntity message, MessageContext ctx) {
		if(ctx.side.equals(Side.CLIENT)){
			EntityPlayer player=Minecraft.getMinecraft().thePlayer;
			
			TileEntity te=player.worldObj.getTileEntity(new BlockPos(message.posX, message.posY, message.posZ));
			
			if(te!=null){
				te.readFromNBT(message.tag);
			}
			
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		this.posX=buf.readInt();
		this.posY=buf.readInt();
		this.posZ=buf.readInt();
		this.tag=ByteBufUtils.readTag(buf);
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(posX);
		buf.writeInt(posY);
		buf.writeInt(posZ);
		ByteBufUtils.writeTag(buf, tag);
	}

}
