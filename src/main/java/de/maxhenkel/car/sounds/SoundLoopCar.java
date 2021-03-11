package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

public abstract class SoundLoopCar extends TickableSound {

    protected EntityCarBase car;

    public SoundLoopCar(EntityCarBase car, SoundEvent event, SoundCategory category) {
        super(event, category);
        this.car = car;
        this.looping = true;
        this.delay = 0;
        this.volume = Main.CLIENT_CONFIG.carVolume.get().floatValue();
        this.pitch = 1F;
        this.priority = true;
        this.relative = false;
        this.attenuation = AttenuationType.LINEAR;
        this.updatePos();
    }

    public void updatePos() {
        this.x = (float) car.getX();
        this.y = (float) car.getY();
        this.z = (float) car.getZ();
    }

    @Override
    public void tick() {
        if (isStopped()) {
            return;
        }

        if (!car.isAlive()) {
            setDonePlaying();
            return;
        }

        ClientPlayerEntity player = Minecraft.getInstance().player;
        if (player == null || !player.isAlive()) {
            setDonePlaying();
            return;
        }

        if (shouldStopSound()) {
            setDonePlaying();
            return;
        }

        updatePos();
    }

    public void setDonePlaying() {
        stop();
    }

    public abstract boolean shouldStopSound();

}
