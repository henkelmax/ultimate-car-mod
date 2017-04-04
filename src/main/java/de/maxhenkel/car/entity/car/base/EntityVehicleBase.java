package de.maxhenkel.car.entity.car.base;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class EntityVehicleBase extends Entity{

	private int steps;
	private double clientX;
	//private double clientY;
	private double clientZ;
	private double clientYaw;
	private double clientPitch;
	
	protected float deltaRotation;
	
	public EntityVehicleBase(World worldIn) {
		super(worldIn);
		this.preventEntitySpawning = true;
		this.stepHeight = 0.6F;
	}

	@Override
	public void onUpdate() {
		
		if (!worldObj.isRemote) {
			this.prevPosX = this.posX;
			this.prevPosY = this.posY;
			this.prevPosZ = this.posZ;
		}

		this.setPositionNonDirty();
		
		super.onUpdate();
		this.tickLerp();
		
	}
	
	public EntityPlayer getDriver() {
		List<Entity> passengers = getPassengers();
		if (passengers.size() <= 0) {
			return null;
		}

		if (passengers.get(0) instanceof EntityPlayer) {
			return (EntityPlayer) passengers.get(0);
		}

		return null;
	}
	
	public int getPassengerSize() {
		return 1;
	}

	@Override
	protected boolean canFitPassenger(Entity passenger) {
		return this.getPassengers().size() < getPassengerSize();
	}
	
	protected void applyYawToEntity(Entity entityToUpdate) {
		entityToUpdate.setRenderYawOffset(this.rotationYaw);
		float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
		float f1 = MathHelper.clamp_float(f, -130.0F, 130.0F);
		entityToUpdate.prevRotationYaw += f1 - f;
		entityToUpdate.rotationYaw += f1 - f;
		entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
	}

	/**
	 * Applies this entity's orientation (pitch/yaw) to another entity. Used to
	 * update passenger orientation.
	 */
	@SideOnly(Side.CLIENT)
	public void applyOrientationToEntity(Entity entityToUpdate) {
		this.applyYawToEntity(entityToUpdate);
	}
	
	public abstract float getOffsetForPassenger(int i, Entity passenger);
	
	@Override
	public void updatePassenger(Entity passenger) {
		if (!isPassenger(passenger)) {
			return;
		}

		float f = 0.0F;

		List<Entity> passengers = getPassengers();

		if (passengers.size() > 0) {
			int i = passengers.indexOf(passenger);

			f=getOffsetForPassenger(i, passenger);
		}

		Vec3d vec3d = (new Vec3d((double) f, 0.0D, 0.0D))
				.rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
		passenger.setPosition(this.posX + vec3d.xCoord, this.posY + getMountedYOffset(), this.posZ + vec3d.zCoord);
		passenger.rotationYaw += this.deltaRotation;
		passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
		this.applyYawToEntity(passenger);
	}
	
	@Override
	public Entity getControllingPassenger() {
		return getDriver();
	}
	
	/**
	 * Returns a boundingBox used to collide the entity with other entities and
	 * blocks. This enables the entity to be pushable on contact, like boats or
	 * minecarts.
	 */
	@Nullable
	@Override
	public AxisAlignedBB getCollisionBox(Entity entityIn) {
		return entityIn.canBePushed() ? entityIn.getEntityBoundingBox() : null;
	}

	/**
	 * Returns the collision bounding box for this entity
	 */
	@Nullable
	@Override
	public AxisAlignedBB getCollisionBoundingBox() {
		return this.getEntityBoundingBox();
	}

	/**
	 * Returns true if this entity should push and be pushed by other entities
	 * when colliding.
	 */
	@Override
	public boolean canBePushed() {
		return true;
	}
	
	/**
	 * Returns true if other Entities should be prevented from moving through
	 * this Entity.
	 */
	@Override
	public boolean canBeCollidedWith() {
		return !this.isDead;
	}
	
	private void tickLerp() {
		if (this.steps > 0 && !this.canPassengerSteer()) {
			double x = posX + (clientX - posX) / (double) steps;
			//double y = posY + (clientY - posY) / (double) steps;
			double z = posZ + (clientZ - posZ) / (double) steps;
			double d3 = MathHelper.wrapDegrees(clientYaw - (double) rotationYaw);
			this.rotationYaw = (float) ((double) rotationYaw + d3 / (double) steps);
			this.rotationPitch = (float) ((double) rotationPitch
					+ (clientPitch - (double) rotationPitch) / (double) steps);
			steps--;
			setPosition(x, posY, z);
			setRotation(rotationYaw, rotationPitch);
		}
	}
	
	/**
	 * Set the position and rotation values directly without any clamping.
	 */
	@SideOnly(Side.CLIENT)
	public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch,
			int posRotationIncrements, boolean teleport) {
		this.clientX = x;
		//this.clientY = y;
		this.clientZ = z;
		this.clientYaw = (double) yaw;
		this.clientPitch = (double) pitch;
		this.steps = 10;
		
		posY=y;
		//this.setPosition(x, y, z);
		//this.setRotation(yaw, pitch);
	}
	
	public static final double calculateMotionX(float speed, float rotationYaw) {
		return (double) (MathHelper.sin(-rotationYaw * 0.017453292F) * speed);
	}

	public static final double calculateMotionZ(float speed, float rotationYaw) {
		return (double) (MathHelper.cos(rotationYaw * 0.017453292F) * speed);
	}
	
	@Override
	protected boolean canTriggerWalking() {
		return false;
	}
	
	@Override
	public boolean processInitialInteract(EntityPlayer player, ItemStack stack, EnumHand hand) {
		if (!player.isSneaking()) {
			if(player.getRidingEntity()!=this){
				if (!worldObj.isRemote) {
					player.startRiding(this);
				}
			}
			return true;
		}
		return false;
	}
	
	public boolean doesEnterThirdPerson(){
		return true;
	}

}
