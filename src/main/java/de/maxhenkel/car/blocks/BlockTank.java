package de.maxhenkel.car.blocks;

import de.maxhenkel.car.CarMod;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
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
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.ResourceHandlerUtil;
import net.neoforged.neoforge.transfer.access.ItemAccess;
import net.neoforged.neoforge.transfer.fluid.FluidResource;

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
        SimpleFluidContent content = stack.get(CarMod.FLUID_STACK_DATA_COMPONENT);
        if (content == null) {
            tank.setFluid(FluidStack.EMPTY);
            return;
        }
        tank.setFluid(content.copy());
    }

    @Override
    protected InteractionResult useItemOn(ItemStack itemStack, BlockState blockState, Level level, BlockPos blockPos, Player player, InteractionHand interactionHand, BlockHitResult blockHitResult) {
        ResourceHandler<FluidResource> capability = level.getCapability(Capabilities.Fluid.BLOCK, blockPos, blockHitResult.getDirection());
        if (capability == null) {
            return InteractionResult.FAIL;
        }
        ItemAccess itemAccess = ItemAccess.forPlayerInteraction(player, interactionHand);
        ResourceHandler<FluidResource> itemCapability = itemAccess.getCapability(Capabilities.Fluid.ITEM);
        if (itemCapability == null) {
            return InteractionResult.FAIL;
        }
        int result = 0;
        //TODO Play empty and fill sound
        if (!ResourceHandlerUtil.isEmpty(itemCapability)) {
            result = ResourceHandlerUtil.move(itemCapability, capability, resource -> true, Integer.MAX_VALUE, null);
        } else if (!ResourceHandlerUtil.isEmpty(capability)) {
            result = ResourceHandlerUtil.move(capability, itemCapability, resource -> true, Integer.MAX_VALUE, null);
        }
        return result > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    protected boolean propagatesSkylightDown(BlockState state) {
        return true;
    }

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
