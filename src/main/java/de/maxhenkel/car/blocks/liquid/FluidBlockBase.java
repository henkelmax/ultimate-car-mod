package de.maxhenkel.car.blocks.liquid;

import de.maxhenkel.car.Main;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public abstract class FluidBlockBase extends Block implements IFluidBlock {

    public FluidBlockBase(String name) {
        super(Properties.create(Material.WATER, MaterialColor.YELLOW));
        setRegistryName(new ResourceLocation(Main.MODID, name));
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        if(entityIn instanceof PlayerEntity){
            PlayerEntity player=(PlayerEntity) entityIn;
            if(!player.abilities.isCreativeMode){
                player.addPotionEffect(new EffectInstance(Effects.NAUSEA, 100, 0, true, false));
            }
        }
        super.onEntityCollision(state, worldIn, pos, entityIn);
    }

    @Override
    public int place(World world, BlockPos pos, @Nonnull FluidStack fluidStack, boolean doPlace) {
        //TODO implement
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(World world, BlockPos pos, boolean doDrain) {
        //TODO implement
        return null;
    }

    @Override
    public boolean canDrain(World world, BlockPos pos) {
        //TODO implement
        return false;
    }

    @Override
    public float getFilledPercentage(World world, BlockPos pos) {
        //TODO implement
        return 0;
    }
}
