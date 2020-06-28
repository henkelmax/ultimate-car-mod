package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Config;
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
        this.repeat = true;
        this.repeatDelay = 0;
        this.volume = Config.carVolume.get().floatValue();
        this.pitch = 1F;
        this.priority = true;
        this.global = false;
        this.attenuationType = AttenuationType.LINEAR;
        this.updatePos();
    }

    public void updatePos() {
        this.x = (float) car.getPosX();
        this.y = (float) car.getPosY();
        this.z = (float) car.getPosZ();
    }

    @Override
    public void tick() {
        if (isDonePlaying()) {
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
        func_239509_o_();
    }

    public abstract boolean shouldStopSound();
}
