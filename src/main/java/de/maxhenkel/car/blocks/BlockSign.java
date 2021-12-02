package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntitySign;
import de.maxhenkel.car.gui.ContainerSign;
import de.maxhenkel.car.gui.TileEntityContainerProvider;
import de.maxhenkel.corelib.block.DirectionalVoxelShape;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.Nullable;

public class BlockSign extends BlockBase implements EntityBlock, IItemBlock, SimpleWaterloggedBlock {

    private static final VoxelShape SHAPE_NORTH_SOUTH = Block.box(0D, 3D, 7.5D, 16D, 13D, 8.5D);
    private static final VoxelShape SHAPE_EAST_WEST = Block.box(7.5D, 3D, 0D, 8.5D, 13D, 16D);
    private static final VoxelShape SHAPE_POST = Block.box(7.5D, 0D, 7.5D, 8.5D, 3D, 8.5D);

    private static final DirectionalVoxelShape SHAPES = new DirectionalVoxelShape.Builder()
            .direction(Direction.NORTH,
                    SHAPE_NORTH_SOUTH,
                    SHAPE_POST
            )
            .direction(Direction.SOUTH,
                    SHAPE_NORTH_SOUTH,
                    SHAPE_POST
            )
            .direction(Direction.EAST,
                    SHAPE_EAST_WEST,
                    SHAPE_POST
            )
            .direction(Direction.WEST,
                    SHAPE_EAST_WEST,
                    SHAPE_POST
            ).build();

    public static final DirectionProperty FACING = DirectionProperty.create("facing", Direction.Plane.HORIZONTAL);
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;

    public BlockSign() {
        super(Properties.of(Material.METAL, MaterialColor.METAL).strength(20F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "sign"));

        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(WATERLOGGED, false));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockEntity te = worldIn.getBlockEntity(pos);

        if (!(te instanceof TileEntitySign)) {
            return InteractionResult.FAIL;
        }
        TileEntitySign sign = (TileEntitySign) te;
        if (player instanceof ServerPlayer) {
            TileEntityContainerProvider.openGui((ServerPlayer) player, sign, (i, playerInventory, playerEntity) -> new ContainerSign(i, sign));
        }
        return InteractionResult.SUCCESS;
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPES.get(state.getValue(FACING));
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        FluidState ifluidstate = context.getLevel().getFluidState(context.getClickedPos());
        return defaultBlockState().setValue(FACING, context.getHorizontalDirection()).setValue(WATERLOGGED, ifluidstate.is(FluidTags.WATER) && ifluidstate.getAmount() == 8);
    }

    @Override
    public BlockState updateShape(BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        if (stateIn.getValue(WATERLOGGED)) {
            worldIn.scheduleTick(currentPos, Fluids.WATER, Fluids.WATER.getTickDelay(worldIn));
        }
        return super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED);
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        return state.getValue(WATERLOGGED) ? Fluids.WATER.getSource(false) : super.getFluidState(state);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntitySign(blockPos, blockState);
    }

}
