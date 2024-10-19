package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityDynamo;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCrank extends BlockBase {

    public static final IntegerProperty CRANK_POS = IntegerProperty.create("rotation", 0, 7);

    public static final VoxelShape SHAPE = Block.box(3.2D, 0D, 3.2D, 12.8D, 9.6D, 12.8D);

    public BlockCrank(Properties properties) {
        super(properties.mapColor(MapColor.WOOD).strength(0.5F).sound(SoundType.WOOD));
        registerDefaultState(stateDefinition.any().setValue(CRANK_POS, 0));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        BlockEntity te = level.getBlockEntity(blockPos.below());

        if (!(te instanceof TileEntityDynamo)) {
            return InteractionResult.FAIL;
        }

        TileEntityDynamo dyn = (TileEntityDynamo) te;

        dyn.addEnergy(dyn.generation);

        int i = blockState.getValue(CRANK_POS) + 1;
        if (i > 7) {
            i = 0;
        }

        level.setBlock(blockPos, blockState.setValue(CRANK_POS, i), 3);

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CRANK_POS);
    }

}
