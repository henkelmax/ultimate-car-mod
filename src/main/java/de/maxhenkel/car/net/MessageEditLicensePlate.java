package de.maxhenkel.car.net;

import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.UUID;

public class MessageEditLicensePlate implements Message<MessageEditLicensePlate> {

    private UUID uuid;
    private String text;

    public MessageEditLicensePlate() {

    }

    public MessageEditLicensePlate(Player player, String text) {
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

    public static void setItemText(Player player, String text) {
        ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
        if (stack.getItem() instanceof ItemLicensePlate) {
            ItemLicensePlate.setText(stack, text);
            player.setItemInHand(InteractionHand.MAIN_HAND, stack);
        } else {
            stack = player.getItemInHand(InteractionHand.OFF_HAND);
            if (stack.getItem() instanceof ItemLicensePlate) {
                ItemLicensePlate.setText(stack, text);
                player.setItemInHand(InteractionHand.OFF_HAND, stack);
            }
        }
    }

    @Override
    public MessageEditLicensePlate fromBytes(FriendlyByteBuf buf) {
        uuid = buf.readUUID();
        text = buf.readUtf(128);
        return this;
    }

    @Override
    public void toBytes(FriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeUtf(text);
    }

}
