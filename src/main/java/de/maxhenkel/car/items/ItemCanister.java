package de.maxhenkel.car.items;

import java.util.List;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ItemCanister extends Item {

	public final int maxFuel;

	public ItemCanister() {
		setMaxStackSize(1);
		setUnlocalizedName("canister");
		setRegistryName("canister");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		maxFuel=Config.canisterMaxFuel;
	}

	@Override
	public EnumActionResult onItemUse(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!playerIn.isSneaking()) {
			return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}

		IBlockState state = worldIn.getBlockState(pos);

		TileEntity te = null;

		if (state.getBlock().equals(ModBlocks.FUEL_STATION_TOP)) {
			te = worldIn.getTileEntity(pos.down());
		} else{
			te = worldIn.getTileEntity(pos);
		}

		if (te == null) {
			return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
		}

		if (te instanceof TileEntityFuelStation) {
			TileEntityFuelStation fuel = (TileEntityFuelStation) te;
			boolean success=ModItems.CANISTER.fillCanister(playerIn.getHeldItem(hand), fuel);
			if(success){
				ModSounds.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, worldIn, pos, null, SoundCategory.BLOCKS);
			}
			return EnumActionResult.SUCCESS;
		}
		
		if(te instanceof IFluidHandler){
			IFluidHandler handler=(IFluidHandler) te;
			
			boolean success=ModItems.CANISTER.fuelFluidHandler(playerIn.getHeldItem(hand), handler);
			if(success){
				ModSounds.playSound(SoundEvents.BLOCK_BREWING_STAND_BREW, worldIn, pos, null, SoundCategory.BLOCKS);
			}
			return EnumActionResult.SUCCESS;
		}
		return super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
		if (stack.hasTagCompound()) {
			NBTTagCompound comp = stack.getTagCompound();

			if (comp.hasKey("fuel")) {
				NBTTagCompound fuel = comp.getCompoundTag("fuel");

				FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fuel);
				if (fluidStack == null) {
					addInfo("-", 0, tooltip);
					super.addInformation(stack, playerIn, tooltip, advanced);
					return;
				}

				addInfo(fluidStack.getLocalizedName(), fluidStack.amount, tooltip);
				super.addInformation(stack, playerIn, tooltip, advanced);
				return;
			}
			addInfo("-", 0, tooltip);
			super.addInformation(stack, playerIn, tooltip, advanced);
			return;
		}
		addInfo("-", 0, tooltip);
		super.addInformation(stack, playerIn, tooltip, advanced);
	}
	
	private void addInfo(String fluid, int amount, List<String> tooltip){
		tooltip.add(new TextComponentTranslation("canister.fluid", fluid)
				.getFormattedText());
		tooltip.add(new TextComponentTranslation("canister.amount", amount).getFormattedText());
	}

	public boolean fillCanister(ItemStack canister, TileEntityFuelStation tile) {

		if (!canister.hasTagCompound()) {
			canister.setTagCompound(new NBTTagCompound());
		}

		NBTTagCompound comp = canister.getTagCompound();

		FluidStack fluid = null;

		if (comp.hasKey("fuel")) {
			fluid = FluidStack.loadFluidStackFromNBT(comp.getCompoundTag("fuel"));
		}

		int maxAmount=maxFuel;
		
		if(fluid!=null){
			maxAmount=maxFuel-fluid.amount;
		}
		
		if(maxAmount<=0){
			return false;
		}
		
		FluidStack result = tile.drain(maxAmount, true);

		if (result == null) {
			return false;
		}

		if (fluid == null) {
			comp.setTag("fuel", result.writeToNBT(new NBTTagCompound()));
			return true;
		}

		if (result.getFluid().equals(fluid.getFluid())) {
			fluid.amount += result.amount;
			comp.setTag("fuel", fluid.writeToNBT(new NBTTagCompound()));
		}
		return true;
	}

	/*public boolean fuelCar(ItemStack canister, EntityCarFuelBase car) {
		if (!canister.hasTagCompound()) {
			return false;
		}

		NBTTagCompound comp = canister.getTagCompound();

		if (!comp.hasKey("fuel")) {
			return false;
		}

		NBTTagCompound fluid = comp.getCompoundTag("fuel");

		FluidStack stack = FluidStack.loadFluidStackFromNBT(fluid);

		if (stack == null) {
			return false;
		}

		int fueledAmount = car.fill(stack, true);

		stack.amount -= fueledAmount;

		if (stack.amount <= 0) {
			comp.setTag("fuel", new NBTTagCompound());
			return true;
		}

		NBTTagCompound f = new NBTTagCompound();
		stack.writeToNBT(f);
		comp.setTag("fuel", f);
		return true;
	}*/
	
	public boolean fuelFluidHandler(ItemStack canister, IFluidHandler handler) {
		if (!canister.hasTagCompound()) {
			return false;
		}

		NBTTagCompound comp = canister.getTagCompound();

		if (!comp.hasKey("fuel")) {
			return false;
		}

		NBTTagCompound fluid = comp.getCompoundTag("fuel");

		FluidStack stack = FluidStack.loadFluidStackFromNBT(fluid);

		if (stack == null) {
			return false;
		}

		int fueledAmount = handler.fill(stack, true);

		stack.amount -= fueledAmount;

		if (stack.amount <= 0) {
			comp.setTag("fuel", new NBTTagCompound());
			return true;
		}

		NBTTagCompound f = new NBTTagCompound();
		stack.writeToNBT(f);
		comp.setTag("fuel", f);
		return true;
	}

}
