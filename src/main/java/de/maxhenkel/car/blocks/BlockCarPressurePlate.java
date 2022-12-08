package de.maxhenkel.car.blocks;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;

public class BlockCarPressurePlate extends BasePressurePlateBlock implements IItemBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected BlockCarPressurePlate() {
        super(Block.Properties.of(Material.STONE).noCollission().strength(0.5F));
        registerDefaultState(stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties());
    }

    @Override
    protected void playOnSound(LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(null, pos, SoundEvents.STONE_PRESSURE_PLATE_CLICK_ON, SoundSource.BLOCKS, 0.3F, 0.6F);
    }

    @Override
    protected void playOffSound(LevelAccessor worldIn, BlockPos pos) {
        worldIn.playSound(null, pos, SoundEvents.STONE_PRESSURE_PLATE_CLICK_OFF, SoundSource.BLOCKS, 0.3F, 0.5F);
    }

    @Override
    protected int getSignalStrength(Level worldIn, BlockPos pos) {
        net.minecraft.world.phys.AABB axisalignedbb = TOUCH_AABB.move(pos);
        return worldIn.getEntitiesOfClass(EntityGenericCar.class, axisalignedbb).size() > 0 ? 15 : 0;
    }

    @Override
    protected int getSignalForState(BlockState state) {
        return state.getValue(POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setSignalForState(BlockState state, int strength) {
        return state.setValue(POWERED, strength > 0);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(POWERED);
    }
}
