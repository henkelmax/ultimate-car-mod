package de.maxhenkel.car.blocks;

import java.util.List;

import de.maxhenkel.car.Config;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class BlockTank extends BlockContainer {

    protected BlockTank() {
        super(Material.GLASS);
        setUnlocalizedName("tank");
        setRegistryName("tank");
        setCreativeTab(ModCreativeTabs.TAB_CAR);
        setHardness(0.5F);
    }

    @Override
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
    }

    @Override
    public void addInformation(ItemStack stack, World player, List<String> tooltip, ITooltipFlag advanced) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("fluid")) {
            NBTTagCompound fluidComp = stack.getTagCompound().getCompoundTag("fluid");
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidComp);

            if (fluidStack != null) {
                tooltip.add(new TextComponentTranslation("tooltip.fluid", fluidStack.getLocalizedName()).getFormattedText());
                tooltip.add(new TextComponentTranslation("tooltip.amount", fluidStack.amount).getFormattedText());
            }
        }
        super.addInformation(stack, player, tooltip, advanced);
    }

    @Override
    public void dropBlockAsItemWithChance(World worldIn, BlockPos pos, IBlockState state, float chance, int fortune) {
        if(!Config.pickUpTank){
            super.dropBlockAsItemWithChance(worldIn, pos, state, chance, fortune);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        if (!stack.hasTagCompound()) {
            return;
        }

        NBTTagCompound comp = stack.getTagCompound();

        if (!comp.hasKey("fluid")) {
            return;
        }

        NBTTagCompound fluidTag = comp.getCompoundTag("fluid");

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
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityTank();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = playerIn.getHeldItem(hand);

        if (stack == null) {
            return false;
        }

        FluidStack fluidStack = FluidUtil.getFluidContained(stack);

        if (fluidStack != null) {
            handleEmpty(stack, worldIn, pos, playerIn, hand);
            return true;
        }

        IFluidHandler handler = FluidUtil.getFluidHandler(stack);

        if (handler != null) {
            handleFill(stack, worldIn, pos, playerIn, hand);
            return true;
        }

        return false;
    }

    public static boolean handleEmpty(ItemStack stack, World worldIn, BlockPos pos,
                                      EntityPlayer playerIn, EnumHand hand) {
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

    public static boolean handleFill(ItemStack stack, World worldIn, BlockPos pos,
                                     EntityPlayer playerIn, EnumHand hand) {
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
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
        return false;
    }

    @Override
    public BlockRenderLayer getBlockLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.INVISIBLE;
    }

}
