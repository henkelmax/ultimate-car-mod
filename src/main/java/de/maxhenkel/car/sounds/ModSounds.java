package de.maxhenkel.car.sounds;

import de.maxhenkel.car.Main;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.fml.javafmlmod.FMLJavaModLoadingContext;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.ForgeRegistries;
import net.neoforged.neoforge.registries.RegistryObject;

public class ModSounds {

    private static final DeferredRegister<SoundEvent> SOUND_REGISTER = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Main.MODID);

    public static RegistryObject<SoundEvent> ENGINE_STOP = addSound("engine_stop");
    public static RegistryObject<SoundEvent> ENGINE_STARTING = addSound("engine_starting");
    public static RegistryObject<SoundEvent> ENGINE_START = addSound("engine_start");
    public static RegistryObject<SoundEvent> ENGINE_IDLE = addSound("engine_idle");
    public static RegistryObject<SoundEvent> ENGINE_HIGH = addSound("engine_high");
    public static RegistryObject<SoundEvent> ENGINE_FAIL = addSound("engine_fail");
    public static RegistryObject<SoundEvent> SPORT_ENGINE_STOP = addSound("sport_engine_stop");
    public static RegistryObject<SoundEvent> SPORT_ENGINE_STARTING = addSound("sport_engine_starting");
    public static RegistryObject<SoundEvent> SPORT_ENGINE_START = addSound("sport_engine_start");
    public static RegistryObject<SoundEvent> SPORT_ENGINE_IDLE = addSound("sport_engine_idle");
    public static RegistryObject<SoundEvent> SPORT_ENGINE_HIGH = addSound("sport_engine_high");
    public static RegistryObject<SoundEvent> SPORT_ENGINE_FAIL = addSound("sport_engine_fail");
    public static RegistryObject<SoundEvent> TRUCK_ENGINE_STOP = addSound("truck_engine_stop");
    public static RegistryObject<SoundEvent> TRUCK_ENGINE_STARTING = addSound("truck_engine_starting");
    public static RegistryObject<SoundEvent> TRUCK_ENGINE_START = addSound("truck_engine_start");
    public static RegistryObject<SoundEvent> TRUCK_ENGINE_IDLE = addSound("truck_engine_idle");
    public static RegistryObject<SoundEvent> TRUCK_ENGINE_HIGH = addSound("truck_engine_high");
    public static RegistryObject<SoundEvent> TRUCK_ENGINE_FAIL = addSound("truck_engine_fail");
    public static RegistryObject<SoundEvent> CAR_CRASH = addSound("car_crash");
    public static RegistryObject<SoundEvent> GAS_STATION = addSound("gas_station");
    public static RegistryObject<SoundEvent> GENERATOR = addSound("generator");
    public static RegistryObject<SoundEvent> CAR_HORN = addSound("car_horn");
    public static RegistryObject<SoundEvent> CAR_LOCK = addSound("car_lock");
    public static RegistryObject<SoundEvent> CAR_UNLOCK = addSound("car_unlock");
    public static RegistryObject<SoundEvent> RATCHET = addSound("ratchet");
    public static RegistryObject<SoundEvent> GAS_STATION_ATTENDANT = addSound("gas_station_attendant");

    public static RegistryObject<SoundEvent> addSound(String soundName) {
        return SOUND_REGISTER.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Main.MODID, soundName)));
    }

    public static void init() {
        SOUND_REGISTER.register(FMLJavaModLoadingContext.get().getModEventBus());
    }

    public static void playSound(SoundEvent evt, Level world, BlockPos pos, Player entity, SoundSource category, float volume) {
        playSound(evt, world, pos, entity, category, volume, 1.0F);
    }

    public static void playSound(SoundEvent evt, Level world, BlockPos pos, Player entity, SoundSource category, float volume, float pitch) {
        if (entity != null) {
            world.playSound(entity, pos, evt, category, volume, pitch);
        } else {
            if (!world.isClientSide) {
                world.playSound(null, (double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, evt, category, volume, pitch);
            }
        }
    }

    public static void playSound(SoundEvent evt, Level world, BlockPos pos, Player entity, SoundSource category) {
        playSound(evt, world, pos, entity, category, 0.15F);
    }

    public static boolean isCarSoundCategory(SoundEvent event) {
        if (event == null) {
            return false;
        }
        return event.equals(ENGINE_STOP.get()) ||
                event.equals(ENGINE_STARTING.get()) ||
                event.equals(ENGINE_START.get()) ||
                event.equals(ENGINE_IDLE.get()) ||
                event.equals(ENGINE_HIGH.get()) ||
                event.equals(ENGINE_FAIL.get()) ||
                event.equals(SPORT_ENGINE_STOP.get()) ||
                event.equals(SPORT_ENGINE_STARTING.get()) ||
                event.equals(SPORT_ENGINE_START.get()) ||
                event.equals(SPORT_ENGINE_IDLE.get()) ||
                event.equals(SPORT_ENGINE_HIGH.get()) ||
                event.equals(SPORT_ENGINE_FAIL.get()) ||
                event.equals(TRUCK_ENGINE_STOP.get()) ||
                event.equals(TRUCK_ENGINE_STARTING.get()) ||
                event.equals(TRUCK_ENGINE_START.get()) ||
                event.equals(TRUCK_ENGINE_IDLE.get()) ||
                event.equals(TRUCK_ENGINE_HIGH.get()) ||
                event.equals(TRUCK_ENGINE_FAIL.get()) ||
                event.equals(CAR_CRASH.get()) ||
                event.equals(CAR_HORN.get()) ||
                event.equals(CAR_LOCK.get()) ||
                event.equals(CAR_UNLOCK.get());

    }

    @OnlyIn(Dist.CLIENT)
    public static void playSoundLoop(AbstractTickableSoundInstance loop, Level world) {
        if (world.isClientSide) {
            Minecraft.getInstance().getSoundManager().play(loop);
        }
    }

}
