package de.maxhenkel.car.entity.car.base;

import java.util.List;
import de.maxhenkel.car.Config;
import de.maxhenkel.car.DamageSourceCar;
import de.maxhenkel.car.IDrivable;
import de.maxhenkel.car.net.*;
import de.maxhenkel.car.sounds.SoundLoopStart;
import de.maxhenkel.tools.MathTools;
import de.maxhenkel.tools.Teleport;
import de.maxhenkel.car.proxy.CommonProxy;
import de.maxhenkel.car.reciepe.ICarbuilder;
import de.maxhenkel.car.registries.CarProperties;
import de.maxhenkel.car.sounds.ModSounds;
import de.maxhenkel.car.sounds.SoundLoopHigh;
import de.maxhenkel.car.sounds.SoundLoopIdle;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityCarBase extends EntityVehicleBase {

	private float maxSpeed = 0.5F;
	protected float maxReverseSpeed = 0.2F;
	protected float acceleration = 0.032F;
	protected float maxRotationSpeed = 5F;
	protected float rollResistance = 0.02F;
	protected float minRotationSpeed=2.0F;

	private float wheelRotation;

	@SideOnly(Side.CLIENT)
	private boolean collidedLastTick;

    @SideOnly(Side.CLIENT)
    private SoundLoopStart startLoop;
	@SideOnly(Side.CLIENT)
	private SoundLoopIdle idleLoop;
	@SideOnly(Side.CLIENT)
	private SoundLoopHigh highLoop;

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
		this.stepHeight=Config.carStepHeight;

		for(CarProperties props:CarProperties.REGISTRY) {
			if(props.getCarID().equals(getID())) {
				this.maxSpeed=props.getSpeed();
				this.acceleration=props.getAcceleration();
				this.maxReverseSpeed=props.getReverseSpeed();
			}
		}
	}

	@Override
	public void onUpdate() {
		super.onUpdate();

		handleTeleport();

		if (isStarted() && !canEngineStayOn()) {
			setStarted(false);
		}

		this.updateGravity();
		this.controlCar();
		this.checkPush();
		this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);

		if (world.isRemote) {
			updateSounds();
		}

	}

	public void centerCar(){
		if(world.isRemote){
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageCenterCar(getDriver()));
		}
        EnumFacing facing=getHorizontalFacing();
        switch (facing){
            case SOUTH:
                rotationYaw=0F;
                break;
            case NORTH:
                rotationYaw=180F;
                break;
            case EAST:
                rotationYaw=-90F;
                break;
            case WEST:
                rotationYaw=90F;
                break;
        }
    }

	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		if(Config.damageEntities) {
			if(entityIn.getEntityBoundingBox().intersects(getCollisionBoundingBox())) {
				float speed=getSpeed();
				if(speed>0.35F) {
					float damage=speed*10;
					entityIn.attackEntityFrom(DamageSourceCar.DAMAGE_CAR, damage);
				}

			}
		}
		return super.getCollisionBox(entityIn);
	}

	private void handleTeleport() {
		if(!Config.teleportDimension) {
			return;
		}

		if(getSpeed()>=maxSpeed) {
			int dimid=world.provider.getDimension();
			if(dimid==Config.teleportDimID) {
				return;
			}

			for(Entity e:getPassengers()) {
				e.dismountRidingEntity();
				Teleport.teleportToDimension(e, Config.teleportDimID);

			}
			Teleport.teleportToDimension(this, Config.teleportDimID);
		}
	}


	public void checkPush() {
		if(getCollisionBoundingBox()==null){
			return;
		}

		List<EntityPlayer> list = world.getEntitiesWithinAABB(EntityPlayer.class,
				getCollisionBoundingBox().expand(0.2, 0, 0.2).expand(-0.2, 0, -0.2),
				EntitySelectors.<EntityPlayer>getTeamCollisionPredicate(this));

		for (int j = 0; j < list.size(); j++) {
			EntityPlayer player = list.get(j);
			if (!player.isPassenger(this) && player.isSneaking()) {
				double motX = calculateMotionX(0.05F, player.rotationYaw);
				double motZ = calculateMotionZ(0.05F, player.rotationYaw);
				//moveEntity(motX, 0, motZ);
				move(MoverType.PLAYER, motX, 0, motZ);
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

	@SideOnly(Side.CLIENT)
	private boolean startedLast;

    @SideOnly(Side.CLIENT)
	public void updateSounds() {
        if (getSpeed() == 0 && isStarted()) {

        	if(!startedLast){
				checkStartLoop();
			}else if(!isSoundPlaying(startLoop)){
        	    if(startLoop!=null){
        	        startLoop.setDonePlaying();
                    startLoop=null;
                }

                checkIdleLoop();
            }
		}
		if (getSpeed() != 0 && isStarted()) {
			checkHighLoop();
		}

		startedLast=isStarted();
	}

    @SideOnly(Side.CLIENT)
    public boolean isSoundPlaying(ISound sound){
        if(sound==null){
            return false;
        }
        return Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound);
    }

	public void destroyCar(EntityPlayer player, boolean dropParts) {
		/*if (dropParts) {
			ICarbuilder builder=getBuilder();
			ICarRecipe reciepe = CarCraftingManager.getInstance().getReciepeByName(builder.getName());
			if (reciepe != null) {
				for (ItemStack stack : reciepe.getInputs()) {
					if(ItemTools.isStackEmpty(stack)){
						continue;
					}
					if (shouldDropItemWithChance(stack)) {
						InventoryHelper.spawnItemStack(world, posX, posY, posZ, stack);
					}
				}
			}
		}*/
		//TODO drop items?
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

		float modifier=1;

		if(Config.carGroundSpeed){
			modifier=getModifier();
		}

		float maxSp=maxSpeed*modifier;
		float maxBackSp=maxReverseSpeed*modifier;

		float speed = MathTools.subtractToZero(getSpeed(), rollResistance);

		if (isForward()) {
			if(speed<=maxSp){
				speed = Math.min(speed + acceleration, maxSp);
			}
		}

		if (isBackward()) {
			if(speed>=-maxBackSp){
				speed = Math.max(speed - acceleration, -maxBackSp);
			}
		}

		setSpeed(speed);


		float rotationSpeed = 0;
		if (Math.abs(speed) > 0.02F) {
			rotationSpeed = MathHelper.abs(getRotationModifier() / (float)Math.pow(speed, 2));

			rotationSpeed = MathHelper.clamp(rotationSpeed, minRotationSpeed, maxRotationSpeed);
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

		if (collidedHorizontally) {
			if (world.isRemote&&!collidedLastTick) {
				onCollision(speed);
				collidedLastTick=true;
			}
		} else {
			this.motionX = calculateMotionX(getSpeed(), rotationYaw);
			this.motionZ = calculateMotionZ(getSpeed(), rotationYaw);
            if (world.isRemote){
                collidedLastTick=false;
            }
		}
	}

	public float getModifier(){
		BlockPos pos=getPosition().down();
		IBlockState state=world.getBlockState(pos);

		Block b=state.getBlock();

		if(b.equals(Blocks.AIR)){
			return 1;
		}

		if(!(b instanceof IDrivable)){
			return 0.5F;
		}

		IDrivable drivable=(IDrivable) b;

		return drivable.getSpeedModifier();
	}

	public abstract float getRotationModifier();

	public void onCollision(float speed) {
		if (world.isRemote) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageCrash(speed, this));
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

	public void updateControls(boolean forward, boolean backward, boolean left, boolean right, EntityPlayer player) {
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
		if (this.world.isRemote && needsUpdate) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageControlCar(forward, backward, left, right, player));
		}
	}

	public void startCarEngine() {
	    EntityPlayer player=getDriver();
		if (player != null && canStartCarEngine(player)) {
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
	public boolean processInitialInteract(EntityPlayer player, EnumHand hand) {
		if (!canPlayerEnterCar(player)) {
			return false;
		}
		return super.processInitialInteract(player, hand);
	}

	public float getKilometerPerHour() {
		return (getSpeed() * 20 * 60 * 60) / 1000;
	}

	public float updateWheelRotation(float delta) {
		wheelRotation += delta * getSpeed() * 8;
		return wheelRotation;
	}

	public void openCarGUi(EntityPlayer player) {
		if (world.isRemote) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageCarGui(true, player));
		}
	}

	public boolean isAccelerating() {
		boolean b = (isForward() || isBackward()) && !collidedHorizontally;
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
		setStarted(started, true, false);
	}

	public void setStarted(boolean started, boolean playStopSound, boolean playFailSound) {
		if (!started&&playStopSound) {
			playStopSound();
		}else if(!started&&playFailSound){
		    playFailSound();
        }
        if(started){
		    setForward(false);
		    setBackward(false);
		    setLeft(false);
		    setRight(false);
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
		setStarted(compound.getBoolean("started"), false, false);
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setBoolean("started", isStarted());
	}

	public void playStopSound() {
		ModSounds.playSound(getStopSound(), world, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
	}

    public void playFailSound() {
        ModSounds.playSound(getFailSound(), world, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
    }

	public void playCrashSound() {
		ModSounds.playSound(getCrashSound(), world, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
	}

	public void playHornSound() {
		ModSounds.playSound(getHornSound(), world, getPosition(), null, SoundCategory.NEUTRAL, Config.carVolume);
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

	@SideOnly(Side.CLIENT)
	public void checkIdleLoop() {
		if (!isSoundPlaying(idleLoop)) {
			idleLoop = new SoundLoopIdle(world, this, getIdleSound(), SoundCategory.NEUTRAL);
			ModSounds.playSoundLoop(idleLoop, world);
		}
	}

	@SideOnly(Side.CLIENT)
	public void checkHighLoop() {
		if (!isSoundPlaying(highLoop)) {
			highLoop = new SoundLoopHigh(world, this, getHighSound(), SoundCategory.NEUTRAL);
			ModSounds.playSoundLoop(highLoop, world);
		}
	}

    @SideOnly(Side.CLIENT)
    public void checkStartLoop() {
        if (!isSoundPlaying(startLoop)) {
            startLoop = new SoundLoopStart(world, this, getStartSound(), SoundCategory.NEUTRAL);
            ModSounds.playSoundLoop(startLoop, world);
        }
    }

	public void onHornPressed(EntityPlayer player) {
		if (world.isRemote) {
			CommonProxy.simpleNetworkWrapper.sendToServer(new MessageCarHorn(true, player));
		}else{
		    if(this instanceof EntityCarBatteryBase){
                EntityCarBatteryBase car= (EntityCarBatteryBase) this;
                if(car.getBatteryLevel()<10){
                    return;
                }
                if(Config.useBattery){
                    car.setBatteryLevel(car.getBatteryLevel()-10);
                }
            }
			playHornSound();
			if(Config.hornFlee) {
				double radius=15;
				List<EntityLiving> list=world.getEntitiesWithinAABB(EntityLiving.class, new AxisAlignedBB(posX-radius, posY-radius, posZ-radius, posX+radius, posY+radius, posZ+radius));
				for(EntityLiving ent:list) {
					fleeEntity(ent);
				}
			}
		}
	}

	public void fleeEntity(EntityLiving entity) {
		double fleeDistance=10;
		Vec3d vecCar=new Vec3d(posX, posY, posZ);
		Vec3d vecEntity=new Vec3d(entity.posX, entity.posY, entity.posZ);
		Vec3d fleeDir=vecEntity.subtract(vecCar);
		fleeDir=fleeDir.normalize();
		Vec3d fleePos=new Vec3d(vecEntity.x+fleeDir.x*fleeDistance, vecEntity.y+fleeDir.y*fleeDistance, vecEntity.z+fleeDir.z*fleeDistance);

		entity.getNavigator().tryMoveToXYZ(fleePos.x, fleePos.y, fleePos.z, 2.5);
	}

}
