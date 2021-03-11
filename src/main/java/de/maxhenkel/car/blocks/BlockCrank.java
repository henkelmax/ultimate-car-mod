package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityDynamo;
import de.maxhenkel.corelib.block.IItemBlock;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BlockCrank extends BlockBase implements IItemBlock {

    public static final IntegerProperty CRANK_POS = IntegerProperty.create("rotation", 0, 7);

    public static final VoxelShape SHAPE = Block.box(3.2D, 0D, 3.2D, 12.8D, 9.6D, 12.8D);

    public BlockCrank() {
        super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(0.5F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(Main.MODID, "crank"));
        registerDefaultState(stateDefinition.any().setValue(CRANK_POS, 0));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        TileEntity te = worldIn.getBlockEntity(pos.below());

        if (!(te instanceof TileEntityDynamo)) {
            return ActionResultType.FAIL;
        }

        TileEntityDynamo dyn = (TileEntityDynamo) te;

        dyn.addEnergy(dyn.generation);

        int i = state.getValue(CRANK_POS) + 1;
        if (i > 7) {
            i = 0;
        }

        worldIn.setBlock(pos, state.setValue(CRANK_POS, i), 3);

        return ActionResultType.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(CRANK_POS);
    }

}
