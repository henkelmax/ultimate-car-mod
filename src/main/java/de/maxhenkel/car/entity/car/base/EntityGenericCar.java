package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.DataSerializerStringList;
import de.maxhenkel.car.entity.car.parts.*;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
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

public class EntityGenericCar extends EntityCarNumberPlateBase {

    public EntityGenericCar(World worldIn) {
        super(worldIn);
    }

    @Override
    public float getMaxSpeed() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return 0;
        }
        return engine.getMaxSpeed();
    }

    @Override
    public float getMaxReverseSpeed() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return 0;
        }
        return engine.getMaxReverseSpeed();
    }

    @Override
    public float getAcceleration() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
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
        PartChassis chassis=getPartByClass(PartChassis.class);
        if(chassis==null){
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
        PartChassis chassis=getPartByClass(PartChassis.class);
        if(chassis==null){
            return new Vec3d[]{new Vec3d(0.55D, 0D, -0.38D), new Vec3d(0.55D, 0D, 0.38D)};
        }
        return chassis.getPlayerOffsets();
    }

    @Override
    public int getPassengerSize() {
        PartChassis chassis=getPartByClass(PartChassis.class);
        if(chassis==null){
            return 0;
        }
        return chassis.getPlayerOffsets().length;
    }

        //new Vec3d(0F, -0.45F, -0.94F)
    @Override
    public Vec3d getNumberPlateOffset() {
        PartChassis chassis=getPartByClass(PartChassis.class);
        if(chassis==null){
            return new Vec3d(0F, 0F, 0F);
        }

        PartNumberPlate numberPlate=getPartByClass(PartNumberPlate.class);
        if(numberPlate==null){
            return new Vec3d(0F, 0F, 0F);
        }
        Vec3d offset=chassis.getNumberPlateOffset();
        Vec3d textOffset=numberPlate.getTextOffset();
        return new Vec3d(offset.x+textOffset.x, -offset.y+textOffset.y, -offset.z+textOffset.z);
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
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return ModSounds.engine_stop;
        }
        return engine.getStopSound();
    }

    public SoundEvent getFailSound() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return ModSounds.engine_fail;
        }
        return engine.getFailSound();
    }

    public SoundEvent getCrashSound() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return ModSounds.car_crash;
        }
        return engine.getCrashSound();
    }

    public SoundEvent getStartSound() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return ModSounds.engine_start;
        }
        return engine.getStartSound();
    }

    public SoundEvent getStartingSound() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return ModSounds.engine_starting;
        }
        return engine.getStartingSound();
    }

    public SoundEvent getIdleSound() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return ModSounds.engine_idle;
        }
        return engine.getIdleSound();
    }

    public SoundEvent getHighSound() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return ModSounds.engine_high;
        }
        return engine.getHighSound();
    }

    public SoundEvent getHornSound() {
        PartEngine engine=getPartByClass(PartEngine.class);
        if(engine==null){
            return ModSounds.car_horn;
        }
        return engine.getHornSound();
    }

    private static final DataParameter<String[]> PARTS = EntityDataManager.<String[]>createKey(EntityGenericCar.class, DataSerializerStringList.STRING_LIST);

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(PARTS, new String[0]);
    }

    public void setPartStrings(String[] parts) {
        this.dataManager.set(PARTS, parts);
    }

    public String[] getPartStrings() {
        return this.dataManager.get(PARTS);
    }

    private List<Part> parts = new ArrayList<>();

    public <T extends Part> T getPartByClass(Class<T> clazz) {
        for (Part part : parts){
            if(clazz.isInstance(part)){
                return (T) part;
            }
        }

        return null;
    }

    //  /summon car:car ~ ~ ~ {parts:["oak_chassis", "wheel"]}
    @Override
    protected void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);

        readPartsFromNBT(compound);
    }

    private boolean isInitialized;

    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();

        tryInitModel();
    }

    public void tryInitModel() {
        if (!isInitialized) {
            initParts();

            if (world.isRemote) {
                initModel();
            }
            isInitialized = true;
        }
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);

        writePartsToNBT(compound);
    }

    public void writePartsToNBT(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();
        for (String s : getPartStrings()) {
            list.appendTag(new NBTTagString(s));
        }

        compound.setTag("parts", list);
    }

    public void readPartsFromNBT(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList("parts", 8);
        String[] partData = new String[list.tagCount()];
        for (int i = 0; i < list.tagCount(); i++) {
            partData[i] = list.getStringTagAt(i);
        }

        setPartStrings(partData);
    }

    @Nullable
    public List<Part> getModelParts() {
        return Collections.unmodifiableList(parts);
    }

    public void initParts() {
        for (String s : getPartStrings()) {
            Part part = PartRegistry.getPart(s);
            if (part != null) {

                if(part instanceof PartChassis){
                    PartChassis chassis= (PartChassis) part;
                    setSize(chassis.getWidth(), chassis.getHeight());
                }

                parts.add(part);
            } else {
                System.err.println("Failed to load car part '" + s + "'");
            }
        }
    }

    //---------------CLIENT---------------------------------

    private List<OBJModelInstance> modelInstances = new ArrayList<>();

    protected void initModel() {
        modelInstances.clear();

        boolean addedWheels = false;
        for (Part part : parts) {
            if (part instanceof PartModel) {
                if (part instanceof PartWheels) {
                    //TODO fix wheels
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
