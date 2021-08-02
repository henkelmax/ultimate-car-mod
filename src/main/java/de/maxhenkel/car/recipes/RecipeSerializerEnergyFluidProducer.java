package de.maxhenkel.car.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

import javax.annotation.Nullable;

public abstract class RecipeSerializerEnergyFluidProducer<T extends EnergyFluidProducerRecipe> extends net.minecraftforge.registries.ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<T> {

    private final RecipeSerializerEnergyFluidProducer.IFactory<T> factory;

    public RecipeSerializerEnergyFluidProducer(RecipeSerializerEnergyFluidProducer.IFactory<T> factory) {
        this.factory = factory;
    }

    @Override
    public T fromJson(ResourceLocation recipeId, JsonObject json) {
        String group = GsonHelper.getAsString(json, "group", "");
        JsonElement jsonelement = GsonHelper.isArrayNode(json, "ingredient") ? GsonHelper.convertToJsonArray(json, "ingredient") : GsonHelper.getAsJsonObject(json, "ingredient");
        ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
        return factory.create(recipeId, group, Ingredient.fromJson(jsonelement), output, GsonHelper.getAsInt(json, "fluidamount"), GsonHelper.getAsInt(json, "energy"), GsonHelper.getAsInt(json, "duration"));
    }

    @Nullable
    @Override
    public T fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
        return factory.create(recipeId, buffer.readUtf(32767), Ingredient.fromNetwork(buffer), buffer.readItem(), buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
    }

    @Override
    public void toNetwork(FriendlyByteBuf buffer, T recipe) {
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
