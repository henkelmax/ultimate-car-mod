package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class BlockCarWorkshopOutter extends BlockBase {

    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 8);

    public BlockCarWorkshopOutter(Properties properties) {
        super(properties.mapColor(MapColor.COLOR_GRAY).strength(3F).sound(SoundType.METAL));

        this.registerDefaultState(stateDefinition.any().setValue(POSITION, 0));
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState blockState, Level level, BlockPos blockPos, Player player, BlockHitResult blockHitResult) {
        BlockPos tePos = findCenter(level, blockPos);

        if (tePos == null) {
            return InteractionResult.FAIL;
        }
        return ModBlocks.CAR_WORKSHOP.get().useWithoutItem(level.getBlockState(tePos), level, tePos, player, blockHitResult);
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);
        validate(worldIn, pos);
    }

    @Override
    protected void affectNeighborsAfterRemoval(BlockState state, ServerLevel level, BlockPos pos, boolean moving) {
        super.affectNeighborsAfterRemoval(state, level, pos, moving);
        validate(level, pos);
    }

    private void validate(Level worldIn, BlockPos pos) {
        BlockPos tePos = findCenter(worldIn, pos);

        if (tePos == null) {
            return;
        }

        BlockEntity te = worldIn.getBlockEntity(tePos);

        if (!(te instanceof TileEntityCarWorkshop)) {
            return;
        }

        TileEntityCarWorkshop workshop = (TileEntityCarWorkshop) te;

        workshop.checkValidity();
    }

    private static BlockPos findCenter(Level world, BlockPos pos) {
        if (isCenter(world, pos.offset(0, 0, 1))) {
            return pos.offset(0, 0, 1);
        }
        if (isCenter(world, pos.offset(1, 0, 0))) {
            return pos.offset(1, 0, 0);
        }
        if (isCenter(world, pos.offset(1, 0, 1))) {
            return pos.offset(1, 0, 1);
        }
        if (isCenter(world, pos.offset(0, 0, -1))) {
            return pos.offset(0, 0, -1);
        }
        if (isCenter(world, pos.offset(-1, 0, 0))) {
            return pos.offset(-1, 0, 0);
        }
        if (isCenter(world, pos.offset(-1, 0, -1))) {
            return pos.offset(-1, 0, -1);
        }
        if (isCenter(world, pos.offset(-1, 0, 1))) {
            return pos.offset(-1, 0, 1);
        }
        if (isCenter(world, pos.offset(1, 0, -1))) {
            return pos.offset(1, 0, -1);
        }
        return null;
    }

    private static boolean isCenter(Level world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().equals(ModBlocks.CAR_WORKSHOP.get());
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(POSITION, 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POSITION);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

}
