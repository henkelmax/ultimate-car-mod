package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.DataSerializerItemList;
import de.maxhenkel.car.entity.car.parts.*;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.items.ICarPart;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.tools.ItemTools;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EntityGenericCar extends EntityCarLicensePlateBase {

    private IInventory partInventory;

    public EntityGenericCar(World worldIn) {
        super(worldIn);
        this.partInventory = new InventoryBasic(getCarName().getUnformattedText(), false, 15);
    }

    @Override
    public void destroyCar(EntityPlayer player, boolean dropParts) {
        super.destroyCar(player, dropParts);
        InventoryHelper.dropInventoryItems(world, this, partInventory);
    }

    @Override
    public float getMaxSpeed() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return 0;
        }
        return engine.getMaxSpeed();
    }

    @Override
    public float getMaxReverseSpeed() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return 0;
        }
        return engine.getMaxReverseSpeed();
    }

    @Override
    public float getAcceleration() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return 0;
        }
        return engine.getAcceleration();
    }

    @Override
    public float getMaxRotationSpeed() {
        return 5F;
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
        return 1000;
    }

    @Override
    public double getEfficiency(@Nullable Fluid fluid) {
        return 1.0;
    }

    @Override
    public float getRotationModifier() {
        return 0.5F;
    }

    @Override
    public double getPlayerYOffset() {
        return 0.2D;
    }

    @Override
    public Vec3d[] getPlayerOffsets() {
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return new Vec3d[]{new Vec3d(0.55D, 0D, -0.38D), new Vec3d(0.55D, 0D, 0.38D)};
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
    public Vec3d getLicensePlateOffset() {
        PartBody chassis = getPartByClass(PartBody.class);
        if (chassis == null) {
            return new Vec3d(0F, 0F, 0F);
        }

        PartLicensePlateHolder numberPlate = getPartByClass(PartLicensePlateHolder.class);
        if (numberPlate == null) {
            return new Vec3d(0F, 0F, 0F);
        }
        Vec3d offset = chassis.getNumberPlateOffset();
        Vec3d textOffset = numberPlate.getTextOffset();
        return new Vec3d(offset.x + textOffset.x, -offset.y + textOffset.y, -offset.z + textOffset.z);
    }

    @Override
    public boolean doesEnterThirdPerson() {
        return true;
    }

    @Override
    public ITextComponent getCarName() {
        return new TextComponentTranslation("entity.car.name");
    }

    public SoundEvent getStopSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.engine_stop;
        }
        return engine.getStopSound();
    }

    public SoundEvent getFailSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.engine_fail;
        }
        return engine.getFailSound();
    }

    public SoundEvent getCrashSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.car_crash;
        }
        return engine.getCrashSound();
    }

    public SoundEvent getStartSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.engine_start;
        }
        return engine.getStartSound();
    }

    public SoundEvent getStartingSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.engine_starting;
        }
        return engine.getStartingSound();
    }

    public SoundEvent getIdleSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.engine_idle;
        }
        return engine.getIdleSound();
    }

    public SoundEvent getHighSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.engine_high;
        }
        return engine.getHighSound();
    }

    public SoundEvent getHornSound() {
        PartEngine engine = getPartByClass(PartEngine.class);
        if (engine == null) {
            return ModSounds.car_horn;
        }
        return engine.getHornSound();
    }

    private static final DataParameter<ItemStack[]> PARTS = EntityDataManager.createKey(EntityGenericCar.class, DataSerializerItemList.ITEM_LIST);

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(PARTS, null);
    }

    private List<Part> parts = new ArrayList<>();

    public <T extends Part> T getPartByClass(Class<T> clazz) {
        for (Part part : parts) {
            if (clazz.isInstance(part)) {
                return (T) part;
            }
        }

        return null;
    }

    public void setPartSerializer() {
        ItemStack[] stacks = new ItemStack[partInventory.getSizeInventory()];
        for (int i = 0; i < partInventory.getSizeInventory(); i++) {
            stacks[i] = partInventory.getStackInSlot(i);
        }

        dataManager.set(PARTS, stacks);
    }

    private boolean updateClientSideItems() {
        ItemStack[] stacks = dataManager.get(PARTS);
        if (stacks == null) {
            return false;
        }
        for (int i = 0; i < stacks.length; i++) {
            partInventory.setInventorySlotContents(i, stacks[i]);
        }
        return true;
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        ItemTools.readInventory(compound, "parts", partInventory);

        setPartSerializer();

        tryInitPartsAndModel();
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        ItemTools.saveInventory(compound, "parts", partInventory);
    }

    private boolean isInitialized;
    private boolean isSpawned = true;

    public void setIsSpawned(boolean isSpawned) {
        this.isSpawned = isSpawned;
    }

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        tryInitPartsAndModel();
    }

    public void tryInitPartsAndModel() {
        if (!isInitialized) {
            if (world.isRemote) {
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
        return Collections.unmodifiableList(parts);
    }

    public IInventory getPartInventory() {
        return partInventory;
    }

    public void initParts() {
        parts.clear();

        for (int i = 0; i < partInventory.getSizeInventory(); i++) {
            ItemStack stack = partInventory.getStackInSlot(i);

            if (!(stack.getItem() instanceof ICarPart)) {
                continue;
            }

            ICarPart itemCarPart = (ICarPart) stack.getItem();

            Part part = itemCarPart.getPart(stack);

            if (part == null) {
                continue;
            }

            checkBoundingBox(part);

            parts.add(part);
        }
    }

    private void checkBoundingBox(Part part) {
        if (part instanceof PartBody) {
            PartBody chassis = (PartBody) part;
            setSize(chassis.getWidth(), chassis.getHeight());
        }
    }

    //---------------CLIENT---------------------------------

    private List<OBJModelInstance> modelInstances = new ArrayList<>();

    protected void initModel() {
        modelInstances.clear();

        boolean addedWheels = false;
        for (Part part : parts) {
            if (part instanceof PartModel) {
                if (part instanceof PartWheel) {
                    //TODO fix wheels (fixed!?)
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

    public List<OBJModelInstance> getModels() {
        return modelInstances;
    }
}
