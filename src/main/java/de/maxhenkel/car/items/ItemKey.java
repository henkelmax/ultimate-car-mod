package de.maxhenkel.car.items;

import java.util.List;
import java.util.UUID;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.PredicateUUID;
import de.maxhenkel.car.entity.car.base.EntityCarLockBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class ItemKey extends Item {

	public ItemKey() {
		setMaxStackSize(1);
		setUnlocalizedName("key");
		setRegistryName("key");
		setCreativeTab(ModCreativeTabs.TAB_CAR);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(ItemStack itemStackIn, World worldIn, EntityPlayer playerIn,
			EnumHand hand) {
		
		UUID carUUID=getCar(itemStackIn);
		
		if(carUUID==null){
			if(worldIn.isRemote){
				playerIn.addChatMessage(new TextComponentTranslation("message.key_no_car"));
			}
			return new ActionResult<ItemStack>(EnumActionResult.PASS, itemStackIn);
		}else if(worldIn.isRemote){
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		}
		
		List<EntityCarLockBase> cars=worldIn.getEntitiesWithinAABB(EntityCarLockBase.class, playerIn.getEntityBoundingBox().expand(25, 25, 25), new PredicateUUID(carUUID));
		
		if(cars.isEmpty()){
			playerIn.addChatMessage(new TextComponentTranslation("message.car_out_of_range"));
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		}
		
		EntityCarLockBase car=cars.get(0);
		
		if(car==null){
			playerIn.addChatMessage(new TextComponentTranslation("message.car_out_of_range"));
			return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
		}
		
		car.setLocked(!car.isLocked(), true);
		
		return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemStackIn);
	}
	
	public static void setCar(ItemStack stack, UUID carUUID){
		if(!stack.hasTagCompound()){
			stack.setTagCompound(new NBTTagCompound());
		}
		
		NBTTagCompound comp=stack.getTagCompound();
		
		comp.setUniqueId("car", carUUID);
	}
	
	public static UUID getCar(ItemStack stack){
		if(stack==null){
			return null;
		}
		
		if(!stack.hasTagCompound()){
			return null;
		}
		
		NBTTagCompound comp=stack.getTagCompound();
		
		if(comp==null){
			return null;
		}

		UUID uuid=comp.getUniqueId("car");
		
		if(uuid==null){
			return null;
		}
		
		return uuid;
	}
	
	public static ItemStack getKeyForCar(UUID car){
		ItemStack stack=new ItemStack(ModItems.KEY);
		
		setCar(stack, car);
		
		return stack;
	}

}
