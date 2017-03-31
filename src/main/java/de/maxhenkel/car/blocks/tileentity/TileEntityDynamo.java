package de.maxhenkel.car.blocks.tileentity;

import cofh.api.energy.IEnergyProvider;
import cofh.api.energy.IEnergyReceiver;
import de.maxhenkel.car.Config;
import de.maxhenkel.car.energy.EnergyUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;

public class TileEntityDynamo extends TileEntityBase implements IEnergyProvider, ITickable{

	private int storedEnergy;
	public final int maxStorage;
	public final int generation;
	
	public TileEntityDynamo() {
		this.maxStorage=Config.dynamoEnergyStorage;
		this.generation=Config.dynamoEnergyGeneration;
		this.storedEnergy=0;
		
	}
	
	@Override
	public void update() {
		for(EnumFacing side:EnumFacing.values()){
			TileEntity te=worldObj.getTileEntity(pos.offset(side));
			
			if(!(te instanceof IEnergyReceiver)){
				continue;
			}

			IEnergyReceiver rec=(IEnergyReceiver) te;
			
			EnergyUtil.pushEnergy(this, rec, storedEnergy, side.getOpposite(), side);
		}
	}
	
	public void addEnergy(int energy){
		storedEnergy+=energy;
		if(storedEnergy>maxStorage){
			storedEnergy=maxStorage;
		}
		markDirty();
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setInteger("stored_energy", storedEnergy);
		return super.writeToNBT(compound);
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		storedEnergy=compound.getInteger("stored_energy");
		super.readFromNBT(compound);
	}
	
	@Override
	public boolean canConnectEnergy(EnumFacing from) {
		return true;
	}

	@Override
	public int extractEnergy(EnumFacing from, int maxExtract, boolean simulate) {
		int i=Math.min(maxExtract, storedEnergy);
		
		if(!simulate){
			storedEnergy-=i;
			markDirty();
		}
		
		return i;
	}

	@Override
	public int getEnergyStored(EnumFacing from) {
		return storedEnergy;
	}

	@Override
	public int getMaxEnergyStored(EnumFacing from) {
		return maxStorage;
	}
	
}
