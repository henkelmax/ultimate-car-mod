package de.maxhenkel.tools;

import javax.annotation.Nullable;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemStackSelector implements Selector<ItemStack>{

	private Item item;
	private int meta;
	
	public ItemStackSelector(Item item, int meta) {
		this.item=item;
		this.meta=meta;
	}
	
	public ItemStackSelector(Item item) {
		this.item=item;
		this.meta=-1;
	}
	
	@Nullable
	public static ItemStackSelector fromString(String str){
		String[] split = str.split(":");
		if (split.length < 2) {
			return null;
		}

		Item item = Item.REGISTRY.getObject(new ResourceLocation(split[0], split[1]));
		if (item == null) {//Evtl air
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
		
		return new ItemStackSelector(item, meta);
	}

	public Item getItem() {
		return item;
	}

	public int getMeta() {
		return meta;
	}
	
	public String toString() {
		if(item==null){//Evtl air
			return "";
		}
		
		ResourceLocation loc=item.getRegistryName();
		
		String str=loc.getResourceDomain() +":" +loc.getResourcePath();
		
		if(meta>=0){
			str=str+":" +meta;
		}
		
		return  str;
	}

	@Override
	public boolean isValid(ItemStack stack) {
		if(item==null||stack==null||stack.getItem()==null){
			return false;
		}
		
		if(!item.equals(stack.getItem())){
			return false;
		}
		
		if(meta<0){
			return true;
		}
		
		if(meta!=stack.getMetadata()){
			return false;
		}
		
		return true;
	}
	
}
