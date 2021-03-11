package de.maxhenkel.car.net;

import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.Hand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.UUID;

public class MessageEditLicensePlate implements Message<MessageEditLicensePlate> {

    private UUID uuid;
    private String text;

    public MessageEditLicensePlate() {

    }

    public MessageEditLicensePlate(PlayerEntity player, String text) {
        this.uuid = player.getUUID();
        this.text = text;
    }

    @Override
    public Dist getExecutingSide() {
        return Dist.DEDICATED_SERVER;
    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {
        if (!context.getSender().getUUID().equals(uuid)) {
            return;
        }
        setItemText(context.getSender(), text);
    }

    public static void setItemText(PlayerEntity player, String text) {
        ItemStack stack = player.getItemInHand(Hand.MAIN_HAND);
        if (stack.getItem() instanceof ItemLicensePlate) {
            ItemLicensePlate.setText(stack, text);
            player.setItemInHand(Hand.MAIN_HAND, stack);
        } else {
            stack = player.getItemInHand(Hand.OFF_HAND);
            if (stack.getItem() instanceof ItemLicensePlate) {
                ItemLicensePlate.setText(stack, text);
                player.setItemInHand(Hand.OFF_HAND, stack);
            }
        }
    }

    @Override
    public MessageEditLicensePlate fromBytes(PacketBuffer buf) {
        uuid = buf.readUUID();
        text = buf.readUtf(128);
        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeUUID(uuid);
        buf.writeUtf(text);
    }

}
