package de.maxhenkel.car.items;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.BlockPaint;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.gui.GuiHandler;
import de.maxhenkel.car.gui.SlotPainter;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class ItemPainter extends Item{

	private boolean isYellow;
	
	public ItemPainter(boolean isYellow) {
		this.isYellow=isYellow;
		if(isYellow){
			setUnlocalizedName("painter_yellow");
			setRegistryName("painter_yellow");
		}else{
			setUnlocalizedName("painter");
			setRegistryName("painter");
		}
		
		setMaxStackSize(1);
		setMaxDamage(1024);

		setCreativeTab(ModCreativeTabs.TAB_CAR);
		
	}
	
	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {
		if(state.getBlock() instanceof BlockPaint){
			return 50F;
		}
		return super.getStrVsBlock(stack, state);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(playerIn.isSneaking()){
			int id;
			if(isYellow){
				id=GuiHandler.GUI_PAINTER_YELLOW;
			}else{
				id=GuiHandler.GUI_PAINTER;
			}
			playerIn.openGui(Main.instance(), id, worldIn, playerIn.getPosition().getX(), playerIn.getPosition().getY(), playerIn.getPosition().getZ());
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(player.isSneaking()){
			return EnumActionResult.PASS;
		}
		
		if(!facing.equals(EnumFacing.UP)){
			return EnumActionResult.FAIL;
		}
		
		if(!BlockPaint.canPlaceBlockAt((IBlockAccess)worldIn, pos.up())){
			return EnumActionResult.FAIL;
		}
		
		if(!worldIn.isAirBlock(pos.up())){
			return EnumActionResult.FAIL;
		}
		
		int index=SlotPainter.getPainterID(player);
		ItemStack stack1=SlotPainter.getPainterStack(player);
		
		if(stack1==null){
			return EnumActionResult.FAIL;
		}
		
		if(index<0||index>=ModBlocks.PAINTS.length){
			return EnumActionResult.FAIL;
		}
		
		BlockPaint block;
		
		if(isYellow){
			block=ModBlocks.YELLOW_PAINTS[index];
		}else{
			block=ModBlocks.PAINTS[index];
		}
		
		
		IBlockState state=block.getDefaultState().withProperty(BlockPaint.FACING, player.getHorizontalFacing());
		
		worldIn.setBlockState(pos.up(), state);
		
		stack1.damageItem(1, player);
		
		return EnumActionResult.SUCCESS;
	}
	
}
