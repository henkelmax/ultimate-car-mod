package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;

public abstract class BlockCrop extends CropsBlock {

    public IntegerProperty CROP_AGE = IntegerProperty.create("age", 0, getMaxAge());

    public static final VoxelShape[] SHAPES_BY_AGE = new VoxelShape[]{
            Block.makeCuboidShape(0D, 0D, 0D, 16D, 2D, 16D),
            Block.makeCuboidShape(0D, 0D, 0D, 16D, 4D, 16D),
            Block.makeCuboidShape(0D, 0D, 0D, 16D, 6D, 16D),
            Block.makeCuboidShape(0D, 0D, 0D, 16D, 8D, 16D),
            Block.makeCuboidShape(0D, 0D, 0D, 16D, 10D, 16D),
            Block.makeCuboidShape(0D, 0D, 0D, 16D, 12D, 16D),
            Block.makeCuboidShape(0D, 0D, 0D, 16D, 14D, 16D),
            Block.makeCuboidShape(0D, 0D, 0D, 16D, 16D, 16D)};

    //TODO block loot table
    //TODO check growth rate
    public BlockCrop(String name) {
        super(Properties.create(Material.PLANTS, MaterialColor.GREEN).sound(SoundType.PLANT));
        setRegistryName(new ResourceLocation(Main.MODID, name));
        this.setDefaultState(stateContainer.getBaseState().with(getAgeProperty(), 0));
    }

    public IntegerProperty getAgeProperty() {
        return CROP_AGE;
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader iBlockReader, BlockPos pos, ISelectionContext selectionContext) {
        return SHAPES_BY_AGE[state.get(getAgeProperty())];
    }

    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CROP_AGE);
    }

    @Override
    public abstract int getMaxAge();

}

