package de.maxhenkel.car.entity.car.base;

import de.maxhenkel.car.DataSerializerStringList;
import de.maxhenkel.car.entity.car.parts.Part;
import de.maxhenkel.car.entity.car.parts.PartRegistry;
import de.maxhenkel.car.entity.model.obj.OBJModelInstance;
import de.maxhenkel.car.sounds.ModSounds;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
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
        return 0.5F;
    }

    @Override
    public float getMaxReverseSpeed() {
        return 0.2F;
    }

    @Override
    public float getAcceleration() {
        return 0.032F;
    }

    @Override
    public float getMaxRotationSpeed() {
        return 5F;
    }

    @Override
    public float getMinRotationSpeed() {
        return 2.0F;
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
    public boolean doesEnterThirdPerson() {
        return true;
    }

    @Override
    public ITextComponent getCarName() {
        return new TextComponentTranslation("entity.car.name");
    }

    @Override
    public int getPassengerSize() {
        return 1;
    }

    @Override
    public float getNumberPlateOffsetX() {
        return 0F;
    }

    @Override
    public float getNumberPlateOffsetY() {
        return -0.45F;
    }

    @Override
    public float getNumberPlateOffsetZ() {
        return -0.94F;
    }

    public SoundEvent getStopSound() {
        return ModSounds.engine_stop;
    }

    public SoundEvent getFailSound() {
        return ModSounds.engine_fail;
    }

    public SoundEvent getCrashSound() {
        return ModSounds.car_crash;
    }

    public SoundEvent getStartSound() {
        return ModSounds.engine_start;
    }

    public SoundEvent getStartingSound() {
        return ModSounds.engine_starting;
    }

    public SoundEvent getIdleSound() {
        return ModSounds.engine_idle;
    }

    public SoundEvent getHighSound() {
        return ModSounds.engine_high;
    }

    public SoundEvent getHornSound() {
        return ModSounds.car_horn;
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

    public void tryInitModel(){
        if(!isInitialized){
            initParts();

            if (world.isRemote) {
                initModel();
            }
            isInitialized=true;
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
        String[] partData=new String[list.tagCount()];
        for (int i = 0; i < list.tagCount(); i++) {
            partData[i]=list.getStringTagAt(i);
        }

        setPartStrings(partData);
    }

    @Nullable
    public List<Part> getModelParts() {
        return Collections.unmodifiableList(parts);
    }

    protected void initParts() {
        for (String s : getPartStrings()) {
            Part part = PartRegistry.getPart(s);
            if (part != null) {
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

        for (Part part : parts) {
            modelInstances.addAll(part.getInstances(this));
        }
    }

    public List<OBJModelInstance> getModels() {
        return modelInstances;
    }
}
