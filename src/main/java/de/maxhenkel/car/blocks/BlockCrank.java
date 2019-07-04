package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityDynamo;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;

public class BlockCrank extends BlockBase implements IItemBlock {

    public static final IntegerProperty CRANK_POS = IntegerProperty.create("rotation", 0, 7);

    public static final VoxelShape SHAPE = Block.makeCuboidShape(3.2D, 0D, 3.2D, 12.8D, 9.6D, 12.8D);

    public BlockCrank() {
        super(Properties.create(Material.WOOD, MaterialColor.WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(Main.MODID, "crank"));
        setDefaultState(stateContainer.getBaseState().with(CRANK_POS, 0));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity te = worldIn.getTileEntity(pos.down());

        if (!(te instanceof TileEntityDynamo)) {
            return false;
        }

        TileEntityDynamo dyn = (TileEntityDynamo) te;

        dyn.addEnergy(dyn.generation);

        int i = state.get(CRANK_POS) + 1;
        if (i > 7) {
            i = 0;
        }

        worldIn.setBlockState(pos, state.with(CRANK_POS, i), 3);

        return true;
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CRANK_POS);
    }


}
