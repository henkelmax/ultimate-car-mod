package de.maxhenkel.car.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySign extends TileEntityBase{

	private String[] text=new String[4];
	
	public TileEntitySign() {
		for(int i=0; i<text.length; i++) {
			text[i]="";
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		compound.setString("textFront", text[0]);
		compound.setString("textFrontSmall", text[1]);
		compound.setString("textBack", text[2]);
		compound.setString("textBackSmall", text[3]);
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		
		this.text[0]=compound.getString("textFront");
		this.text[1]=compound.getString("textFrontSmall");
		this.text[2]=compound.getString("textBack");
		this.text[3]=compound.getString("textBackSmall");
	}

	public String getText(int i) {
		if(i<0||i>3) {
			return "";
		}
		return text[i];
	}

	public void setText(int i, String s) {
		if(i<0||i>3||s==null) {
			return;
		}
		text[i]=s;
		markDirty();
	}
	
	public void setText(String[] s) {
		if(s==null||s.length!=4) {
			return;
		}
		text=s;
		markDirty();
		synchronize();
	}
}
