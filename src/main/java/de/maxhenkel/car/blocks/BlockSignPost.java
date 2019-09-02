package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;

public class BlockSignPost extends BlockBase implements IItemBlock {

	public static final AxisAlignedBB AABB = new AxisAlignedBB(7.5/16D, 0D, 7.5D/16D, 8.5/16D, 1D, 8.5D/16D);
	
	public BlockSignPost() {
		super(Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(2F).sound(SoundType.METAL));
		setRegistryName(new ResourceLocation(Main.MODID, "sign_post"));
	}

	@Override
	public Item toItem() {
		return new BlockItem(this, new Item.Properties().group(ModItemGroups.TAB_CAR)).setRegistryName(this.getRegistryName());
	}

	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		return Block.makeCuboidShape(7.5D, 0D, 7.5D, 8.5D, 16D, 8.5D);
	}

	@Override
	public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
		return false;
	}

	@Override
	public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
		return false;
	}
}
