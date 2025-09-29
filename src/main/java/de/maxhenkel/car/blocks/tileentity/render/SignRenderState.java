package de.maxhenkel.car.blocks.tileentity.render;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;
import net.minecraft.util.FormattedCharSequence;

public class SignRenderState extends BlockEntityRenderState {

    public Direction direction;
    public FormattedCharSequence[] text = new FormattedCharSequence[8];

}
