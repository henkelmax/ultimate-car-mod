package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropsBlock;
import net.minecraft.block.material.Material;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import java.util.Random;

//TODO block loot table
//TODO check growth rate
public class BlockCanolaCrop extends CropsBlock {
    public static final IntegerProperty CANOLA_AGE = BlockStateProperties.AGE_0_3;
    //TODO check sizes
    private static final VoxelShape[] SHAPE = new VoxelShape[]{Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D), Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D)};

    public BlockCanolaCrop() {
        super(Properties.create(Material.PLANTS));
        setRegistryName(new ResourceLocation(Main.MODID, "canola"));
    }

    public IntegerProperty getAgeProperty() {
        return CANOLA_AGE;
    }

    @Override
    public int getMaxAge() {
        return 3;
    }

    @Override
    protected IItemProvider getSeedsItem() {
        return ModItems.CANOLA_SEEDS;
    }

    @Override
    public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
        if (random.nextInt(3) != 0) {
            super.tick(state, worldIn, pos, random);
        }
    }

    @Override
    protected int getBonemealAgeIncrease(World worldIn) {
        return super.getBonemealAgeIncrease(worldIn) / 3;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CANOLA_AGE);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader reader, BlockPos pos, ISelectionContext context) {
        return SHAPE[state.get(this.getAgeProperty())];
    }

}
