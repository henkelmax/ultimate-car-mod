package de.maxhenkel.car.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;

public abstract class RecipeSerializerEnergyFluidProducer<T extends EnergyFluidProducerRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<T> {

    private final RecipeSerializerEnergyFluidProducer.IFactory<T> factory;

    public RecipeSerializerEnergyFluidProducer(RecipeSerializerEnergyFluidProducer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T read(ResourceLocation recipeId, JsonObject json) {
        String group = JSONUtils.getString(json, "group", "");
        JsonElement jsonelement = JSONUtils.isJsonArray(json, "ingredient") ? JSONUtils.getJsonArray(json, "ingredient") : JSONUtils.getJsonObject(json, "ingredient");
        ItemStack output = ShapedRecipe.deserializeItem(JSONUtils.getJsonObject(json, "result"));
        return factory.create(recipeId, group, Ingredient.deserialize(jsonelement), output, JSONUtils.getInt(json, "fluidamount"), JSONUtils.getInt(json, "energy"), JSONUtils.getInt(json, "duration"));
    }

    @Nullable
    @Override
    public T read(ResourceLocation recipeId, PacketBuffer buffer) {
        return factory.create(recipeId, buffer.readString(32767), Ingredient.read(buffer), buffer.readItemStack(), buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
    }

    @Override
    public void write(PacketBuffer buffer, T recipe) {
        buffer.writeString(recipe.group);
        recipe.ingredient.write(buffer);
        buffer.writeItemStack(recipe.result);
        buffer.writeVarInt(recipe.fluidAmount);
        buffer.writeVarInt(recipe.energy);
        buffer.writeVarInt(recipe.duration);
    }

    public interface IFactory<T extends EnergyFluidProducerRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient input, ItemStack output, int fluidAmount, int energy, int duration);
    }
}
