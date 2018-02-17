package de.maxhenkel.car.blocks;

import java.util.List;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModCreativeTabs;
import de.maxhenkel.car.blocks.tileentity.TileEntityFuelStation;
import de.maxhenkel.car.gui.GuiHandler;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class BlockFuelStation extends BlockOrientableHorizontal {

    public BlockFuelStation() {
        super(Material.IRON, MapColor.IRON);
        setUnlocalizedName("fuelstation");
        setRegistryName("fuelstation");
        setHardness(4.0F);
        setResistance(50.0F);
        setSoundType(SoundType.METAL);
        setCreativeTab(ModCreativeTabs.TAB_CAR);
        useNeighborBrightness = true;

    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
                                    EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);

        if (te == null || !(te instanceof TileEntityFuelStation)) {
            return false;
        }

        TileEntityFuelStation station= (TileEntityFuelStation) te;

        ItemStack stack = playerIn.getHeldItem(hand);

        if(station.isOwner(playerIn)||!station.hasTrade()){
            if (stack != null) {
                FluidStack fluidStack = FluidUtil.getFluidContained(stack);

                if (fluidStack != null) {
                    boolean success = BlockTank.handleEmpty(stack, worldIn, pos, playerIn, hand);
                    if (success) {
                        return true;
                    }
                }
                IFluidHandler handler = FluidUtil.getFluidHandler(stack);

                if (handler != null) {
                    boolean success1 = BlockTank.handleFill(stack, worldIn, pos, playerIn, hand);
                    if (success1) {
                        return true;
                    }
                }
            }
        }

        if (!playerIn.isSneaking()) {
            playerIn.openGui(Main.instance(), GuiHandler.GUI_FUELSTATION, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        } else if(station.isOwner(playerIn)){
            playerIn.openGui(Main.instance(), GuiHandler.GUI_FUELSTATION_ADMIN, worldIn, pos.getX(), pos.getY(), pos.getZ());
            return true;
        }
        return false;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFuelStation();
    }

    @Override
    public boolean isBlockNormalCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
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
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean canPlaceTorchOnTop(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }

    public static final AxisAlignedBB AABB_NORTH_SOUTH = new AxisAlignedBB(0.125F, 0, 0.3125F, 1 - 0.125F, 2 - 0.0625F,
            1 - 0.3125F);
    public static final AxisAlignedBB AABB_EAST_WEST = new AxisAlignedBB(0.3125F, 0, 0.125F, 1 - 0.3125F, 2 - 0.0625F,
            1 - 0.125F);
    public static final AxisAlignedBB AABB_SLAB = new AxisAlignedBB(0, 0, 0, 1, 0.5F, 1);

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        EnumFacing facing = state.getValue(FACING);

        if (facing.equals(EnumFacing.NORTH) || facing.equals(EnumFacing.SOUTH)) {
            return AABB_NORTH_SOUTH;
        } else {
            return AABB_EAST_WEST;
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return new AxisAlignedBB(0, 0, 0, 1, 2, 1);
    }

    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox,
                                      List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        addCollisionBoxToList(pos, entityBox, collidingBoxes, getBoundingBox(state, worldIn, pos));
        addCollisionBoxToList(pos, entityBox, collidingBoxes, AABB_SLAB);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {

        if (!worldIn.isAirBlock(pos.up())) {
            return false;
        }

        return super.canPlaceBlockAt(worldIn, pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
                                ItemStack stack) {

        if (worldIn.isAirBlock(pos.up())) {
            worldIn.setBlockState(pos.up(), ModBlocks.FUEL_STATION_TOP.getDefaultState().withProperty(ModBlocks.FUEL_STATION_TOP.FACING, state.getValue(FACING)));
        }

        TileEntity te = worldIn.getTileEntity(pos);

        if (te != null && te instanceof TileEntityFuelStation && placer instanceof EntityPlayer) {
            TileEntityFuelStation station= (TileEntityFuelStation) te;
            station.setOwner((EntityPlayer) placer);
        }

        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        super.breakBlock(worldIn, pos, state);

        IBlockState stateUp = worldIn.getBlockState(pos.up());
        if (stateUp != null && stateUp.getBlock() != null && stateUp.getBlock().equals(ModBlocks.FUEL_STATION_TOP)) {
            worldIn.setBlockToAir(pos.up());
        }

        dropItems(worldIn, pos);
    }

    public static void dropItems(World worldIn, BlockPos pos){
        TileEntity te = worldIn.getTileEntity(pos);

        if (te != null && te instanceof TileEntityFuelStation) {
            TileEntityFuelStation station= (TileEntityFuelStation) te;
            InventoryHelper.dropInventoryItems(worldIn, pos, station.getInventory());
            InventoryHelper.dropInventoryItems(worldIn, pos, station.getTradingInventory());
        }
    }

}
