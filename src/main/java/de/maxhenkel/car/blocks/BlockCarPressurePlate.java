package de.maxhenkel.car.blocks;

import com.mojang.serialization.MapCodec;
import de.maxhenkel.car.entity.car.base.EntityGenericCar;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BasePressurePlateBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.material.MapColor;

public class BlockCarPressurePlate extends BasePressurePlateBlock {

    public static final BooleanProperty POWERED = BlockStateProperties.POWERED;

    protected BlockCarPressurePlate(Properties properties) {
        super(properties.mapColor(MapColor.COLOR_BLACK).noCollision().strength(0.5F), BlockSetType.STONE);
        registerDefaultState(stateDefinition.any().setValue(POWERED, false));
    }

    @Override
    protected MapCodec<? extends BasePressurePlateBlock> codec() {
        return null; //TODO Add
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
