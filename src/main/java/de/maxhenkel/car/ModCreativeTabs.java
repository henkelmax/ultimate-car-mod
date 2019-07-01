package de.maxhenkel.car;

import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.items.ModItems;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModCreativeTabs {

    public static final ItemGroup TAB_CAR = new ItemGroup("car") {


        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.TAR);
        }

		/*@Override
		public void displayAllRelevantItems(NonNullList<ItemStack> list) {

			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.CANOLA_OIL, 1)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.CANOLA_METHANOL_MIX, 1)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.BIO_DIESEL, 1)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.GLYCERIN, 1)));
			list.add(FluidUtil.getFilledBucket(new FluidStack(ModFluids.METHANOL, 1)));
			super.displayAllRelevantItems(list);
		}*/
    };

    public static final ItemGroup TAB_CAR_PARTS = new ItemGroup("car_parts") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.OAK_BODY);
        }

    };

}
