package de.maxhenkel.car.fluids;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.FlowingFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.IFluidState;
import net.minecraft.item.Item;
import net.minecraft.state.StateContainer;
import net.minecraft.tags.FluidTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidAttributes;

public abstract class CarFluid extends FlowingFluid {

    protected FluidAttributes attributes;
    private ResourceLocation still;
    private ResourceLocation flowing;

    public CarFluid(ResourceLocation registryName, ResourceLocation still, ResourceLocation flowing) {
        this.still = still;
        this.flowing = flowing;
        setRegistryName(registryName);
        attributes = build().build();
    }

    protected FluidAttributes.Builder build() {
        return FluidAttributes.builder(getRegistryName().getPath(), still, flowing).sound(SoundEvents.ITEM_BUCKET_FILL);
    }

    public abstract void applyEffects(Entity entity, BlockState state, World worldIn, BlockPos pos);

    @Override
    protected FluidAttributes createAttributes(Fluid fluid) {
        return attributes;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public abstract Item getFilledBucket();

    @Override
    protected boolean func_215665_a(IFluidState fluidState, IBlockReader blockReader, BlockPos pos, Fluid fluid, Direction direction) {
        return direction == Direction.DOWN && !fluid.isIn(FluidTags.WATER);
    }

    @Override
    public int getTickRate(IWorldReader p_205569_1_) {
        return 5;
    }

    @Override
    protected float getExplosionResistance() {
        return 100F;
    }

    @Override
    protected BlockState getBlockState(IFluidState state) {
        return getFluidBlock().getDefaultState().with(FlowingFluidBlock.LEVEL, Integer.valueOf(getLevelFromState(state)));
    }

    public abstract Block getFluidBlock();

    @Override
    public boolean isSource(IFluidState state) {
        return false;
    }

    @Override
    public int getLevel(IFluidState state) {
        return state.get(LEVEL_1_8);
    }

    @Override
    public Fluid getFlowingFluid() {
        if (this instanceof Flowing) {
            return this;
        }
        return getEquivalent();
    }

    @Override
    public Fluid getStillFluid() {
        if (this instanceof Source) {
            return this;
        }
        return getEquivalent();
    }

    public abstract Fluid getEquivalent();

    @Override
    public boolean isEquivalentTo(Fluid fluidIn) {
        return fluidIn == getEquivalent() || fluidIn == this;
    }

    @Override
    protected boolean canSourcesMultiply() {
        return false;
    }

    @Override
    protected void beforeReplacingBlock(IWorld worldIn, BlockPos pos, BlockState state) {
        TileEntity tileentity = state.getBlock().hasTileEntity() ? worldIn.getTileEntity(pos) : null;
        Block.spawnDrops(state, worldIn.getWorld(), pos, tileentity);
    }

    @Override
    protected int getSlopeFindDistance(IWorldReader worldIn) {
        return 4;
    }

    @Override
    protected int getLevelDecreasePerBlock(IWorldReader worldIn) {
        return 1;
    }

    public static abstract class Flowing extends CarFluid {

        public Flowing(ResourceLocation registryName, ResourceLocation still, ResourceLocation flowing) {
            super(registryName, still, flowing);
        }

        protected void fillStateContainer(StateContainer.Builder<Fluid, IFluidState> builder) {
            super.fillStateContainer(builder);
            builder.add(LEVEL_1_8);
        }

        public int getLevel(IFluidState state) {
            return state.get(LEVEL_1_8);
        }

        public boolean isSource(IFluidState state) {
            return false;
        }
    }

    public static abstract class Source extends CarFluid {

        public Source(ResourceLocation registryName, ResourceLocation still, ResourceLocation flowing) {
            super(registryName, still, flowing);
        }

        public int getLevel(IFluidState state) {
            return 8;
        }

        public boolean isSource(IFluidState state) {
            return true;
        }
    }

}

