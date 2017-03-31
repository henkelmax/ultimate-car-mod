package de.maxhenkel.car;

import java.util.LinkedList;
import java.util.List;

import net.minecraft.util.math.BlockPos;

public class BlockPosList {

	private List<BlockPos> positions;
	
	public BlockPosList() {
		this.positions=new LinkedList<BlockPos>();
	}
	
	public boolean contains(BlockPos pos){
		return positions.contains(pos);
	}
	
	public void add(BlockPos pos){
		if(!contains(pos)){
			positions.add(pos);
		}
	}
	
}
