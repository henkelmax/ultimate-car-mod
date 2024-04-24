package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.config.Fuel;
import de.maxhenkel.car.entity.car.parts.*;
import de.maxhenkel.car.integration.jei.CarRecipe;
import de.maxhenkel.car.integration.jei.CarRecipeBuilder;
import de.maxhenkel.car.items.ICarPart;
import de.maxhenkel.car.items.ItemKey;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.corelib.client.obj.OBJModelInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import org.joml.Vector3d;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class EntityGenericCar extends EntityCarLicensePlateBase {

    private static final EntityDataAccessor<NonNullList<ItemStack>> PARTS = SynchedEntityData.defineId(EntityGenericCar.class, Main.ITEM_LIST.get());

    private List<Part> parts;
    private float maxUpStep;

    public EntityGenericCar(EntityType type, Level worldIn) {
        super(type, worldIn);
        maxUpStep = super.maxUpStep();
    }

    public EntityGenericCar(Level worldIn) {
        this(Main.CAR_ENTITY_TYPE.get(), worldIn);
    }

    private List<Part> getCarParts() {
        if (parts == null) {
            parts = new ArrayList<>();
        }
        return parts;
    }

    @Override
    public float getWheelRotationAmount() {
        PartWheelBase wheel = getPartByClass(PartWheelBase.class);
        if (wheel == null) {
            return super.getWheelRotationAmount();
        }
        return wheel.getRotationModifier() * getSpeed();
    }

    @Override
    public int getFluidInventorySize() {
        PartTankContainer tank = getPartByClass(PartTankContainer.class);
        if (tank == null) {
            return 0;
        }
        return tank.getFluidAmount();
    }

    @Override
    public float getMaxSpeed() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return 0F;
        }
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return 0F;
        }
        return engine.getMaxSpeed() * chassis.getMaxSpeed();
    }

    @Override
    public float getMaxReverseSpeed() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return 0F;
        }
        return engine.getMaxReverseSpeed();
    }

    @Override
    public float getAcceleration() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return 0F;
        }
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return 0F;
        }
        return engine.getAcceleration() * chassis.getAcceleration();
    }

    @Override
    public float getMaxRotationSpeed() {
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return 5.0F;
        }
        return chassis.getMaxRotationSpeed();
    }

    @Override
    public float getMinRotationSpeed() {
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return 2.0F;
        }
        return chassis.getMinRotationSpeed();
    }

    @Override
    public float getRollResistance() {
        return 0.02F;
    }

    @Override
    public float getOptimalTemperature() {
        return 90F;
    }

    @Override
    public int getMaxFuel() {
        PartTank tank = getPartByClass(PartTank.class);
        if (tank == null) {
            return 0;
        }
        return tank.getSize();
    }

    @Override
    public int getEfficiency(@Nullable Fluid fluid) {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return 0;
        }

        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return 0;
        }

        int fluidEfficiency = 0;

        if (fluid == null) {
            fluidEfficiency = 100;
        } else {
            Fuel fuel = Main.FUEL_CONFIG.getFuels().getOrDefault(fluid, null);
            if (fuel != null) {
                fluidEfficiency = fuel.getEfficiency();
            }
        }

        return (int) Math.ceil(chassis.getFuelEfficiency() * engine.getFuelEfficiency() * (float) fluidEfficiency);
    }

    @Override
    public float getRotationModifier() {
        return 0.5F;
    }

    @Override
    public float getPitch() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine instanceof PartEngineTruck) {
            return 1F + 0.35F * Math.abs(getSpeed()) / getMaxSpeed();
        }
        return Math.abs(getSpeed()) / getMaxSpeed();
    }

    @Override
    public double getPlayerYOffset() {
        return 0.2D;
    }

    @Override
    public Vector3d[] getPlayerOffsets() {
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return new Vector3d[]{new Vector3d(0.55D, 0D, -0.38D), new Vector3d(0.55D, 0D, 0.38D)};
        }
        return chassis.getPlayerOffsets();
    }

    @Override
    public int getPassengerSize() {
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return 0;
        }
        return chassis.getPlayerOffsets().length;
    }

    @Override
    public Vector3d getLicensePlateOffset() {
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return new Vector3d(0F, 0F, 0F);
        }

        PartLicensePlateHolder numberPlate = getPartByClass(PartLicensePlateHolder.class);
        if (numberPlate == null) {
            return new Vector3d(0F, 0F, 0F);
        }
        Vector3d offset = chassis.getNumberPlateOffset();
        Vector3d textOffset = numberPlate.getTextOffset();
        return new Vector3d(offset.x + textOffset.x, -offset.y + textOffset.y, offset.z - textOffset.z);
    }

    @Override
    public boolean doesEnterThirdPerson() {
        return true;
    }

    public SoundEvent getStopSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.ENGINE_STOP.get();
        }
        return engine.getStopSound();
    }

    public SoundEvent getFailSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.ENGINE_FAIL.get();
        }
        return engine.getFailSound();
    }

    public SoundEvent getCrashSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.CAR_CRASH.get();
        }
        return engine.getCrashSound();
    }

    public SoundEvent getStartSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.ENGINE_START.get();
        }
        return engine.getStartSound();
    }

    public SoundEvent getStartingSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.ENGINE_STARTING.get();
        }
        return engine.getStartingSound();
    }

    public SoundEvent getIdleSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.ENGINE_IDLE.get();
        }
        return engine.getIdleSound();
    }

    public SoundEvent getHighSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.ENGINE_HIGH.get();
        }
        return engine.getHighSound();
    }

    public SoundEvent getHornSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.CAR_HORN.get();
        }
        return engine.getHornSound();
    }

    @Override
    protected Component getTypeName() {
        PartBody body = getPartByClass(PartBody.class);
        if (body == null) {
            return super.getTypeName();
        }
        return Component.translatable("car_name." + body.getTranslationKey(), Component.translatable("car_variant." + body.getMaterialTranslationKey()));
    }

    public Component getShortName() {
        PartBody body = getPartByClass(PartBody.class);
        if (body == null) {
            return getTypeName();
        }
        return Component.translatable("car_short_name." + body.getTranslationKey());
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(PARTS, NonNullList.create());
    }

    public <T extends Part> T getPartByClass(Class<T> clazz) {
        for (Part part : getCarParts()) {
            if (clazz.isInstance(part)) {
                return (T) part;
            }
        }

        return null;
    }

    public void setPartSerializer() {
        NonNullList<ItemStack> stacks = NonNullList.withSize(partInventory.getContainerSize(), ItemStack.EMPTY);
        for (int i = 0; i < partInventory.getContainerSize(); i++) {
            stacks.set(i, partInventory.getItem(i));
        }

        entityData.set(PARTS, stacks);
    }

    private boolean updateClientSideItems() {
        NonNullList<ItemStack> stacks = entityData.get(PARTS);
        if (stacks.isEmpty()) {
            return false;
        }
        for (int i = 0; i < stacks.size(); i++) {
            partInventory.setItem(i, stacks.get(i));
        }
        return true;
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);

        if (compound.getAllKeys().stream().allMatch(s -> s.equals("id"))) {
            randomizeParts();
            setItem(0, ItemKey.getKeyForCar(getUUID()));
            setItem(1, ItemKey.getKeyForCar(getUUID()));
            setFuelAmount(100);
            setBatteryLevel(500);
            initTemperature();
        }

        setPartSerializer();
        tryInitPartsAndModel();
    }

    protected void randomizeParts() {
        List<CarRecipe> allRecipes = CarRecipeBuilder.getAllRecipes();
        CarRecipe recipe = allRecipes.get(new Random().nextInt(allRecipes.size()));
        getCarParts().clear();
        partInventory.clearContent();
        List<ItemStack> inputs = recipe.getInputs();
        for (int i = 0; i < inputs.size(); i++) {
            partInventory.setItem(i, inputs.get(i));
        }
    }

    private boolean isInitialized;
    private boolean isSpawned = true;

    public void setIsSpawned(boolean isSpawned) {
        this.isSpawned = isSpawned;
    }

    public boolean isSpawned() {
        return isSpawned;
    }

    @Override
    public void tick() {
        super.tick();

        tryInitPartsAndModel();
    }

    public void tryInitPartsAndModel() {
        if (!isInitialized) {
            if (level().isClientSide) {
                if (!isSpawned || updateClientSideItems()) {
                    initParts();
                    initModel();
                    isInitialized = true;
                }
            } else {
                initParts();
                isInitialized = true;
            }
        }
    }

    public List<Part> getModelParts() {
        return Collections.unmodifiableList(getCarParts());
    }

    public void initParts() {
        getCarParts().clear();

        for (int i = 0; i < partInventory.getContainerSize(); i++) {
            ItemStack stack = partInventory.getItem(i);

            if (!(stack.getItem() instanceof ICarPart)) {
                continue;
            }

            ICarPart itemCarPart = (ICarPart) stack.getItem();

            Part part = itemCarPart.getPart(stack);

            if (part == null) {
                continue;
            }

            getCarParts().add(part);
        }

        checkInitializing();
    }

    private void checkInitializing() {
        PartBody body = getPartByClass(PartBody.class);

        if (body instanceof PartBodyTransporter) {
            PartContainer container = getPartByClass(PartContainer.class);
            if (externalInventory.getContainerSize() <= 0) {
                if (container != null) {
                    externalInventory = new SimpleContainer(54);
                } else {
                    externalInventory = new SimpleContainer(27);
                }
            }
        }

        PartWheelBase partWheels = getPartByClass(PartWheelBase.class);
        if (partWheels != null) {
            setMaxUpStep(partWheels.getStepHeight());
        }
    }

    @Override
    public float maxUpStep() {
        return maxUpStep;
    }

    public void setMaxUpStep(float maxUpStep) {
        this.maxUpStep = maxUpStep;
    }

    @Override
    public double getCarWidth() {
        PartBody body = getPartByClass(PartBody.class);
        if (body != null) {
            return body.getWidth();
        }
        return super.getCarWidth();
    }

    @Override
    public double getCarHeight() {
        PartBody body = getPartByClass(PartBody.class);
        if (body != null) {
            return body.getHeight();
        }
        return super.getCarHeight();
    }

    //---------------CLIENT---------------------------------

    private List<OBJModelInstance<EntityGenericCar>> modelInstances = new ArrayList<>();

    protected void initModel() {
        modelInstances.clear();

        boolean addedWheels = false;
        for (Part part : getCarParts()) {
            if (part instanceof PartModel) {
                if (part instanceof PartWheelBase) {
                    if (!addedWheels) {
                        addedWheels = true;
                    } else {
                        continue;
                    }
                }
                modelInstances.addAll(((PartModel) part).getInstances(this));
            }
        }
    }

    public List<OBJModelInstance<EntityGenericCar>> getModels() {
        return modelInstances;
    }

}
