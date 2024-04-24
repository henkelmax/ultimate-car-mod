package de.maxhenkel.car.loottable;

import com.mojang.serialization.*;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.fluids.FluidStack;
import net.neoforged.neoforge.fluids.SimpleFluidContent;

import java.util.List;

public class CopyFluid extends LootItemConditionalFunction {

    public static final MapCodec<CopyFluid> CODEC = RecordCodecBuilder.mapCodec(instance -> commonFields(instance).apply(instance, CopyFluid::new));

    protected CopyFluid(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        BlockEntity tileEntity = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (!(tileEntity instanceof TileEntityTank tank)) {
            return stack;
        }
        FluidStack fluid = tank.getFluid();
        if (fluid.isEmpty()) {
            return stack;
        }
        stack.set(Main.FLUID_STACK_DATA_COMPONENT, SimpleFluidContent.copyOf(fluid));
        return stack;
    }

    @Override
    public LootItemFunctionType getType() {
        return Main.COPY_FLUID.get();
    }

}
