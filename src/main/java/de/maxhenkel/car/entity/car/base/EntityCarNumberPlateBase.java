package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.items.ItemNumberPlate;
import de.maxhenkel.car.items.ModItems;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityCarNumberPlateBase extends EntityCarLockBase {

    private static final DataParameter<String> NUMBER_PLATE = EntityDataManager
            .<String>createKey(EntityCarNumberPlateBase.class, DataSerializers.STRING);

    public EntityCarNumberPlateBase(World worldIn) {
        super(worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(NUMBER_PLATE, "");
    }

    @Override
    public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
        if(player.isSneaking()&&!isLocked()){
            ItemStack stack=player.getHeldItem(hand);
            if(stack.getItem().equals(ModItems.NUMBER_PLATE)){
                String text=ItemNumberPlate.getText(stack);
                if(text!=null&&!text.isEmpty()){
                    ItemTools.decrItemStack(stack, player);
                    player.setHeldItem(hand, stack);
                    setNumberPlate(text);
                    return true;
                }
            }
        }

        return super.processInitialInteract(player, hand);
    }

    public String getNumberPlate() {
        return this.dataManager.get(NUMBER_PLATE);
    }

    public void setNumberPlate(String plate) {
        dataManager.set(NUMBER_PLATE, plate);
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setString("number_plate", getNumberPlate());
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        setNumberPlate(compound.getString("number_plate"));
    }

    public abstract Vec3d getNumberPlateOffset();
}
