package de.maxhenkel.car.events;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import net.minecraft.client.CameraType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.Entity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class PlayerEvents {

    private Minecraft minecraft;
    private EntityVehicleBase lastVehicle;

    public PlayerEvents() {
        this.minecraft = Minecraft.getInstance();
        this.lastVehicle = null;
    }

    @SubscribeEvent
    public void onPlayerTick(PlayerTickEvent.Pre evt) {
        if (!evt.getEntity().level().isClientSide()) {
            return;
        }

        if (!evt.getEntity().equals(getPlayer())) {
            return;
        }

        EntityVehicleBase vehicle = getRidingVehicle();

        if (vehicle != null && lastVehicle == null) {
            if (vehicle.doesEnterThirdPerson()) {
                setThirdPerson(true);
            }
        } else if (vehicle == null && lastVehicle != null) {
            if (lastVehicle.doesEnterThirdPerson()) {
                setThirdPerson(false);
            }
        }
        lastVehicle = vehicle;
    }

    private void setThirdPerson(boolean third) {
        if (!CarMod.CLIENT_CONFIG.thirdPersonEnter.get()) {
            return;
        }

        if (third) {
            minecraft.options.setCameraType(CameraType.THIRD_PERSON_BACK);
        } else {
            minecraft.options.setCameraType(CameraType.FIRST_PERSON);
        }

    }

    private LocalPlayer getPlayer() {
        return minecraft.player;
    }

    private Entity getRidingEntity() {
        return getPlayer().getVehicle();
    }

    private EntityVehicleBase getRidingVehicle() {
        Entity e = getRidingEntity();
        if (e instanceof EntityVehicleBase) {
            return (EntityVehicleBase) e;
        }

        return null;
    }

}
