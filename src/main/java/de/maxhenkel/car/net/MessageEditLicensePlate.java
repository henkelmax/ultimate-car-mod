package de.maxhenkel.car.net;

import de.maxhenkel.car.items.ItemLicensePlate;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;

import java.util.UUID;

public class MessageEditLicensePlate extends MessageToServer<MessageEditLicensePlate> {

    private UUID uuid;
    private String text;

    public MessageEditLicensePlate() {
        this.uuid = new UUID(0, 0);
        this.text = "";
    }

    public MessageEditLicensePlate(EntityPlayer player, String text) {
        this.uuid = player.getUniqueID();
        this.text = text;
    }

    @Override
    public void execute(EntityPlayer player, MessageEditLicensePlate message) {
        if (!player.getUniqueID().equals(message.uuid)) {
            return;
        }

        setItemText(player, message.text);
    }

    public static void setItemText(EntityPlayer player, String text) {
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (stack.getItem() instanceof ItemLicensePlate) {
            ItemLicensePlate.setText(stack, text);
            player.setHeldItem(EnumHand.MAIN_HAND, stack);
        } else {
            stack = player.getHeldItem(EnumHand.OFF_HAND);
            if (stack.getItem() instanceof ItemLicensePlate) {
                ItemLicensePlate.setText(stack, text);
                player.setHeldItem(EnumHand.OFF_HAND, stack);
            }
        }
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);

        text = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
        ByteBufUtils.writeUTF8String(buf, text);
    }

}
