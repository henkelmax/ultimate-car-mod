package de.maxhenkel.car.blocks.tileentity;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.maxhenkel.tools.BlockPosList;
import de.maxhenkel.car.Config;
import de.maxhenkel.car.blocks.ModBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;

public class TileEntityCable extends TileEntityBase implements ITickable, IEnergyReceiver{

	private final int transferRate;
	
	public TileEntityCable(int transferRate) {
		this.transferRate=transferRate;
	}
	
	public TileEntityCable() {
		this(Config.cableTransferRate);
	}
	
	@Override
	public void update() {
		
		if (world.isRemote) {
			return;
		}
		
		int energy=0;
		Map<IEnergyProvider, EnumFacing> providers=new HashMap<IEnergyProvider, EnumFacing>();
		
		for(EnumFacing facing:EnumFacing.values()){
			IEnergyProvider provider=getEnergyProvider(facing);
			
			if(provider==null){
				continue;
			}
			
			int cex=Math.max(0, transferRate-energy);
			
			if(cex<=0){
				break;
			}
			
			int extract=provider.extractEnergy(facing.getOpposite(), cex, true);
			
			if(extract>0){
				energy+=extract;
				providers.put(provider, facing.getOpposite());
			}
			
		}
		
		if(energy<=0){
			return;
		}
		
		Map<IEnergyReceiver, EnumFacing> receivers=new HashMap<IEnergyReceiver, EnumFacing>();
		
		getConnectedReceivers(receivers, new BlockPosList(), pos);
		
		if(receivers.size()<=0){
			return;
		}
		
		int split=energy/receivers.size();
		
		if(split<=0){
			return; //TODO handle
		}
		
		int received=0;
		
		for(Entry<IEnergyReceiver, EnumFacing> entry:receivers.entrySet()){
			received+=entry.getKey().receiveEnergy(entry.getValue(), split, false);
		}
		
		for(Entry<IEnergyProvider, EnumFacing> entry:providers.entrySet()){
			if(received<=0){
				break;
			}
			received-=entry.getKey().extractEnergy(entry.getValue(), received, false);
		}
		
	}
	
	public void drainProvider(IEnergyProvider provider){
		
	}
	
	public void getConnectedReceivers(Map<IEnergyReceiver, EnumFacing> receivers, BlockPosList positions, BlockPos pos){
		for(EnumFacing side:EnumFacing.values()){
			BlockPos p=pos.offset(side);
			
			if(positions.contains(p)){
				continue;
			}
			
			positions.add(p);
			
			IBlockState state=world.getBlockState(p);
			
			if(state.getBlock().equals(ModBlocks.CABLE)){
				getConnectedReceivers(receivers, positions, p);
				continue;
			}
			
			TileEntity te=world.getTileEntity(p);
			
			if(!(te instanceof IEnergyReceiver)){
				continue;
			}
			
			IEnergyReceiver receiver=(IEnergyReceiver) te;
			
			if(receiver.equals(this)){
				continue;
			}
			
			receivers.put(receiver, side.getOpposite());
		}
	}
	
	@Nullable
	public IEnergyReceiver getEnergyReciever(EnumFacing side){
		TileEntity te=world.getTileEntity(pos.offset(side));
		
		if(te instanceof IEnergyReceiver){
			return (IEnergyReceiver) te;
		}
		return null;
	}
	
	@Nullable
	public IEnergyProvider getEnergyProvider(EnumFacing side){
		TileEntity te=world.getTileEntity(pos.offset(side));
		
		if(te instanceof IEnergyProvider){
			return (IEnergyProvider) te;
		}
		return null;
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int receiveEnergy(EnumFacing from, int maxReceive, boolean simulate) {
		/*int amount=Math.min(maxReceive, transferRate-energyStored);
		
		if(!simulate){
			energyStored+=amount;
			markDirty();
			if(amount>0){
				sidesReceivedFrom[from.getIndex()]=true;
			}
		}
		
		return amount;*/
		return 0;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return 0;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return 0;
	}

	
}
