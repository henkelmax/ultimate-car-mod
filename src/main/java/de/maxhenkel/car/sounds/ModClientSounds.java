package de.maxhenkel.car.sounds;

import de.maxhenkel.car.blocks.tileentity.TileEntityGasStation;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;

public class ModClientSounds {

    public static void playSoundLoop(AbstractTickableSoundInstance loop, Level world) {
        if (world.isClientSide()) {
            Minecraft.getInstance().getSoundManager().play(loop);
        }
    }

    public static void playGasStationSound(TileEntityGasStation gasStation) {
        playSoundLoop(new SoundLoopTileentity(ModSounds.GAS_STATION.get(), SoundSource.BLOCKS, gasStation), gasStation.getLevel());
    }

}
