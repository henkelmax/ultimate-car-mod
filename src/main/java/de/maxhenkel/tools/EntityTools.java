package de.maxhenkel.tools;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.AxisAlignedBB;

import javax.annotation.Nullable;
import java.util.UUID;

public class EntityTools {

    @Nullable
    public static EntityGenericCar getCarByUUID(PlayerEntity player, UUID uuid) {
        double distance = 10D;
        return player.world.getEntitiesWithinAABB(EntityGenericCar.class, new AxisAlignedBB(player.posX - distance, player.posY - distance, player.posZ - distance, player.posX + distance, player.posY + distance, player.posZ + distance), entity -> entity.getUniqueID().equals(uuid)).stream().findAny().orElse(null);
    }

}
