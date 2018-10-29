package de.maxhenkel.car.blocks.tileentity;

import de.maxhenkel.car.Config;
import de.maxhenkel.tools.EnergyUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.energy.IEnergyStorage;

public class TileEntityDynamo extends TileEntityBase implements IEnergyStorage, ITickable{

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
			IEnergyStorage storage=EnergyUtil.getEnergyStorageOffset(world, pos, side);

			if(storage==null){
				continue;
			}
			
			EnergyUtil.pushEnergy(this, storage, storedEnergy, side.getOpposite(), side);
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
    public int receiveEnergy(int maxReceive, boolean simulate) {
        return 0;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int i=Math.min(maxExtract, storedEnergy);

        if(!simulate){
            storedEnergy-=i;
            markDirty();
        }

        return i;
    }

    @Override
    public int getEnergyStored() {
        return storedEnergy;
    }

    @Override
    public int getMaxEnergyStored() {
        return maxStorage;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return false;
    }
}
