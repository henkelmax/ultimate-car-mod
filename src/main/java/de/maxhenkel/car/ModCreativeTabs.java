package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;

public class ModCreativeTabs {
	
	public static final CreativeTabs TAB_CAR = new CreativeTabs("car") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(Item.getItemFromBlock(ModBlocks.TAR));
		}
		
		@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {
			
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.CANOLA_OIL, 1)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.CANOLA_METHANOL_MIX, 1)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.BIO_DIESEL, 1)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.GLYCERIN, 1)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.METHANOL, 1)));
			super.displayAllRelevantItems(list);
		}
		
	};
	
}
