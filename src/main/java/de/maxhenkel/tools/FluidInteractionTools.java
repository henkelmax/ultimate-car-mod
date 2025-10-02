package de.maxhenkel.tools;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

public class FluidInteractionTools {

    public static boolean tryFluidInteraction(Player player, InteractionHand hand, Level level, BlockPos pos) {
        ResourceHandler<FluidResource> blockCapability = level.getCapability(Capabilities.Fluid.BLOCK, pos, null);
        ItemAccess itemAccess = ItemAccess.forPlayerInteraction(player, hand);
        ResourceHandler<FluidResource> itemCapability = itemAccess.getCapability(Capabilities.Fluid.ITEM);
        if (itemCapability != null) {
            int moved = ResourceHandlerUtil.move(itemCapability, blockCapability, resource -> true, Integer.MAX_VALUE, null);
            if (moved > 0) {
                player.playSound(SoundEvents.BUCKET_EMPTY, 1F, 1F);
                return true;
            }
            moved = ResourceHandlerUtil.move(blockCapability, itemCapability, resource -> true, Integer.MAX_VALUE, null);
            if (moved > 0) {
                player.playSound(SoundEvents.BUCKET_FILL, 1F, 1F);
                return true;
            }
        }
        return false;
    }

}
