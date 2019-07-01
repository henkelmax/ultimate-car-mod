package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityCarWorkshop;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockCarWorkshopOutter extends Block implements IItemBlock {

    public static final IntegerProperty POSITION = IntegerProperty.create("position", 0, 8);

    public BlockCarWorkshopOutter() {
        super(Properties.create(Material.IRON, MaterialColor.GRAY).hardnessAndResistance(3F).sound(SoundType.METAL));
        setRegistryName(new ResourceLocation(Main.MODID, "car_workshop_outter"));

        this.setDefaultState(stateContainer.getBaseState().with(POSITION, 0));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        BlockPos tePos = findCenter(worldIn, pos);

        if (tePos == null) {
            return false;
        }
        return ModBlocks.CAR_WORKSHOP.onBlockActivated(worldIn.getBlockState(tePos), worldIn, tePos, player, handIn, hit);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        validate(worldIn, pos);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        super.onReplaced(state, worldIn, pos, newState, isMoving);
        validate(worldIn, pos);
    }

    private void validate(World worldIn, BlockPos pos) {
        BlockPos tePos = findCenter(worldIn, pos);

        if (tePos == null) {
            return;
        }

        TileEntity te = worldIn.getTileEntity(tePos);

        if (!(te instanceof TileEntityCarWorkshop)) {
            return;
        }

        TileEntityCarWorkshop workshop = (TileEntityCarWorkshop) te;

        workshop.checkValidity();
    }

    private static BlockPos findCenter(World world, BlockPos pos) {
        if (isCenter(world, pos.add(0, 0, 1))) {
            return pos.add(0, 0, 1);
        }
        if (isCenter(world, pos.add(1, 0, 0))) {
            return pos.add(1, 0, 0);
        }
        if (isCenter(world, pos.add(1, 0, 1))) {
            return pos.add(1, 0, 1);
        }
        if (isCenter(world, pos.add(0, 0, -1))) {
            return pos.add(0, 0, -1);
        }
        if (isCenter(world, pos.add(-1, 0, 0))) {
            return pos.add(-1, 0, 0);
        }
        if (isCenter(world, pos.add(-1, 0, -1))) {
            return pos.add(-1, 0, -1);
        }
        if (isCenter(world, pos.add(-1, 0, 1))) {
            return pos.add(-1, 0, 1);
        }
        if (isCenter(world, pos.add(1, 0, -1))) {
            return pos.add(1, 0, -1);
        }
        return null;
    }

    private static boolean isCenter(World world, BlockPos pos) {
        return world.getBlockState(pos).getBlock().equals(ModBlocks.CAR_WORKSHOP);
    }


    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockItemUseContext context) {
        return this.getDefaultState().with(POSITION, 0);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POSITION);
    }


    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

}
