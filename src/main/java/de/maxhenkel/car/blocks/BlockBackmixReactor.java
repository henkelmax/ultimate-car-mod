package de.maxhenkel.car.blocks;

import de.maxhenkel.car.blocks.tileentity.TileEntityBackmixReactor;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockBackmixReactor extends BlockGui {

	protected BlockBackmixReactor() {
		super(Material.IRON, "backmix_reactor");
		setHardness(3.0F);
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = playerIn.getHeldItem(hand);

		if (stack != null) {
			FluidStack fluidStack = FluidUtil.getFluidContained(stack);

			if (fluidStack != null) {
				boolean success = BlockTank.handleEmpty(fluidStack, stack, worldIn, pos, playerIn, hand);
				if (success) {
					return true;
				}
			}
			IFluidHandler handler = FluidUtil.getFluidHandler(stack);

			if (handler != null) {
				boolean success1 = BlockTank.handleFill(handler, stack, worldIn, pos, playerIn, hand);
				if (success1) {
					return true;
				}
			}

		}

		return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBackmixReactor();
	}

	@Override
	public int getGUIID() {
		return GuiHandler.GUI_BACKMIX_REACTOR;
	}

}
