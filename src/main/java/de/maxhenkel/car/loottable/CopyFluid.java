package de.maxhenkel.car.loottable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
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

public class CopyFluid extends LootItemConditionalFunction {

    protected CopyFluid(LootItemCondition[] conditions) {
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

    public static class Serializer extends LootItemConditionalFunction.Serializer<CopyFluid> {
        @Override
        public CopyFluid deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, LootItemCondition[] iLootConditions) {
            return new CopyFluid(iLootConditions);
        }
    }

}
