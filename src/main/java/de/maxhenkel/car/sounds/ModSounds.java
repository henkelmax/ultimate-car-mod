package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.tools.NoRegister;
import de.maxhenkel.tools.ReflectionHelper;
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
    public static SoundEvent ENGINE_STOP = registerSound("engine_stop");
    public static SoundEvent ENGINE_STARTING = registerSound("engine_starting");
    public static SoundEvent ENGINE_START = registerSound("engine_start");
    public static SoundEvent ENGINE_IDLE = registerSound("engine_idle");
    public static SoundEvent ENGINE_HIGH = registerSound("engine_high");
    public static SoundEvent ENGINE_FAIL = registerSound("engine_fail");
    public static SoundEvent SPORT_ENGINE_STOP = registerSound("sport_engine_stop");
    public static SoundEvent SPORT_ENGINE_STARTING = registerSound("sport_engine_starting");
    public static SoundEvent SPORT_ENGINE_START = registerSound("sport_engine_start");
    public static SoundEvent SPORT_ENGINE_IDLE = registerSound("sport_engine_idle");
    public static SoundEvent SPORT_ENGINE_HIGH = registerSound("sport_engine_high");
    public static SoundEvent SPORT_ENGINE_FAIL = registerSound("sport_engine_fail");
    public static SoundEvent CAR_CRASH = registerSound("car_crash");
    public static SoundEvent GAS_STATION = registerSound("gas_station");
    public static SoundEvent GENERATOR = registerSound("generator");
    public static SoundEvent CAR_HORN = registerSound("car_horn");
    public static SoundEvent CAR_LOCK = registerSound("car_lock");
    public static SoundEvent CAR_UNLOCK = registerSound("car_unlock");
    public static SoundEvent RATCHET = registerSound("ratchet");

    public static List<SoundEvent> getAll() {
        List<SoundEvent> sounds = new ArrayList<>();
        for (Field field : ModBlocks.class.getFields()) {
            if (ReflectionHelper.hasAnnotation(field, NoRegister.class)) {
                continue;
            }
            try {
                Object obj = field.get(null);
                if (obj != null && obj instanceof SoundEvent) {
                    sounds.add((SoundEvent) obj);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return sounds;
    }

    public static SoundEvent registerSound(String soundName) {
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
                world.playSound(entity, pos, evt, category, volume, pitch);
            }
        }
    }

    public static void playSound(SoundEvent evt, World world, BlockPos pos, PlayerEntity entity, SoundCategory category) {
        playSound(evt, world, pos, entity, category, 0.15F);
    }

    @OnlyIn(Dist.CLIENT)
    public static void playSoundLoop(TickableSound loop, World world) {
        if (world.isRemote) {
            Minecraft.getInstance().getSoundHandler().play(loop);
        }
    }
}
