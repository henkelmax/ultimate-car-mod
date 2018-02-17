package de.maxhenkel.car.entity.car;

import de.maxhenkel.car.entity.car.base.EntityCarLockBase;
import de.maxhenkel.car.entity.car.base.EntityCarNumberPlateBase;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.car.reciepe.CarBuilderTransporter;
import de.maxhenkel.car.reciepe.ICarbuilder;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class EntityCarTransporter extends EntityCarNumberPlateBase {
	
	private static final DataParameter<Boolean> HAS_CONTAINER = EntityDataManager.<Boolean>createKey(EntityCarTransporter.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Integer> TYPE = EntityDataManager.<Integer>createKey(EntityCarTransporter.class,
			DataSerializers.VARINT);
	
	
	public EntityCarTransporter(World worldIn) {
		this(worldIn, false, EnumDyeColor.WHITE);
	}
	
	public EntityCarTransporter(World worldIn, boolean hasContainer, EnumDyeColor color) {
		super(worldIn);
		setSize(2.0F, 1.51F);
		maxFuel=2000;
		//maxSpeed=0.4F;
		setHasContainer(hasContainer);
		setType(color);
		
		if(hasContainer){
			this.externalInventory=new InventoryBasic(getCarName().getFormattedText(), false, 54);
		}else{
			this.externalInventory=new InventoryBasic(getCarName().getFormattedText(), false, 27);
		}
		
	}
	
	@Override
	public float getRotationModifier() {
		return 0.25F;
	}

	@Override
	public ITextComponent getCarName() {
		return new TextComponentTranslation("entity.car_transporter.name");
	}

	@Override
	public float getFrontOffsetForPassenger(int i, Entity passenger) {
		return 0.55F;
	}
	
	@Override
	public float getsideOffsetForPassenger(int i, Entity passenger) {
		if(i<=0){
			return -0.38F;
		}
		return 0.38F;
	}
	
	@Override
	public int getPassengerSize() {
		return 2;
	}
	
	@Override
	public double getMountedYOffset() {
		return -0.5D;
	}
	
	@Override
	protected void entityInit() {
		super.entityInit();
		this.dataManager.register(HAS_CONTAINER, false);
		this.dataManager.register(TYPE, EnumDyeColor.WHITE.getMetadata());
	}

	private void setHasContainer(boolean has) {
		this.dataManager.set(HAS_CONTAINER, has);
	}

	public boolean getHasContainer() {
		return this.dataManager.get(HAS_CONTAINER);
	}
	
	public void setType(EnumDyeColor color) {
		this.dataManager.set(TYPE, color.getMetadata());
	}

	public EnumDyeColor getType() {
		return EnumDyeColor.byMetadata(this.dataManager.get(TYPE));
	}

	public void addContainer(){
	    if(getHasContainer()){
	        return;
        }

        IInventory inv=externalInventory;
        externalInventory=new InventoryBasic(getCarName().getFormattedText(), false, 54);
        for(int i=0; i<inv.getSizeInventory(); i++){
            externalInventory.setInventorySlotContents(i, inv.getStackInSlot(i));
            inv.setInventorySlotContents(i, ItemTools.EMPTY);
        }
	    setHasContainer(true);
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
	    if(player.isSneaking()&&!isLocked()){
            ItemStack stack=player.getHeldItem(hand);
            if(stack.getItem().equals(ModItems.CONTAINER)){
                ItemTools.decrItemStack(stack, player);
                player.setHeldItem(hand, stack);
                addContainer();
                return true;
            }
        }

        return super.processInitialInteract(player, hand);
    }

    @Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("has_container", getHasContainer());
		compound.setInteger("type", getType().getMetadata());
		super.writeEntityToNBT(compound);
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		setHasContainer(compound.getBoolean("has_container"));
		
		if(getHasContainer()){
			this.externalInventory=new InventoryBasic(getCarName().getFormattedText(), false, 54);
		}else{
			this.externalInventory=new InventoryBasic(getCarName().getFormattedText(), false, 27);
		}
		
		setType(EnumDyeColor.byMetadata(compound.getInteger("type")));
		super.readEntityFromNBT(compound);
	}

	@Override
	public ICarbuilder getBuilder() {
		return new CarBuilderTransporter(getHasContainer(), getType());
	}

	@Override
	public String getID() {
		return "car_transporter";
	}
	
}
