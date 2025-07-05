package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.sounds.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

public class CarClientSoundController {

    private final EntityGenericCar car;

    private SoundLoopStart startLoop;
    private SoundLoopIdle idleLoop;
    private SoundLoopHigh highLoop;
    private SoundLoopStarting startingLoop;

    public CarClientSoundController(EntityCarBase car) {
        this.car = (EntityGenericCar) car;
    }

    public void checkIdleLoop() {
        if (!isSoundPlaying(idleLoop)) {
            idleLoop = new SoundLoopIdle(car, car.getIdleSound(), SoundSource.MASTER);
            ModClientSounds.playSoundLoop(idleLoop, car.level());
        }
    }

    public void checkHighLoop() {
        if (!isSoundPlaying(highLoop)) {
            highLoop = new SoundLoopHigh(car, car.getHighSound(), SoundSource.MASTER);
            ModClientSounds.playSoundLoop(highLoop, car.level());
        }
    }

    public void checkStartLoop() {
        if (!isSoundPlaying(startLoop)) {
            startLoop = new SoundLoopStart(car, car.getStartSound(), SoundSource.MASTER);
            ModClientSounds.playSoundLoop(startLoop, car.level());
        }
    }

    public void checkStartingLoop() {
        if (!isSoundPlaying(startingLoop)) {
            startingLoop = new SoundLoopStarting(car, car.getStartingSound(), SoundSource.MASTER);
            ModClientSounds.playSoundLoop(startingLoop, car.level());
        }
    }

    public boolean isSoundPlaying(SoundInstance sound) {
        if (sound == null) {
            return false;
        }
        return Minecraft.getInstance().getSoundManager().isActive(sound);
    }

    private boolean startedLast;

    public void updateSounds() {
        if (!car.isStarted() && car.isStarting()) {
            checkStartingLoop();
        }

        if (car.getSpeed() == 0 && car.isStarted()) {

            if (!startedLast) {
                checkStartLoop();
            } else if (!isSoundPlaying(startLoop)) {
                if (startLoop != null) {
                    startLoop.setDonePlaying();
                    startLoop = null;
                }

                checkIdleLoop();
            }
        }
        if (car.getSpeed() != 0 && car.isStarted()) {
            checkHighLoop();
        }

        startedLast = car.isStarted();
    }

}
