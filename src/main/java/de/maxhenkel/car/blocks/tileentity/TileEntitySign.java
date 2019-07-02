package de.maxhenkel.car.blocks.tileentity;


import de.maxhenkel.car.Main;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IIntArray;
import net.minecraft.util.IntArray;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TileEntitySign extends TileEntityBase{

	private String[] text=new String[8];
	
	public TileEntitySign() {
		super(Main.SIGN_TILE_ENTITY_TYPE);
		for(int i=0; i<text.length; i++) {
			text[i]="";
		}
	}
	
	@Override
	public CompoundNBT write(CompoundNBT compound) {
		for(int i=0; i<text.length; i++) {
			compound.putString("text" +i, text[i]);
		}
		return super.write(compound);
	}
	
	@Override
	public void read(CompoundNBT compound) {
		for(int i=0; i<text.length; i++) {
			this.text[i]=compound.getString("text" +i);
		}
		super.read(compound);
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

	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent("block.car.sign");
	}

	@Override
	public IIntArray getFields() {
		return new IntArray(0);
	}
}
