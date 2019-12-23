package de.maxhenkel.car.entity.car.base;

import java.util.List;
import javax.annotation.Nullable;

import de.maxhenkel.car.Config;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.IPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.NetworkHooks;

public abstract class EntityVehicleBase extends Entity {

    public static float SCALE_FACTOR = 0.7F;

    private int steps;
    private double clientX;
    private double clientY;
    private double clientZ;
    private double clientYaw;
    private double clientPitch;

    protected float deltaRotation;

    protected AxisAlignedBB boundingBox;

    public EntityVehicleBase(EntityType type, World worldIn) {
        super(type, worldIn);
        this.preventEntitySpawning = true;
        this.stepHeight = 0.6F;

        recalculateBoundingBox();
    }

    @Override
    public void tick() {
        if (!world.isRemote) {
            this.prevPosX = func_226277_ct_();
            this.prevPosY = func_226278_cu_();
            this.prevPosZ = func_226281_cx_();
        }

        setPositionNonDirty();

        super.tick();
        this.tickLerp();

        recalculateBoundingBox();
    }

    public void recalculateBoundingBox() {
        double width = getCarWidth();
        double height = getCarHeight();
        boundingBox = new AxisAlignedBB(func_226277_ct_() - width / 2D, func_226278_cu_(), func_226281_cx_() - width / 2D, func_226277_ct_() + width / 2D, func_226278_cu_() + height, func_226281_cx_() + width / 2D);
    }

    public double getCarWidth() {
        return 1.3D;
    }

    public double getCarHeight() {
        return 1.6D;
    }

