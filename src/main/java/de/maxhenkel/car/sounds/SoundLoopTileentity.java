package de.maxhenkel.car.sounds;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class SoundLoopTileentity extends AbstractTickableSoundInstance {

    protected Level world;
    protected BlockPos pos;

    public SoundLoopTileentity(SoundEvent event, SoundSource category, BlockEntity tileEntity) {
        super(event, category, SoundInstance.createUnseededRandom());
        this.world = tileEntity.getLevel();
        this.pos = tileEntity.getBlockPos();
        this.looping = true;
        this.delay = 0;
        this.x = pos.getX();
        this.y = pos.getY();
        this.z = pos.getZ();
        this.volume = 0.15F;
        this.pitch = 1.0F;
    }

    @Override
    public void tick() {
        if (isStopped()) {
            return;
        }

        BlockEntity tileEntity = getTileEntity();

        if (tileEntity == null || tileEntity.getLevel() == null) {
            stop();
            return;
        }

        if (tileEntity.getLevel().isClientSide()) {
            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null || !player.isAlive()) {
                stop();
                return;
            }
        }

        if (!(tileEntity instanceof ISoundLoopable)) {
            stop();
            return;
        }

        ISoundLoopable loop = (ISoundLoopable) tileEntity;

        if (!loop.shouldSoundBePlayed()) {
            stop();
            return;
        }
    }

    public BlockEntity getTileEntity() {
        return world.getBlockEntity(pos);
    }

    public interface ISoundLoopable {
        boolean shouldSoundBePlayed();

        void play();
    }

}