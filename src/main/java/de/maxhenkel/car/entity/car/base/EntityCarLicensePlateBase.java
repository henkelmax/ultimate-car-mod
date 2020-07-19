package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.entity.car.parts.PartLicensePlateHolder;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

public abstract class EntityCarLicensePlateBase extends EntityCarLockBase {

    private static final DataParameter<String> LICENSE_PLATE = EntityDataManager.createKey(EntityCarLicensePlateBase.class, DataSerializers.STRING);

    public EntityCarLicensePlateBase(EntityType type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LICENSE_PLATE, "");
    }

    @Override
    public ActionResultType processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isSneaking() && !isLocked()) {
            if (hasLicensePlateHolder()) {
                ItemStack stack = player.getHeldItem(hand);
                if (stack.getItem() instanceof ItemLicensePlate) {
                    String text = ItemLicensePlate.getText(stack);
                    if (!text.isEmpty()) {
                        ItemUtils.decrItemStack(stack, player);
                        player.setHeldItem(hand, stack);
                        setLicensePlate(text);
                        return ActionResultType.CONSUME;
                    }
                }
            }
        }

        return super.processInitialInteract(player, hand);
    }

    public boolean hasLicensePlateHolder() {
        if (!(this instanceof EntityGenericCar)) {
            return false;
        }

        EntityGenericCar car = (EntityGenericCar) this;

        if (car.getPartByClass(PartLicensePlateHolder.class) != null) {
            return true;
        }
        return false;
    }

    public String getLicensePlate() {
        return this.dataManager.get(LICENSE_PLATE);
    }

    public void setLicensePlate(String plate) {
        dataManager.set(LICENSE_PLATE, plate);
    }

    @Override
    protected void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putString("license_plate", getLicensePlate());
    }

    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        setLicensePlate(compound.getString("license_plate"));
    }

    public abstract Vector3d getLicensePlateOffset();

}
