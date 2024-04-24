package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Main;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Arrays;

public class TileEntitySign extends TileEntityBase {

    private String[] text = new String[8];

    public TileEntitySign(BlockPos pos, BlockState state) {
        super(Main.SIGN_TILE_ENTITY_TYPE.get(), pos, state);
        Arrays.fill(text, "");
    }

    @Override
    public void saveAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        super.saveAdditional(compound, provider);
        for (int i = 0; i < text.length; i++) {
            compound.putString("text" + i, text[i]);
        }
    }

    @Override
    public void loadAdditional(CompoundTag compound, HolderLookup.Provider provider) {
        for (int i = 0; i < text.length; i++) {
            this.text[i] = compound.getString("text" + i);
        }
        super.loadAdditional(compound, provider);
    }

    public String getText(int i) {
        if (i < 0 || i >= text.length) {
            return "";
        }
        return text[i];
    }

    public String[] getSignText() {
        return text;
    }

    public void setText(int i, String s) {
        if (i < 0 || i >= text.length || s == null) {
            return;
        }
        text[i] = s;
        setChanged();
        synchronize();
    }

    public void setText(String[] s) {
        if (s == null || s.length != text.length) {
            return;
        }
        text = s;
        setChanged();
        synchronize();
    }

    @Override
    public Component getTranslatedName() {
        return Component.translatable("block.car.sign");
    }

    @Override
    public ContainerData getFields() {
        return new SimpleContainerData(0);
    }
}
