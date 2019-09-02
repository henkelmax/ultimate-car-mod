package de.maxhenkel.car.net;

import de.maxhenkel.car.sounds.SoundLoopTileentity.ISoundLoopable;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkEvent;

@Deprecated
public class MessagePlaySoundLoop implements Message<MessagePlaySoundLoop> {

    private int posX;
    private int posY;
    private int posZ;

    public MessagePlaySoundLoop() {

    }

    public MessagePlaySoundLoop(TileEntity tileEntity) {
        this.posX = tileEntity.getPos().getX();
        this.posY = tileEntity.getPos().getY();
        this.posZ = tileEntity.getPos().getZ();

    }

    @Override
    public void executeServerSide(NetworkEvent.Context context) {

    }

    @Override
    public void executeClientSide(NetworkEvent.Context context) {
        playSound();
    }

    @OnlyIn(Dist.CLIENT)
    private void playSound() {
        PlayerEntity player = Minecraft.getInstance().player;

        TileEntity te = player.world.getTileEntity(new BlockPos(posX, posY, posZ));

        if (te instanceof ISoundLoopable) {
            ISoundLoopable loop = (ISoundLoopable) te;
            loop.play();
        }
    }

    @Override
    public MessagePlaySoundLoop fromBytes(PacketBuffer buf) {
        this.posX = buf.readInt();
        this.posY = buf.readInt();
        this.posZ = buf.readInt();

        return this;
    }

    @Override
    public void toBytes(PacketBuffer buf) {
        buf.writeInt(posX);
        buf.writeInt(posY);
        buf.writeInt(posZ);
    }
}
