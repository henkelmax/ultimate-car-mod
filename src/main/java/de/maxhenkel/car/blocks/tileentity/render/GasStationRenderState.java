package de.maxhenkel.car.blocks.tileentity.render;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.AABB;

import javax.annotation.Nullable;

public class GasStationRenderState extends BlockEntityRenderState {

    @Nullable
    public FormattedCharSequence text;
    public Direction direction;
    @Nullable
    public AABB hitbox;

}
