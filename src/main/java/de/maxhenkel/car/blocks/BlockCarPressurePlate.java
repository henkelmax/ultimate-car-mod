package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.block.AbstractPressurePlateBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;

public class BlockCarPressurePlate extends AbstractPressurePlateBlock implements IItemBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected BlockCarPressurePlate() {
        super(Block.Properties.create(Material.ROCK).doesNotBlockMovement().hardnessAndResistance(0.5F));
        setRegistryName(new ResourceLocation(Main.MODID, "car_pressure_plate"));
        setDefaultState(stateContainer.getBaseState().with(POWERED, false));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().group(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }

    @Override
    protected void playClickOnSound(IWorld worldIn, BlockPos pos) {
        worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_ON, SoundCategory.BLOCKS, 0.3F, 0.6F);
    }

    @Override
    protected void playClickOffSound(IWorld worldIn, BlockPos pos) {
        worldIn.playSound(null, pos, SoundEvents.BLOCK_STONE_PRESSURE_PLATE_CLICK_OFF, SoundCategory.BLOCKS, 0.3F, 0.5F);
    }

    @Override
    protected int computeRedstoneStrength(World worldIn, BlockPos pos) {
        AxisAlignedBB axisalignedbb = PRESSURE_AABB.offset(pos);
        return worldIn.getEntitiesWithinAABB(EntityGenericCar.class, axisalignedbb).size() > 0 ? 15 : 0;
    }

    @Override
    protected int getRedstoneStrength(BlockState state) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setRedstoneStrength(BlockState state, int strength) {
        return state.with(POWERED, strength > 0);
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}
