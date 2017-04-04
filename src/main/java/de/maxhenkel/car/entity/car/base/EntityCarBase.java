package de.maxhenkel.car.entity.car.base;

import java.util.List;
import de.maxhenkel.car.Config;
import de.maxhenkel.car.MathTools;
import de.maxhenkel.car.net.MessageCarGui;
import de.maxhenkel.car.net.MessageControlCar;
import de.maxhenkel.car.net.MessageCrash;
import de.maxhenkel.car.net.MessageStartCar;
import de.maxhenkel.car.proxy.CommonProxy;
import de.maxhenkel.car.reciepe.CarCraftingManager;
import de.maxhenkel.car.reciepe.ICarRecipe;
import de.maxhenkel.car.reciepe.ICarbuilder;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopHigh;
import de.maxhenkel.car.sounds.SoundLoopIdle;
import de.maxhenkel.car.sounds.SoundLoopStart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityCarBase extends EntityVehicleBase {

	protected float maxSpeed = 0.5F;
	protected float maxReverseSpeed = 0.2F;
	protected float acceleration = 0.032F;
	protected float maxRotationSpeed = 5F;
	protected float rollResistance = 0.02F;
	protected float minRotationSpeed=2.0F;

	private float wheelRotation;

	@SideOnly(Side.CLIENT)
	private SoundLoopIdle idleLoop;
	@SideOnly(Side.CLIENT)
	private SoundLoopHigh highLoop;
	@SideOnly(Side.CLIENT)
	private SoundLoopStart startLoop;

	private static final DataParameter<Float> SPEED = EntityDataManager.<Float>createKey(EntityCarBase.class,
			DataSerializers.FLOAT);
	private static final DataParameter<Boolean> STARTED = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
			DataSerializers.BOOLEAN);

	private static final DataParameter<Boolean> FORWARD = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> BACKWARD = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> LEFT = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
			DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> RIGHT = EntityDataManager.<Boolean>createKey(EntityCarBase.class,
			DataSerializers.BOOLEAN);

	public EntityCarBase(World worldIn) {
		super(worldIn);
		this.setSize(1.3F, 1.6F);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		if (isStarted() && !canEngineStayOn()) {
			setStarted(false);
		}

		this.updateGravity();
		this.controlCar();
		this.checkPush();
		this.moveEntity(this.motionX, this.motionY, this.motionZ);

		if (worldObj.isRemote) {
			updateSounds();
		}

	}

	public void checkPush() {
		List<EntityPlayer> list = this.worldObj.getEntitiesWithinAABB(EntityPlayer.class,
				getCollisionBoundingBox().expand(0.2, 0, 0.2),
				EntitySelectors.<EntityPlayer>getTeamCollisionPredicate(this));

		for (int j = 0; j < list.size(); j++) {
			EntityPlayer player = list.get(j);
			if (!player.isPassenger(this) && player.isSneaking()) {
				double motX = calculateMotionX(0.05F, player.rotationYaw);
				double motZ = calculateMotionZ(0.05F, player.rotationYaw);
				moveEntity(motX, 0, motZ);
				return;
			}
		}
	}

	public boolean canEngineStayOn() {
		if (isInWater() || isInLava()) {
			return false;
		}

		return true;
	}

	public void updateSounds() {
		if (getSpeed() == 0 && isStarted()) {
			checkIdleLoop();
		}
		if (getSpeed() != 0 && isStarted()) {
			checkHighLoop();
		}
	}

	public void destroyCar(boolean dropParts) {
		if (dropParts) {
			ICarbuilder builder=getBuilder();
			ICarRecipe reciepe = CarCraftingManager.getInstance().getReciepeByName(builder.getName());
			if (reciepe != null) {
				for (ItemStack stack : reciepe.getInputs()) {
					if (shouldDropItemWithChance(stack)) {
						InventoryHelper.spawnItemStack(worldObj, posX, posY, posZ, stack);
					}
				}
			}
		}
		setDead();
	}

	public boolean shouldDropItemWithChance(ItemStack stack) {
		return rand.nextInt(10) != 0;
	}

	public abstract ICarbuilder getBuilder();

	private void controlCar() {

		if (!isBeingRidden()) {
			setForward(false);
			setBackward(false);
			setLeft(false);
			setRight(false);
		}

		float speed = MathTools.subtractToZero(getSpeed(), rollResistance);

		if (isForward()) {
			speed = Math.min(speed + acceleration, maxSpeed);
		}
		
		if (isBackward()) {
			speed = Math.max(speed - acceleration, -maxReverseSpeed);
		}

		setSpeed(speed);
		
		
		float rotationSpeed = 0;
		if (Math.abs(speed) > 0.02F) {
			rotationSpeed = MathHelper.abs(getRotationModifier() / (float)Math.pow(speed, 2));
			
			rotationSpeed = MathHelper.clamp_float(rotationSpeed, minRotationSpeed, maxRotationSpeed);
		}

		deltaRotation = 0;

		if(speed<0){
			rotationSpeed=-rotationSpeed;
		}
		
		if (isLeft()) {
			deltaRotation -= rotationSpeed;
		}
		if (isRight()) {
			deltaRotation += rotationSpeed;
		}

		this.rotationYaw += this.deltaRotation;

		if (rotationYaw > 180) {
			rotationYaw -= 360;
			prevRotationYaw = rotationYaw;
		} else if (rotationYaw <= -180) {
			rotationYaw += 360;
			prevRotationYaw = rotationYaw;
		}

		if (isCollidedHorizontally) {
			if (worldObj.isRemote) {
				onCollision(speed);
			}
		} else {
			this.motionX = calculateMotionX(getSpeed(), rotationYaw);
			this.motionZ = calculateMotionZ(getSpeed(), rotationYaw);
		}
	}
	
	public abstract float getRotationModifier();

	public void onCollision(float speed) {
		if (worldObj.isRemote) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageCrash(speed));
		}
		setSpeed(0.01F);
		this.motionX = 0;
		this.motionZ = 0;
	}

	public boolean canPlayerDriveCar(EntityPlayer player) {
		if (player.equals(getDriver()) && isStarted()) {
			return true;
		} else if (isInWater() || isInLava()) {
			return false;
		} else {
			return false;
		}
	}

	private void updateGravity() {
		if (hasNoGravity()) {
			this.motionY = 0;
			return;
		}

		this.motionY += -0.2D;
	}

	public void updateControls(boolean forward, boolean backward, boolean left, boolean right) {
		boolean needsUpdate = false;

		if (isForward() != forward) {
			setForward(forward);
			needsUpdate = true;
		}

		if (isBackward() != backward) {
			setBackward(backward);
			needsUpdate = true;
		}

		if (isLeft() != left) {
			setLeft(left);
			needsUpdate = true;
		}

		if (isRight() != right) {
			setRight(right);
			needsUpdate = true;
		}
		if (this.worldObj.isRemote && needsUpdate) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageControlCar(forward, backward, left, right));
		}
	}

	public void startCarEngine() {
		if (isStarted()) {
			setStarted(false);
			if (worldObj.isRemote) {
				CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStartCar(false));
			}
		} else if (getDriver() != null && canStartCarEngine(getDriver())) {
			setStarted(true);
			checkStartLoop();
			if (worldObj.isRemote) {
				CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStartCar(false));
			}
		} else {
			playEngineFailSound();
			if (worldObj.isRemote) {
				CommonProxy.simpleNetworkWrapper.sendToServer(new MessageStartCar(true));
			}
		}
	}

	public void startCarEngineServerSide(boolean failed) {
		if (failed) {
			playEngineFailSound();
		} else if (isStarted()) {
			setStarted(false);
		} else {
			setStarted(true);
		}
	}

	public boolean canStartCarEngine(EntityPlayer player) {
		if (isInWater() || isInLava()) {
			return false;
		}

		return true;
	}

	@Override
	public double getMountedYOffset() {
		return -0.4D;
	}

	public boolean canPlayerEnterCar(EntityPlayer player) {
		return true;
	}

	@Override
	public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand) {
		if (!canPlayerEnterCar(player)) {
			return false;
		}
		return super.processInitialInteract(player, stack, hand);
	}

	public float getKilometerPerHour() {
		return (getSpeed() * 20 * 60 * 60) / 1000;
	}

	public float updateWheelRotation(float delta) {
		wheelRotation += delta * getSpeed() * 8;
		return wheelRotation;
	}

	public void openCarGUi(EntityPlayer player) {
		if (worldObj.isRemote) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageCarGui(true));
		}
	}

	public boolean isAccelerating() {
		boolean b = (isForward() || isBackward()) && !isCollidedHorizontally;
		return b && isStarted();
	}

	public float getMaxSpeed() {
		return maxSpeed;
	}

	@Override
	protected void entityInit() {
		this.dataManager.register(STARTED, Boolean.valueOf(false));
		this.dataManager.register(SPEED, Float.valueOf(0));
		this.dataManager.register(FORWARD, Boolean.valueOf(false));
		this.dataManager.register(BACKWARD, Boolean.valueOf(false));
		this.dataManager.register(LEFT, Boolean.valueOf(false));
		this.dataManager.register(RIGHT, Boolean.valueOf(false));
	}

	public void setSpeed(float speed) {
		this.dataManager.set(SPEED, speed);
	}

	public float getSpeed() {
		return this.dataManager.get(SPEED);
	}

	public void setStarted(boolean started) {
		if (!started) {
			playStopSound();
		}
		this.dataManager.set(STARTED, Boolean.valueOf(started));
	}

	public boolean isStarted() {
		return this.dataManager.get(STARTED);
	}

	public void setForward(boolean forward) {
		this.dataManager.set(FORWARD, Boolean.valueOf(forward));
	}

	public boolean isForward() {
		if (getDriver() == null || !canPlayerDriveCar(getDriver())) {
			return false;
		}
		return this.dataManager.get(FORWARD);
	}

	public void setBackward(boolean backward) {
		this.dataManager.set(BACKWARD, Boolean.valueOf(backward));
	}

	public boolean isBackward() {
		if (getDriver() == null || !canPlayerDriveCar(getDriver())) {
			return false;
		}
		return this.dataManager.get(BACKWARD);
	}

	public void setLeft(boolean left) {
		this.dataManager.set(LEFT, Boolean.valueOf(left));
	}

	public boolean isLeft() {
		return this.dataManager.get(LEFT);
	}

	public void setRight(boolean right) {
		this.dataManager.set(RIGHT, Boolean.valueOf(right));
	}

	public boolean isRight() {
		return this.dataManager.get(RIGHT);
	}

	public abstract ITextComponent getCarName();

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		setStarted(compound.getBoolean("started"));
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("started", isStarted());
	}

	public void playStopSound() {
		ModSounds.playSound(getStopSound(), worldObj, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
	}

	public void playEngineFailSound() {
		ModSounds.playSound(getFailSound(), worldObj, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
	}

	public void playCrashSound() {
		ModSounds.playSound(getCrashSound(), worldObj, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
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

	public SoundEvent getIdleSound() {
		return ModSounds.engine_idle;
	}

	public SoundEvent getHighSound() {
		return ModSounds.engine_high;
	}

	@SideOnly(Side.CLIENT)
	public void checkIdleLoop() {
		if (startLoop != null && !startLoop.isDonePlaying()) {
			return;
		}
		if (idleLoop == null || idleLoop.isDonePlaying()) {
			idleLoop = new SoundLoopIdle(worldObj, this, getIdleSound(), SoundCategory.NEUTRAL);
			ModSounds.playSoundLoop(idleLoop, worldObj);
		}
	}

	@SideOnly(Side.CLIENT)
	public void checkHighLoop() {
		if (startLoop != null && !startLoop.isDonePlaying()) {
			return;
		}
		if (highLoop == null || highLoop.isDonePlaying()) {
			highLoop = new SoundLoopHigh(worldObj, this, getHighSound(), SoundCategory.NEUTRAL);
			ModSounds.playSoundLoop(highLoop, worldObj);
		}
	}

	@SideOnly(Side.CLIENT)
	public void checkStartLoop() {
		if (startLoop == null || startLoop.isDonePlaying()) {
			startLoop = new SoundLoopStart(worldObj, this, getStartSound(), SoundCategory.NEUTRAL);
			ModSounds.playSoundLoop(startLoop, worldObj);
		}
	}

}
