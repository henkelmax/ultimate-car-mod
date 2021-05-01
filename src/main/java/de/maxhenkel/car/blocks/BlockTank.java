package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.blocks.tileentity.render.item.TankItemTileEntityRenderer;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.fluid.FluidUtils;
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
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.IBlockReader;
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
import java.util.List;

public class BlockTank extends BlockBase implements ITileEntityProvider, IItemBlock {

    protected BlockTank() {
        super(Block.Properties.of(Material.GLASS).strength(0.5F).sound(SoundType.GLASS).noOcclusion());
        setRegistryName(new ResourceLocation(Main.MODID, "tank"));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR).stacksTo(1).setISTER(() -> TankItemTileEntityRenderer::new)).setRegistryName(getRegistryName());
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable IBlockReader worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        if (stack.hasTag() && stack.getTag().contains("fluid")) {
            CompoundNBT fluidComp = stack.getTag().getCompound("fluid");
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidComp);

            if (fluidStack != null) {
                tooltip.add(new TranslationTextComponent("tooltip.fluid", new StringTextComponent(fluidStack.getDisplayName().getString()).withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY));
                tooltip.add(new TranslationTextComponent("tooltip.amount", new StringTextComponent(String.valueOf(fluidStack.getAmount())).withStyle(TextFormatting.DARK_GRAY)).withStyle(TextFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public void setPlacedBy(World worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        TileEntity te = worldIn.getBlockEntity(pos);

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
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        return FluidUtils.tryFluidInteraction(player, handIn, worldIn, pos) ? ActionResultType.SUCCESS : ActionResultType.FAIL;
    }

    public static boolean handleEmpty(ItemStack stack, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand) {
        TileEntity te = worldIn.getBlockEntity(pos);

        if (!(te instanceof IFluidHandler)) {
            return false;
        }

        IFluidHandler handler = (IFluidHandler) te;

        IItemHandler inv = new InvWrapper(playerIn.inventory);

        FluidActionResult res = FluidUtil.tryEmptyContainerAndStow(stack, handler, inv, Integer.MAX_VALUE, playerIn, true);

        if (res.isSuccess()) {
            playerIn.setItemInHand(hand, res.result);
            return true;
        }

        return false;
    }

    public static boolean handleFill(ItemStack stack, World worldIn, BlockPos pos, PlayerEntity playerIn, Hand hand) {
        TileEntity te = worldIn.getBlockEntity(pos);

        if (!(te instanceof IFluidHandler)) {
            return false;
        }

        IFluidHandler blockHandler = (IFluidHandler) te;

        IItemHandler inv = new InvWrapper(playerIn.inventory);

        FluidActionResult result = FluidUtil.tryFillContainerAndStow(stack, blockHandler, inv, Integer.MAX_VALUE, playerIn, true);

        if (result.isSuccess()) {
            playerIn.setItemInHand(hand, result.result);
            return true;
        }

        return false;
    }

    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.INVISIBLE;
    }

    @Override
    public boolean propagatesSkylightDown(BlockState state, IBlockReader world, BlockPos pos) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getShadeBrightness(BlockState state, IBlockReader reader, BlockPos pos) {
        return 1F;
    }

    @Nullable
    @Override
    public TileEntity newBlockEntity(IBlockReader worldIn) {
        return new TileEntityTank();
    }

}
