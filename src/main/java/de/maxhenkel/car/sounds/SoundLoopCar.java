package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;

public abstract class SoundLoopCar extends AbstractTickableSoundInstance {

    protected EntityCarBase car;

    public SoundLoopCar(EntityCarBase car, SoundEvent event, SoundSource category) {
        super(event, category, SoundInstance.createUnseededRandom());
        this.car = car;
        this.looping = true;
        this.delay = 0;
        this.volume = Main.CLIENT_CONFIG.carVolume.get().floatValue();
        this.pitch = 1F;
        this.relative = false;
        this.attenuation = Attenuation.LINEAR;
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

        LocalPlayer player = Minecraft.getInstance().player;
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
