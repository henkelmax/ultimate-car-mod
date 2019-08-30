package de.maxhenkel.car.loottable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import de.maxhenkel.car.Main;
import de.maxhenkel.car.blocks.tileentity.TileEntityTank;
import de.maxhenkel.tools.FluidUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootFunction;
import net.minecraft.world.storage.loot.LootParameters;
import net.minecraft.world.storage.loot.conditions.ILootCondition;
import net.minecraftforge.fluids.FluidStack;

public class CopyFluid extends LootFunction {
    protected CopyFluid(ILootCondition[] conditions) {
        super(conditions);
    }

    @Override
    public ItemStack doApply(ItemStack stack, LootContext context) {
        TileEntity tileEntity = context.get(LootParameters.BLOCK_ENTITY);
        if (!(tileEntity instanceof TileEntityTank)) {
            return stack;
        }

        TileEntityTank tank = (TileEntityTank) tileEntity;
        FluidStack fluid = tank.getFluid();

        if (FluidUtils.isEmpty(fluid)) {
            return stack;
        }

        CompoundNBT fluidCompound = new CompoundNBT();
        fluid.writeToNBT(fluidCompound);

        stack.getOrCreateTag().put("fluid", fluidCompound);

        return stack;
    }

    public static class Serializer extends net.minecraft.world.storage.loot.LootFunction.Serializer<CopyFluid> {

        public Serializer() {
            super(new ResourceLocation(Main.MODID, "copy_fluid"), CopyFluid.class);
        }

        @Override
        public CopyFluid deserialize(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext, ILootCondition[] iLootConditions) {
            return new CopyFluid(iLootConditions);
        }
    }
}
