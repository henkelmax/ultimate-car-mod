package de.maxhenkel.car.blocks.tileentity.render;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.neoforged.neoforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class TankRenderState extends BlockEntityRenderState {

    public float amount;
    public FluidStack fluid;
    public boolean[] connections = new boolean[Direction.values().length];
    public int tint;
    @Nullable
    public TextureAtlasSprite texture;

    public boolean isTankConnectedTo(Direction direction){
        return connections[direction.ordinal()];
    }

}
