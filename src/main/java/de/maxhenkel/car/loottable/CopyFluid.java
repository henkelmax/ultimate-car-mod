package de.maxhenkel.car.loottable;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class CopyFluid extends LootItemConditionalFunction {

    public static final Codec<CopyFluid> CODEC = RecordCodecBuilder.create(instance -> commonFields(instance).apply(instance, CopyFluid::new));

    protected CopyFluid(List<LootItemCondition> conditions) {
        super(conditions);
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        BlockEntity tileEntity = context.getParamOrNull(LootContextParams.BLOCK_ENTITY);
        if (!(tileEntity instanceof TileEntityTank)) {
            return stack;
        }

        TileEntityTank tank = (TileEntityTank) tileEntity;
        FluidStack fluid = tank.getFluid();

        if (fluid.isEmpty()) {
            return stack;
        }

        CompoundTag fluidCompound = new CompoundTag();
        fluid.writeToNBT(fluidCompound);

        stack.getOrCreateTag().put("fluid", fluidCompound);

        return stack;
    }

    @Override
    public LootItemFunctionType getType() {
        return Main.COPY_FLUID;
    }

}
