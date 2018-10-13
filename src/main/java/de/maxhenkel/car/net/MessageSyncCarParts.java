package de.maxhenkel.car.net;

import de.maxhenkel.car.PredicateUUID;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import java.util.List;
import java.util.UUID;

@Deprecated
public class MessageSyncCarParts implements IMessage, IMessageHandler<MessageSyncCarParts, IMessage>{

	private UUID uuid;
	private NBTTagCompound tag;

	public MessageSyncCarParts() {

	}

	public MessageSyncCarParts(EntityGenericCar car) {
		this.uuid=car.getUniqueID();
		this.tag=new NBTTagCompound();
		car.writePartsToNBT(this.tag);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IMessage onMessage(MessageSyncCarParts message, MessageContext ctx) {
		if(ctx.side.equals(Side.CLIENT)){
			EntityPlayer player=Minecraft.getMinecraft().player;

            if(player==null||player.world==null||message==null){
                return null;
            }

			List<EntityGenericCar> carList=player.world.getEntities(EntityGenericCar.class, new PredicateUUID(message.uuid));

			for(EntityGenericCar car:carList) {
                car.readPartsFromNBT(message.tag);
            }
		}
		return null;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
        long l1=buf.readLong();
        long l2=buf.readLong();
        this.uuid=new UUID(l1, l2);
		this.tag=ByteBufUtils.readTag(buf);
	}

	@Override
	public void toBytes(ByteBuf buf) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
		ByteBufUtils.writeTag(buf, tag);
	}

}
