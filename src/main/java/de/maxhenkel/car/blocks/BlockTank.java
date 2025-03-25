package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import de.maxhenkel.corelib.fluid.FluidUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.neoforge.fluids.FluidActionResult;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.FluidUtil;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.fluids.capability.IFluidHandler;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;

import javax.annotation.Nullable;

public class BlockTank extends BlockBase implements EntityBlock {

    protected BlockTank(Properties properties) {
        super(properties.mapColor(MapColor.METAL).strength(0.5F).sound(SoundType.GLASS).noOcclusion());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    public void setPlacedBy(Level worldIn, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(worldIn, pos, state, placer, stack);

        BlockEntity te = worldIn.getBlockEntity(pos);

        if (!(te instanceof TileEntityTank)) {
            return;
        }

        TileEntityTank tank = (TileEntityTank) te;

        applyItemData(stack, tank);
        tank.synchronize();
    }

    public static void applyItemData(ItemStack stack, TileEntityTank tank) {
        SimpleFluidContent content = stack.get(Main.FLUID_STACK_DATA_COMPONENT);
        if (content == null) {
            tank.setFluid(FluidStack.EMPTY);
            return;
        }
        tank.setFluid(content.copy());
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        return FluidUtils.tryFluidInteraction(player, interactionHand, level, blockPos) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    public static boolean handleEmpty(ItemStack stack, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand) {
        BlockEntity be = worldIn.getBlockEntity(pos);
        if (!(be instanceof IFluidHandler handler)) {
            return false;
        }
        IItemHandler inv = new InvWrapper(playerIn.getInventory());
        FluidActionResult res = FluidUtil.tryEmptyContainerAndStow(stack, handler, inv, Integer.MAX_VALUE, playerIn, true);
        if (res.isSuccess()) {
            playerIn.setItemInHand(hand, res.result);
            return true;
        }
        return false;
    }

    public static boolean handleFill(ItemStack stack, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand) {
        BlockEntity te = worldIn.getBlockEntity(pos);
        if (!(te instanceof IFluidHandler blockHandler)) {
            return false;
        }
        IItemHandler inv = new InvWrapper(playerIn.getInventory());
        FluidActionResult result = FluidUtil.tryFillContainerAndStow(stack, blockHandler, inv, Integer.MAX_VALUE, playerIn, true);
        if (result.isSuccess()) {
            playerIn.setItemInHand(hand, result.result);
            return true;
        }
        return false;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public float getShadeBrightness(BlockState state, BlockGetter reader, BlockPos pos) {
        return 1F;
    }


    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos blockPos, BlockState blockState) {
        return new TileEntityTank(blockPos, blockState);
    }

}
