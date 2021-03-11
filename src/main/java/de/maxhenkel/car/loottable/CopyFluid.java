package de.maxhenkel.car.loottable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.LootFunction;
import net.minecraft.loot.LootFunctionType;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fluids.FluidStack;

public class CopyFluid extends LootFunction {

    protected CopyFluid(ILootCondition[] conditions) {
        super(conditions);
    }

    @Override
    public ItemStack run(ItemStack stack, LootContext context) {
        TileEntity tileEntity = context.getParamOrNull(LootParameters.BLOCK_ENTITY);
        if (!(tileEntity instanceof TileEntityTank)) {
            return stack;
        }

        TileEntityTank tank = (TileEntityTank) tileEntity;
        FluidStack fluid = tank.getFluid();

        if (fluid.isEmpty()) {
            return stack;
        }

        CompoundNBT fluidCompound = new CompoundNBT();
        fluid.writeToNBT(fluidCompound);

        stack.getOrCreateTag().put("fluid", fluidCompound);

        return stack;
    }

    @Override
    public LootFunctionType getType() {
        return Main.COPY_FLUID;
    }

    public static class Serializer extends LootFunction.Serializer<CopyFluid> {
        @Override
        public CopyFluid deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditions) {
            return new CopyFluid(iLootConditions);
        }
    }

}
