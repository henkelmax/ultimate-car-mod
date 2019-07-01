package de.maxhenkel.tools;

import javax.annotation.Nullable;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class BlockSelector implements Selector<BlockState>{

	private Block block;
	private int meta;
	
	public BlockSelector(Block block, int meta) {
		this.block = block;
		this.meta = meta;
	}
	
	public Block getBlock() {
		return block;
	}
	
	public int getMeta() {
		return meta;
	}
	
	@Nullable
	public static BlockSelector fromString(String str){
		String[] split = str.split(":");
		if (split.length < 2) {
			return null;
		}

		Block block = ForgeRegistries.BLOCKS.getValue(new ResourceLocation(split[0], split[1]));
		if (block == null||block.equals(Blocks.AIR)) {
			return null;
		}
		
		int meta=-1;
		
		if(split.length>=3){
			try{
				meta=Integer.parseInt(split[2]);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return new BlockSelector(block, meta);
	}
	
	public String toString() {
		if(block==null|| block.equals(Blocks.AIR)){
			return "";
		}
		
		ResourceLocation loc=block.getRegistryName();
		
		String str=loc.getNamespace() +":" +loc.getPath();
		
		if(meta>=0){
			str=str+":" +meta;
		}
		
		return  str;
	}

	@Override
	public boolean isValid(BlockState state) {
		if(block==null||state==null||state.getBlock()==null){
			return false;
		}

		if(!block.equals(state.getBlock())){
			return false;
		}

		//TODO metadata
		/*if(meta<0){
			return true;
		}

		if(meta!=block.getMetaFromState(state)){
			return false;
		}*/

		return true;
	}
}
