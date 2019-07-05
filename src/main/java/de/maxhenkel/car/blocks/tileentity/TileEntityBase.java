package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.net.MessageSyncTileEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.IIntArray;
import net.minecraft.util.INameable;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.ServerWorld;
import net.minecraftforge.fml.network.NetworkDirection;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends TileEntity implements INameable {

    private ITextComponent name;
    private CompoundNBT compoundLast;

    public TileEntityBase(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public void synchronize() {
        if (!world.isRemote && world instanceof ServerWorld) {
            CompoundNBT last = write(new CompoundNBT());
            if (compoundLast == null || !compoundLast.equals(last)) {
                ServerWorld serverWorld = (ServerWorld) world;

                MessageSyncTileEntity msg = new MessageSyncTileEntity(pos, last);
                serverWorld.getPlayers(player -> getDistanceSq(player.posX, player.posY, player.posZ) / 2D <= 128D).forEach(player -> Main.SIMPLE_CHANNEL.sendTo(msg, player.connection.netManager, NetworkDirection.PLAY_TO_CLIENT));

                this.compoundLast = last;
            }
        }
    }

    public void synchronize(int ticks) {
        if (world.getGameTime() % ticks == 0) {
            synchronize();
        }
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    public abstract ITextComponent getTranslatedName();

    public void setCustomName(ITextComponent name) {
        this.name = name;
    }

    @Override
    public ITextComponent getName() {
        return name != null ? name : getTranslatedName();
    }

    @Override
    @Nullable
    public ITextComponent getCustomName() {
        return name;
    }

    public abstract IIntArray getFields();

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        if (name != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(name));
        }
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        if (compound.contains("CustomName")) {
            name = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
        super.read(compound);
    }
}
