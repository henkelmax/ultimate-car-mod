package de.maxhenkel.car.items;

import cofh.redstoneflux.api.IEnergyProvider;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.ModBlocks;
import de.maxhenkel.car.blocks.tileentity.TileEntityGenerator;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import java.util.List;

public class ItemBattery extends Item {

	public ItemBattery() {
		setMaxStackSize(1);
		setUnlocalizedName("battery");
		setRegistryName("battery");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
		setMaxDamage(500);
	}

	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {

		int damage=getMaxDamage(stack)-getDamage(stack);

		tooltip.add(new TextComponentTranslation("tooltip.battery_energy", damage).getFormattedText());
		tooltip.add(new TextComponentTranslation("tooltip.battery", damage).getFormattedText());

		super.addInformation(stack, worldIn, tooltip, flagIn);
	}

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return false;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
	    if(worldIn.getBlockState(pos).getBlock().equals(ModBlocks.GENERATOR)){
            TileEntity te=worldIn.getTileEntity(pos);
            if(te instanceof IEnergyProvider){
                IEnergyProvider provider= (IEnergyProvider) te;
                ItemStack stack=player.getHeldItem(hand);

                int energyToFill=stack.getItemDamage();

                int amount=provider.extractEnergy(facing, energyToFill, false);

                stack.setItemDamage(energyToFill-amount);
                player.setHeldItem(hand, stack);
                return EnumActionResult.SUCCESS;
            }
        }

	    return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }
}
