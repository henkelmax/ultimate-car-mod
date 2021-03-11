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
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        String group = JSONUtils.getAsString(json, "group", "");
        JsonElement jsonelement = JSONUtils.isArrayNode(json, "ingredient") ? JSONUtils.convertToJsonArray(json, "ingredient") : JSONUtils.getAsJsonObject(json, "ingredient");
        ItemStack output = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
        return factory.create(recipeId, group, Ingredient.fromJson(jsonelement), output, JSONUtils.getAsInt(json, "fluidamount"), JSONUtils.getAsInt(json, "energy"), JSONUtils.getAsInt(json, "duration"));
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, PacketBuffer buffer) {
        return factory.create(recipeId, buffer.readUtf(32767), Ingredient.fromNetwork(buffer), buffer.readItem(), buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
    }

    @Override
    public void toNetwork(PacketBuffer buffer, T recipe) {
        buffer.writeUtf(recipe.group);
        recipe.ingredient.toNetwork(buffer);
        buffer.writeItem(recipe.result);
        buffer.writeVarInt(recipe.fluidAmount);
        buffer.writeVarInt(recipe.energy);
        buffer.writeVarInt(recipe.duration);
    }

    public interface IFactory<T extends EnergyFluidProducerRecipe> {
        T create(ResourceLocation resourceLocation, String group, Ingredient input, ItemStack output, int fluidAmount, int energy, int duration);
    }
}
