package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.fluids.ModFluids;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockGlycerin extends BlockFluidClassic {

	public BlockGlycerin() {
		super(ModFluids.GLYCERIN, Material.WATER);
		setRegistryName("glycerin");
		setUnlocalizedName("glycerin");
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		if(entityIn instanceof EntityPlayer){
			EntityPlayer player=(EntityPlayer) entityIn;
			if(!player.capabilities.isCreativeMode){
				player.addPotionEffect(new PotionEffect(Potion.getPotionById(9), 100, 0, true, false));
			}
		}
		super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
	}

}
