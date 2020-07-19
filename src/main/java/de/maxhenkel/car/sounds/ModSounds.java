package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Main;
import de.maxhenkel.corelib.reflection.ReflectionUtils;
import de.maxhenkel.tools.NoRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.TickableSound;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ModSounds {

    public static SoundEvent ENGINE_STOP = addSound("engine_stop");
    public static SoundEvent ENGINE_STARTING = addSound("engine_starting");
    public static SoundEvent ENGINE_START = addSound("engine_start");
    public static SoundEvent ENGINE_IDLE = addSound("engine_idle");
    public static SoundEvent ENGINE_HIGH = addSound("engine_high");
    public static SoundEvent ENGINE_FAIL = addSound("engine_fail");
    public static SoundEvent SPORT_ENGINE_STOP = addSound("sport_engine_stop");
    public static SoundEvent SPORT_ENGINE_STARTING = addSound("sport_engine_starting");
    public static SoundEvent SPORT_ENGINE_START = addSound("sport_engine_start");
    public static SoundEvent SPORT_ENGINE_IDLE = addSound("sport_engine_idle");
    public static SoundEvent SPORT_ENGINE_HIGH = addSound("sport_engine_high");
    public static SoundEvent SPORT_ENGINE_FAIL = addSound("sport_engine_fail");
    public static SoundEvent TRUCK_ENGINE_STOP = addSound("truck_engine_stop");
    public static SoundEvent TRUCK_ENGINE_STARTING = addSound("truck_engine_starting");
    public static SoundEvent TRUCK_ENGINE_START = addSound("truck_engine_start");
    public static SoundEvent TRUCK_ENGINE_IDLE = addSound("truck_engine_idle");
    public static SoundEvent TRUCK_ENGINE_HIGH = addSound("truck_engine_high");
    public static SoundEvent TRUCK_ENGINE_FAIL = addSound("truck_engine_fail");
    public static SoundEvent CAR_CRASH = addSound("car_crash");
    public static SoundEvent GAS_STATION = addSound("gas_station");
    public static SoundEvent GENERATOR = addSound("generator");
    public static SoundEvent CAR_HORN = addSound("car_horn");
    public static SoundEvent CAR_LOCK = addSound("car_lock");
    public static SoundEvent CAR_UNLOCK = addSound("car_unlock");
    public static SoundEvent RATCHET = addSound("ratchet");
    public static SoundEvent GAS_STATION_ATTENDANT = addSound("gas_station_attendant");

    public static List<SoundEvent> getAll() {
        List<SoundEvent> sounds = new ArrayList<>();
        for (Field field : ModSounds.class.getFields()) {
            if (ReflectionUtils.hasAnnotation(field, NoRegister.class)) {
                continue;
            }
            try {
                Object obj = field.get(null);
                if (obj instanceof SoundEvent) {
                    sounds.add((SoundEvent) obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sounds;
    }

    public static SoundEvent addSound(String soundName) {
        SoundEvent event = new SoundEvent(new ResourceLocation(Main.MODID, soundName));
        event.setRegistryName(new ResourceLocation(Main.MODID, soundName));
        return event;
    }

    public static void playSound(SoundEvent evt, World world, BlockPos pos, PlayerEntity entity, SoundCategory category, float volume) {
        playSound(evt, world, pos, entity, category, volume, 1.0F);
    }

    public static void playSound(SoundEvent evt, World world, BlockPos pos, PlayerEntity entity, SoundCategory category, float volume, float pitch) {
        if (entity != null) {
            world.playSound(entity, pos, evt, category, volume, pitch);
        } else {
            if (!world.isRemote) {
                world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, evt, category, volume, pitch);
            }
        }
    }

    public static void playSound(SoundEvent evt, World world, BlockPos pos, PlayerEntity entity, SoundCategory category) {
        playSound(evt, world, pos, entity, category, 0.15F);
    }

    public static boolean isCarSoundCategory(SoundEvent event) {
        if (event == null) {
            return false;
        }
        return event.equals(ENGINE_STOP) ||
                event.equals(ENGINE_STARTING) ||
                event.equals(ENGINE_START) ||
                event.equals(ENGINE_IDLE) ||
                event.equals(ENGINE_HIGH) ||
                event.equals(ENGINE_FAIL) ||
                event.equals(SPORT_ENGINE_STOP) ||
                event.equals(SPORT_ENGINE_STARTING) ||
                event.equals(SPORT_ENGINE_START) ||
                event.equals(SPORT_ENGINE_IDLE) ||
                event.equals(SPORT_ENGINE_HIGH) ||
                event.equals(SPORT_ENGINE_FAIL) ||
                event.equals(TRUCK_ENGINE_STOP) ||
                event.equals(TRUCK_ENGINE_STARTING) ||
                event.equals(TRUCK_ENGINE_START) ||
                event.equals(TRUCK_ENGINE_IDLE) ||
                event.equals(TRUCK_ENGINE_HIGH) ||
                event.equals(TRUCK_ENGINE_FAIL) ||
                event.equals(CAR_CRASH) ||
                event.equals(CAR_HORN) ||
                event.equals(CAR_LOCK) ||
                event.equals(CAR_UNLOCK);

    }

    @OnlyIn(Dist.CLIENT)
    public static void playSoundLoop(TickableSound loop, World world) {
        if (world.isRemote) {
            Minecraft.getInstance().getSoundHandler().play(loop);
        }
    }

}
