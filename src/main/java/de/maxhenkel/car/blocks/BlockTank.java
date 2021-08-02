package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.car.blocks.tileentity.render.item.TankItemTileEntityRenderer;
import de.maxhenkel.corelib.block.IItemBlock;
import de.maxhenkel.corelib.blockentity.SimpleBlockEntityTicker;
import de.maxhenkel.corelib.client.CustomRendererBlockItem;
import de.maxhenkel.corelib.fluid.FluidUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
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

public class BlockTank extends BlockBase implements EntityBlock, IItemBlock {

    protected BlockTank() {
        super(Block.Properties.of(Material.GLASS).strength(0.5F).sound(SoundType.GLASS).noOcclusion());
        setRegistryName(new ResourceLocation(Main.MODID, "tank"));
    }

    @Override
    public Item toItem() {
        return new CustomRendererBlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR).stacksTo(1), TankItemTileEntityRenderer::new).setRegistryName(getRegistryName());
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return new SimpleBlockEntityTicker<>();
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        if (stack.hasTag() && stack.getTag().contains("fluid")) {
            CompoundTag fluidComp = stack.getTag().getCompound("fluid");
            FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidComp);

            if (fluidStack != null) {
                tooltip.add(new TranslatableComponent("tooltip.fluid", new TextComponent(fluidStack.getDisplayName().getString()).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
                tooltip.add(new TranslatableComponent("tooltip.amount", new TextComponent(String.valueOf(fluidStack.getAmount())).withStyle(ChatFormatting.DARK_GRAY)).withStyle(ChatFormatting.GRAY));
            }
        }
        super.appendHoverText(stack, worldIn, tooltip, flagIn);
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
        if (!stack.hasTag()) {
            tank.setFluid(FluidStack.EMPTY);
            return;
        }

        CompoundTag comp = stack.getTag();

        if (!comp.contains("fluid")) {
            return;
        }

        CompoundTag fluidTag = comp.getCompound("fluid");

        FluidStack fluidStack = FluidStack.loadFluidStackFromNBT(fluidTag);

        tank.setFluid(fluidStack);
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        return FluidUtils.tryFluidInteraction(player, handIn, worldIn, pos) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
    }

    public static boolean handleEmpty(ItemStack stack, Level worldIn, BlockPos pos, Player playerIn, InteractionHand hand) {
        BlockEntity te = worldIn.getBlockEntity(pos);

        if (!(te instanceof IFluidHandler)) {
            return false;
        }

        IFluidHandler handler = (IFluidHandler) te;

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

        if (!(te instanceof IFluidHandler)) {
            return false;
        }

        IFluidHandler blockHandler = (IFluidHandler) te;

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
    public boolean propagatesSkylightDown(BlockState state, BlockGetter world, BlockPos pos) {
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
