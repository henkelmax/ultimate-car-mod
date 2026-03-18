package de.maxhenkel.car.recipes;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class RecipeSerializerKey {

    private static final MapCodec<KeyRecipe> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(CraftingBookCategory.CODEC.fieldOf("category").orElse(CraftingBookCategory.MISC).forGetter(CraftingRecipe::category))
                    .apply(instance, KeyRecipe::new)
    );
    private static final StreamCodec<RegistryFriendlyByteBuf, KeyRecipe> STREAM_CODEC = StreamCodec.composite(CraftingBookCategory.STREAM_CODEC, CraftingRecipe::category, KeyRecipe::new);
    public static RecipeSerializer<KeyRecipe> RECIPE_SERIALIZER = new RecipeSerializer<>(CODEC, STREAM_CODEC);

}