    @Override
    protected void removePassenger(Entity passenger) {
        super.removePassenger(passenger);
        if (passenger instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) passenger;
            Direction facing = getHorizontalFacing();

            double offsetX = 0D;
            double offsetZ = 0D;

            for (int i = 0; i < 4; i++) {
                AxisAlignedBB playerbb = player.getBoundingBox();
                double playerHitboxWidth = playerbb.getXSize() / 2D;
                double carHitboxWidth = getCarWidth() / 2D;

                double offset = playerHitboxWidth + carHitboxWidth + 0.2D;

                offsetX += facing.getXOffset() * offset;
                offsetZ += facing.getZOffset() * offset;

                AxisAlignedBB aabb = player.getBoundingBox().offset(offsetX, 0D, offsetZ);

                if (!world.checkBlockCollision(aabb) && world.func_226668_i_(player)) { //world.checkNoEntityCollision(player) //TODO
                    break;
                }

                offsetX = 0D;
                offsetZ = 0D;
                facing = facing.rotateY();
            }

            player.setPositionAndUpdate(func_226277_ct_() + offsetX, func_226278_cu_(), func_226281_cx_() + offsetZ);
        }
    }

    public PlayerEntity getDriver() {
        List<Entity> passengers = getPassengers();
        if (passengers.size() <= 0) {
            return null;
        }

        if (passengers.get(0) instanceof PlayerEntity) {
            return (PlayerEntity) passengers.get(0);
        }

        return null;
    }

    public abstract int getPassengerSize();

    @Override
    protected boolean canFitPassenger(Entity passenger) {
        return this.getPassengers().size() < getPassengerSize();
    }

    protected void applyYawToEntity(Entity entityToUpdate) {
        entityToUpdate.setRenderYawOffset(this.rotationYaw);
        float f = MathHelper.wrapDegrees(entityToUpdate.rotationYaw - this.rotationYaw);
        float f1 = MathHelper.clamp(f, -130.0F, 130.0F);
        entityToUpdate.prevRotationYaw += f1 - f;
        entityToUpdate.rotationYaw += f1 - f;
        entityToUpdate.setRotationYawHead(entityToUpdate.rotationYaw);
    }

    /**
     * Applies this entity's orientation (pitch/yaw) to another entity. Used to
     * update passenger orientation.
     */
    @OnlyIn(Dist.CLIENT)
    public void applyOrientationToEntity(Entity entityToUpdate) {
        this.applyYawToEntity(entityToUpdate);
    }

    public abstract Vec3d[] getPlayerOffsets();

    @Override
    public void updatePassenger(Entity passenger) {
        if (!isPassenger(passenger)) {
            return;
        }

        double front = 0.0F;
        double side = 0.0F;
        double height = 0.0F;

        List<Entity> passengers = getPassengers();

        if (passengers.size() > 0) {
            int i = passengers.indexOf(passenger);

            Vec3d offset = getPlayerOffsets()[i];
            front = offset.x;
            side = offset.z;
            height = offset.y;
        }

        Vec3d vec3d = (new Vec3d(front, height, side))
                .rotateYaw(-this.rotationYaw * 0.017453292F - ((float) Math.PI / 2F));
        passenger.setPosition(func_226277_ct_() + vec3d.x, func_226278_cu_() + vec3d.y, func_226281_cx_() + vec3d.z);
        passenger.rotationYaw += deltaRotation;
        passenger.setRotationYawHead(passenger.getRotationYawHead() + this.deltaRotation);
        applyYawToEntity(passenger);
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
        if (!Config.collideWithEntities.get()) {
            if (!(entityIn instanceof EntityVehicleBase)) {
                return null;
            }
        }
        return entityIn.canBePushed() ? entityIn.getBoundingBox() : null;
    }

    /**
     * Returns the collision bounding box for this entity
     */
    @Nullable
    @Override
    public AxisAlignedBB getCollisionBoundingBox() {
        return this.getBoundingBox();
    }

    @Override
    public AxisAlignedBB getBoundingBox() {
        return boundingBox;
    }

    @Override
    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
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
        return isAlive();
    }

    private void tickLerp() {
        if (this.steps > 0 && !this.canPassengerSteer()) {
            double x = func_226277_ct_() + (clientX - func_226277_ct_()) / (double) steps;
            double y = func_226278_cu_() + (clientY - func_226278_cu_()) / (double) steps;
            double z = func_226281_cx_() + (clientZ - func_226281_cx_()) / (double) steps;
            double d3 = MathHelper.wrapDegrees(clientYaw - (double) rotationYaw);
            this.rotationYaw = (float) ((double) rotationYaw + d3 / (double) steps);
            this.rotationPitch = (float) ((double) rotationPitch
                    + (clientPitch - (double) rotationPitch) / (double) steps);
            steps--;
            setPosition(x, y, z);
            setRotation(rotationYaw, rotationPitch);
        }
    }

    /**
     * Set the position and rotation values directly without any clamping.
     */
    @OnlyIn(Dist.CLIENT)
    public void setPositionAndRotationDirect(double x, double y, double z, float yaw, float pitch,
                                             int posRotationIncrements, boolean teleport) {
        this.clientX = x;
        this.clientY = y;
        this.clientZ = z;
        this.clientYaw = (double) yaw;
        this.clientPitch = (double) pitch;
        this.steps = 10;
    }

    public static final double calculateMotionX(float speed, float rotationYaw) {
        return (double) (MathHelper.sin(-rotationYaw * 0.017453292F) * speed);
    }

    public static final double calculateMotionZ(float speed, float rotationYaw) {
        return (double) (MathHelper.cos(rotationYaw * 0.017453292F) * speed);
    }

    //TODO
    /*@Override
    protected boolean canTriggerWalking() {
        return false;
    }*/

    @Override
    public boolean processInitialInteract(PlayerEntity player, Hand hand) {
        if (!player.func_225608_bj_()) {
            if (player.getRidingEntity() != this) {
                if (!world.isRemote) {
                    player.startRiding(this);
                }
            }
            return true;
        }
        return false;
    }

    public abstract boolean doesEnterThirdPerson();

    @Override
    public IPacket<?> createSpawnPacket() {
        return NetworkHooks.getEntitySpawningPacket(this);
    }
}
