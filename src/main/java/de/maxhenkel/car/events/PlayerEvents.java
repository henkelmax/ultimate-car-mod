package de.maxhenkel.car.events;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityVehicleBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;

@OnlyIn(Dist.CLIENT)
public class PlayerEvents {

    private Minecraft minecraft;
    private EntityVehicleBase lastVehicle;

    public PlayerEvents() {
        this.minecraft = Minecraft.getInstance();
        this.lastVehicle = null;
    }

    @SubscribeEvent
    public void onPlayerTick(TickEvent.PlayerTickEvent evt) {
        if (evt.side.equals(LogicalSide.SERVER)) {
            return;
        }

        if (!evt.player.equals(getPlayer())) {
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
        if (!Main.CLIENT_CONFIG.thirdPersonEnter.get()) {
            return;
        }

        if (third) {
            minecraft.gameSettings.setPointOfView(PointOfView.THIRD_PERSON_BACK);
        } else {
            minecraft.gameSettings.setPointOfView(PointOfView.FIRST_PERSON);
        }

    }

    private ClientPlayerEntity getPlayer() {
        return minecraft.player;
    }

    private Entity getRidingEntity() {
        return getPlayer().getRidingEntity();
    }

    private EntityVehicleBase getRidingVehicle() {
        Entity e = getRidingEntity();
        if (e instanceof EntityVehicleBase) {
            return (EntityVehicleBase) e;
        }

        return null;
    }

}
