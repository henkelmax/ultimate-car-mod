package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.entity.car.base.EntityCarBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public abstract class SoundLoopCar extends TickableSound {

    protected World world;
    protected EntityCarBase car;

    public SoundLoopCar(World world, EntityCarBase car, SoundEvent event, SoundCategory category) {
        super(event, category);
        this.world = world;
        this.car = car;
        this.repeat = true;
        this.repeatDelay = 0;
        this.updatePos();
        this.volume = Config.carVolume.get().floatValue();
        this.pitch = 1.0F;
    }

    public void updatePos() {
        this.x = car.getPosition().getX();
        this.y = car.getPosition().getY();
        this.z = car.getPosition().getZ();
    }

    @Override
    public void tick() {
        if (donePlaying) {
            onFinishPlaying();
            return;
        }

        if (!car.isAlive()) {
            this.donePlaying = true;
            this.repeat = false;
            return;
        }

        if (world.isRemote) {
            ClientPlayerEntity player = Minecraft.getInstance().player;
            if (player == null || !player.isAlive()) {
                this.donePlaying = true;
                this.repeat = false;
                return;
            }
        }

        if (shouldStopSound()) {
            this.donePlaying = true;
            this.repeat = false;
            return;
        }

        updatePos();
    }

    public void onFinishPlaying() {

    }

    public void setDonePlaying() {
        donePlaying = true;
    }

    public abstract boolean shouldStopSound();
}
