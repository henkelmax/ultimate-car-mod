package de.maxhenkel.car.blocks;

import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.Nullable;

public class BlockCarPressurePlate extends BasePressurePlateBlock implements IItemBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected BlockCarPressurePlate() {
        super(Block.Properties.of(Material.STONE).noCollission().strength(0.5F), BlockSetType.STONE);
        registerDefaultState(stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties());
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
