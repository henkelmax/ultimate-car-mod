package de.maxhenkel.car.net;

import de.maxhenkel.car.items.ItemLicensePlate;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

public class MessageEditLicensePlate implements Message<MessageEditLicensePlate> {

    private UUID uuid;
    private String text;

    public MessageEditLicensePlate() {
        this.uuid = new UUID(0, 0);
        this.text = "";
    }

    public MessageEditLicensePlate(PlayerEntity player, String text) {
        this.uuid = player.getUniqueID();
        this.text = text;
    }


    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUniqueID().equals(uuid)) {
            return;
        }
        setItemText(context.getSender(), text);
    }

    public static void setItemText(PlayerEntity player, String text) {
        ItemStack stack = player.getHeldItem(Hand.MAIN_HAND);
        if (stack.getItem() instanceof ItemLicensePlate) {
            ItemLicensePlate.setText(stack, text);
            player.setHeldItem(Hand.MAIN_HAND, stack);
        } else {
            stack = player.getHeldItem(Hand.OFF_HAND);
            if (stack.getItem() instanceof ItemLicensePlate) {
                ItemLicensePlate.setText(stack, text);
                player.setHeldItem(Hand.OFF_HAND, stack);
            }
        }
    }


    @Override
    public void executeClientSide(NetworkEvent.Context context) {

    }

    @Override
    public MessageEditLicensePlate fromBytes(PacketBuffer buf) {
        long l1 = buf.readLong();
        long l2 = buf.readLong();
        this.uuid = new UUID(l1, l2);

        text = buf.readString();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeLong(uuid.getMostSignificantBits());
        buf.writeLong(uuid.getLeastSignificantBits());
        buf.writeString(text);
    }
}
