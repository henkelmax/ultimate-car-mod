package de.maxhenkel.car.net;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.corelib.net.Message;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.handling.IPayloadContext;

import java.util.UUID;

public class MessageEditLicensePlate implements Message<MessageEditLicensePlate> {

    public static final CustomPacketPayload.Type<MessageEditLicensePlate> TYPE = new CustomPacketPayload.Type<>(ResourceLocation.fromNamespaceAndPath(Main.MODID, "edit_license_plate"));

    private UUID uuid;
    private String text;

    public MessageEditLicensePlate() {

    }

    public MessageEditLicensePlate(Player player, String text) {
        this.uuid = player.getUUID();
        this.text = text;
    }

    @Override
    public PacketFlow getExecutingSide() {
        return PacketFlow.SERVERBOUND;
    }

    @Override
    public void executeServerSide(IPayloadContext context) {
        if (!(context.player() instanceof ServerPlayer sender)) {
            return;
        }

        if (!sender.getUUID().equals(uuid)) {
            return;
        }
        setItemText(sender, text);
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
    public MessageEditLicensePlate fromBytes(RegistryFriendlyByteBuf buf) {
        uuid = buf.readUUID();
        text = buf.readUtf(128);
        return this;
    }

    @Override
    public void toBytes(RegistryFriendlyByteBuf buf) {
        buf.writeUUID(uuid);
        buf.writeUtf(text);
    }

    @Override
    public Type<MessageEditLicensePlate> type() {
        return TYPE;
    }

}
