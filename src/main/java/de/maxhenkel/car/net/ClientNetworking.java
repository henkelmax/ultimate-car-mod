package de.maxhenkel.car.net;

import de.maxhenkel.car.blocks.tileentity.TileEntityBase;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import de.maxhenkel.corelib.codec.ValueInputOutputUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.TagValueInput;

import java.util.UUID;

public class ClientNetworking {

    public static void syncTileEntity(BlockPos pos, CompoundTag tag) {
        Player player = Minecraft.getInstance().player;

        if (player == null || player.level() == null) {
            return;
        }

        BlockEntity te = player.level().getBlockEntity(pos);

        if (te instanceof TileEntityBase tileEntityBase) {
            TagValueInput valueInput = ValueInputOutputUtils.createValueInput(tileEntityBase, player.registryAccess(), tag);
            tileEntityBase.loadAdditional(valueInput);
        }
    }

    public static void centerCar(UUID uuid) {
        Player player = Minecraft.getInstance().player;
        Player ridingPlayer = player.level().getPlayerByUUID(uuid);

        if (!(ridingPlayer.getVehicle() instanceof EntityCarBase car)) {
            return;
        }

        if (ridingPlayer.equals(car.getDriver())) {
            car.centerCar();
        }
    }

}
