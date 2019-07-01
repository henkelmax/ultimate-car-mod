package de.maxhenkel.car.blocks;

import java.util.Collections;
import java.util.List;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.tools.IItemBlock;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class BlockTank extends Block implements ITileEntityProvider, IItemBlock {

    protected BlockTank() {
        super(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.5F).sound(SoundType.GLASS));
        setRegistryName(new ResourceLocation(Main.MODID, "tank"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModCreativeTabs.TAB_CAR)).setRegistryName(this.getRegistryName());
    }

    @Override
    public void harvestBlock(World worldIn, PlayerEntity player, BlockPos pos, BlockState state, @Nullable TileEntity te, ItemStack s) {
        super.harvestBlock(worldIn, player, pos, state, te, s);

        if (player.abilities.isCreativeMode || !Config.pickUpTank) {
            return;
        }

        ItemStack stack = new ItemStack(this);

        if (!(te instanceof TileEntityTank)) {
            spawnAsEntity(worldIn, pos, stack);
            return;
        }

        TileEntityTank tank = (TileEntityTank) te;

        FluidStack fluid = tank.getFluid();

        if (fluid == null) {
            spawnAsEntity(worldIn, pos, stack);
            return;
        }

        CompoundNBT compound = new CompoundNBT();

        CompoundNBT comp = new CompoundNBT();

        fluid.writeToNBT(comp);

        compound.put("fluid", comp);

        stack.setTag(compound);

        spawnAsEntity(worldIn, pos, stack);
    }

   /* @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        super.onBlockHarvested(worldIn, pos, state, player);

        if (player.capabilities.isCreativeMode || !Config.pickUpTank) {
            return;
        }

        ItemStack stack = new ItemStack(this);

        TileEntity te = worldIn.getTileEntity(pos);

        if (!(te instanceof TileEntityTank)) {
            spawnAsEntity(worldIn, pos, stack);
            super.breakBlock(worldIn, pos, state);
            return;
        }

        TileEntityTank tank = (TileEntityTank) te;

        FluidStack fluid = tank.getFluid();

        if (fluid == null) {
            spawnAsEntity(worldIn, pos, stack);
            super.breakBlock(worldIn, pos, state);
            return;
        }

        NBTTagCompound compound = new NBTTagCompound();

        NBTTagCompound comp = new NBTTagCompound();

        fluid.writeToNBT(comp);

        compound.setTag("fluid", comp);

        stack.setTagCompound(compound);

        spawnAsEntity(worldIn, pos, stack);
    }*/

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag() && stack.getTag().contains("fluid")) {
            CompoundNBT fluidComp = stack.getTag().getCompound("fluid");
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidComp);

            if (fluidStack != null) {
                tooltip.add(new TranslationTextComponent("tooltip.fluid", fluidStack.getLocalizedName()));
                tooltip.add(new TranslationTextComponent("tooltip.amount", fluidStack.amount));
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    //TODO check / compatibility with loot tables
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        if (!Config.pickUpTank) {
            return super.getDrops(state, builder);
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        if (!stack.hasTag()) {
            return;
        }

        CompoundNBT comp = stack.getTag();

        if (!comp.contains("fluid")) {
            return;
        }

        CompoundNBT fluidTag = comp.getCompound("fluid");

        TileEntity te = worldIn.getTileEntity(pos);

        if (!(te instanceof TileEntityTank)) {
            return;
        }

        TileEntityTank tank = (TileEntityTank) te;

        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);

        tank.setFluid(fluidStack);
        tank.synchronize();
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);

        if (stack == null) {
            return false;
        }

        FluidStack fluidStack = FluidUtil.getFluidContained(stack).orElse(null);

        if (fluidStack != null) {
            handleEmpty(stack, worldIn, pos, player, handIn);
            return true;
        }

        IFluidHandler handler = FluidUtil.getFluidHandler(stack).orElse(null);

        if (handler != null) {
            handleFill(stack, worldIn, pos, player, handIn);
            return true;
        }

        return false;
    }

    public static boolean handleEmpty(ItemStack stack, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand) {
        TileEntity te = worldIn.getTileEntity(pos);

        if (!(te instanceof IFluidHandler)) {
            return false;
        }

        IFluidHandler handler = (IFluidHandler) te;

        IItemHandler inv = new InvWrapper(playerIn.inventory);

        FluidActionResult res = FluidUtil.tryEmptyContainerAndStow(stack, handler, inv, Integer.MAX_VALUE, playerIn, true);

        if (res.isSuccess()) {
            playerIn.setHeldItem(hand, res.result);
            return true;
        }

        return false;
    }

    public static boolean handleFill(ItemStack stack, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand) {
        TileEntity te = worldIn.getTileEntity(pos);

        if (!(te instanceof IFluidHandler)) {
            return false;
        }

        IFluidHandler blockHandler = (IFluidHandler) te;

        IItemHandler inv = new InvWrapper(playerIn.inventory);

        FluidActionResult result = FluidUtil.tryFillContainerAndStow(stack, blockHandler, inv, Integer.MAX_VALUE,
                playerIn, true);

        if (result.isSuccess()) {
            playerIn.setHeldItem(hand, result.result);
            return true;
        }

        return false;
    }

    @Override
    public boolean isNormalCube(BlockState state, IBlockReader worldIn, BlockPos pos) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(BlockState state, IEnviromentBlockReader world, BlockPos pos, Direction face) {
        return false;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityTank();
    }
}
