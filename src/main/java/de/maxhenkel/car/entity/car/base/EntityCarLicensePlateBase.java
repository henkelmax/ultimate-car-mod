package de.maxhenkel.car.entity.car.base;

import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.joml.Vector3d;
import de.maxhenkel.car.entity.car.parts.PartLicensePlateHolder;
import de.maxhenkel.car.items.ItemLicensePlate;
import de.maxhenkel.corelib.item.ItemUtils;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public abstract class EntityCarLicensePlateBase extends EntityCarLockBase {

    private static final EntityDataAccessor<String> LICENSE_PLATE = SynchedEntityData.defineId(EntityCarLicensePlateBase.class, EntityDataSerializers.STRING);

    public EntityCarLicensePlateBase(EntityType type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(LICENSE_PLATE, "");
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (player.isShiftKeyDown() && !isLocked()) {
            if (hasLicensePlateHolder()) {
                ItemStack stack = player.getItemInHand(hand);
                if (stack.getItem() instanceof ItemLicensePlate) {
                    String text = ItemLicensePlate.getText(stack);
                    if (!text.isEmpty()) {
                        ItemUtils.decrItemStack(stack, player);
                        player.setItemInHand(hand, stack);
                        setLicensePlate(text);
                        return InteractionResult.CONSUME;
                    }
                }
            }
        }

        return super.interact(player, hand);
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
        return this.entityData.get(LICENSE_PLATE);
    }

    public void setLicensePlate(String plate) {
        entityData.set(LICENSE_PLATE, plate);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput valueOutput) {
        super.addAdditionalSaveData(valueOutput);
        valueOutput.putString("license_plate", getLicensePlate());
    }

    @Override
    public void readAdditionalSaveData(ValueInput valueInput) {
        super.readAdditionalSaveData(valueInput);
        setLicensePlate(valueInput.getStringOr("license_plate", ""));
    }

    public abstract Vector3d getLicensePlateOffset();

}
