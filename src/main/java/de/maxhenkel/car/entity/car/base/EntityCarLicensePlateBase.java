package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.entity.car.parts.PartLicensePlateHolder;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityCarLicensePlateBase extends EntityCarLockBase {

    private static final DataParameter<String> LICENSE_PLATE = EntityDataManager
            .<String>createKey(EntityCarLicensePlateBase.class, DataSerializers.STRING);

    public EntityCarLicensePlateBase(EntityType type, World worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(LICENSE_PLATE, "");
    }

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        if (player.isShiftKeyDown() && !isLocked()) {
            if (hasLicensePlateHolder()) {
                ItemStack stack = player.getHeldItem(hand);
                if (stack.getItem() instanceof ItemLicensePlate) {
                    String text = ItemLicensePlate.getText(stack);
                    if (text != null && !text.isEmpty()) {
                        ItemTools.decrItemStack(stack, player);
                        player.setHeldItem(hand, stack);
                        setLicensePlate(text);
                        return true;
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

    public abstract Vec3d getLicensePlateOffset();
}
