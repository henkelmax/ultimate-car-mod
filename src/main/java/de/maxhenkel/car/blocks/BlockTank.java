package de.maxhenkel.car.blocks;

import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.blocks.tileentity.render.item.TankItemTileEntityRenderer;
import de.maxhenkel.tools.FluidUtils;
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
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IEnviromentBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class BlockTank extends BlockBase implements ITileEntityProvider, IItemBlock {

    protected BlockTank() {
        super(Block.Properties.create(Material.GLASS).hardnessAndResistance(0.5F).sound(SoundType.GLASS));
        setRegistryName(new ResourceLocation(Main.MODID, "tank"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModItemGroups.TAB_CAR).maxStackSize(1).setTEISR(() -> TankItemTileEntityRenderer::new)).setRegistryName(getRegistryName());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag() && stack.getTag().contains("fluid")) {
            CompoundNBT fluidComp = stack.getTag().getCompound("fluid");
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidComp);

            if (fluidStack != null) {
                tooltip.add(new TranslationTextComponent("tooltip.fluid", fluidStack.getDisplayName().applyTextStyle(TextFormatting.DARK_GRAY)).applyTextStyle(TextFormatting.GRAY));
                tooltip.add(new TranslationTextComponent("tooltip.amount", new StringTextComponent(String.valueOf(fluidStack.getAmount())).applyTextStyle(TextFormatting.DARK_GRAY)).applyTextStyle(TextFormatting.GRAY));
            }
        }
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity te = worldIn.getTileEntity(pos);

        if (!(te instanceof TileEntityTank)) {
            return;
        }

        TileEntityTank tank = (TileEntityTank) te;

        applyItemData(stack, tank);
        tank.synchronize();
    }

    public static void applyItemData(ItemStack stack, TileEntityTank tank) {
        if (!stack.hasTag()) {
            tank.setFluid(FluidStack.EMPTY);
            return;
        }

        CompoundNBT comp = stack.getTag();

        if (!comp.contains("fluid")) {
            return;
        }

        CompoundNBT fluidTag = comp.getCompound("fluid");

        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);

        tank.setFluid(fluidStack);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack stack = player.getHeldItem(handIn);

        if (FluidUtil.getFluidContained(stack).orElse(null) != null) {
            FluidUtils.tryFluidInteraction(player, handIn, worldIn, pos);
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

        FluidActionResult result = FluidUtil.tryFillContainerAndStow(stack, blockHandler, inv, Integer.MAX_VALUE, playerIn, true);

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

    @Override
    public boolean propagatesSkylightDown(BlockState p_200123_1_, IBlockReader p_200123_2_, BlockPos p_200123_3_) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float func_220080_a(BlockState state, IBlockReader reader, BlockPos pos) {
        return 1F;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(IBlockReader worldIn) {
        return new TileEntityTank();
    }

    @Override
    public boolean hasCustomBreakingProgress(BlockState state) {
        return true;
    }
}
