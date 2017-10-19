package de.maxhenkel.car.blocks.tileentity;

import net.minecraft.nbt.NBTTagCompound;

public class TileEntitySign extends TileEntityBase{

	private String[] text=new String[8];
	
	public TileEntitySign() {
		for(int i=0; i<text.length; i++) {
			text[i]="";
		}
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		for(int i=0; i<text.length; i++) {
			compound.setString("text" +i, text[i]);
		}
		return super.writeToNBT(compound);
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		for(int i=0; i<text.length; i++) {
			this.text[i]=compound.getString("text" +i);
		}
	}

	public String getText(int i) {
		if(i<0||i>=text.length) {
			return "";
		}
		return text[i];
	}
	
	public String[] getText() {
		return text.clone();
	}

	public void setText(int i, String s) {
		if(i<0||i>=text.length||s==null) {
			return;
		}
		text[i]=s;
		markDirty();
		synchronize();
	}
	
	public void setText(String[] s) {
		if(s==null||s.length!=text.length) {
			return;
		}
		text=s;
		markDirty();
		synchronize();
	}
}
