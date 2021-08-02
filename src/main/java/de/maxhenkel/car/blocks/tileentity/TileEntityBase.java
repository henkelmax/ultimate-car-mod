package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.net.MessageSyncTileEntity;
import de.maxhenkel.corelib.entity.EntityUtils;
import de.maxhenkel.corelib.net.NetUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Nameable;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;

public abstract class TileEntityBase extends BlockEntity implements Nameable {

    private Component name;
    private CompoundTag compoundLast;

    public TileEntityBase(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
        super(tileEntityTypeIn, pos, state);
    }

    public void synchronize() {
        if (!level.isClientSide && level instanceof ServerLevel) {
            CompoundTag last = save(new CompoundTag());
            if (compoundLast == null || !compoundLast.equals(last)) {
                ServerLevel serverWorld = (ServerLevel) level;

                MessageSyncTileEntity msg = new MessageSyncTileEntity(worldPosition, last);
                EntityUtils.forEachPlayerAround(serverWorld, worldPosition, 128D, playerEntity -> NetUtils.sendTo(Main.SIMPLE_CHANNEL, playerEntity, msg));
                compoundLast = last;
            }
        }
    }

    public void synchronize(int ticks) {
        if (level.getGameTime() % ticks == 0) {
            synchronize();
        }
    }

    @Override
    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
        load(pkt.getTag());
    }

    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    public abstract Component getTranslatedName();

    public void setCustomName(Component name) {
        this.name = name;
    }

    @Override
    public Component getName() {
        return name != null ? name : getTranslatedName();
    }

    @Override
    @Nullable
    public Component getCustomName() {
        return name;
    }

    public abstract ContainerData getFields();

    @Override
    public CompoundTag save(CompoundTag compound) {
        if (name != null) {
            compound.putString("CustomName", Component.Serializer.toJson(name));
        }
        return super.save(compound);
    }

    @Override
    public void load(CompoundTag compound) {
        if (compound.contains("CustomName")) {
            name = Component.Serializer.fromJson(compound.getString("CustomName"));
        }
        super.load(compound);
    }
}
