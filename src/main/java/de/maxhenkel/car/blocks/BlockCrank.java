package de.maxhenkel.car.blocks;

import de.maxhenkel.car.Main;
import de.maxhenkel.car.ModItemGroups;
import de.maxhenkel.car.blocks.tileentity.TileEntityDynamo;
import de.maxhenkel.corelib.block.IItemBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

public class BlockCrank extends BlockBase implements IItemBlock {

    public static final IntegerProperty CRANK_POS = IntegerProperty.create("rotation", 0, 7);

    public static final VoxelShape SHAPE = Block.box(3.2D, 0D, 3.2D, 12.8D, 9.6D, 12.8D);

    public BlockCrank() {
        super(Properties.of(Material.WOOD, MaterialColor.WOOD).strength(0.5F).sound(SoundType.WOOD));
        setRegistryName(new ResourceLocation(Main.MODID, "crank"));
        registerDefaultState(stateDefinition.any().setValue(CRANK_POS, 0));
    }

    @Override
    public Item toItem() {
        return new BlockItem(this, new Item.Properties().tab(ModItemGroups.TAB_CAR)).setRegistryName(getRegistryName());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        BlockEntity te = worldIn.getBlockEntity(pos.below());

        if (!(te instanceof TileEntityDynamo)) {
            return InteractionResult.FAIL;
        }

        TileEntityDynamo dyn = (TileEntityDynamo) te;

        dyn.addEnergy(dyn.generation);

        int i = state.getValue(CRANK_POS) + 1;
        if (i > 7) {
            i = 0;
        }

        worldIn.setBlock(pos, state.setValue(CRANK_POS, i), 3);

        return InteractionResult.SUCCESS;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CRANK_POS);
    }

}
