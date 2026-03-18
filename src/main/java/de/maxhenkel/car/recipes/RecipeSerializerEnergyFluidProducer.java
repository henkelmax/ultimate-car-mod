package de.maxhenkel.car.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.crafting.*;

public class RecipeSerializerEnergyFluidProducer {

    public static final RecipeSerializer<BlastFurnaceRecipe> BLAST_FURNACE_RECIPE_SERIALIZER = new RecipeSerializer<>(RecipeSerializerEnergyFluidProducer.codec(BlastFurnaceRecipe::new), RecipeSerializerEnergyFluidProducer.streamCodec(BlastFurnaceRecipe::new));
    public static final RecipeSerializer<OilMillRecipe> OIL_MILL_RECIPE_SERIALIZER = new RecipeSerializer<>(RecipeSerializerEnergyFluidProducer.codec(OilMillRecipe::new), RecipeSerializerEnergyFluidProducer.streamCodec(OilMillRecipe::new));

    public static <T extends EnergyFluidProducerRecipe> MapCodec<T> codec(RecipeSerializerEnergyFluidProducer.IFactory<T> factory) {
        return RecordCodecBuilder.mapCodec((builder) -> builder
                .group(
                        Codec.STRING.optionalFieldOf("group", "")
                                .forGetter((recipe) -> recipe.group),
                        Ingredient.CODEC
                                .fieldOf("ingredient")
                                .forGetter((recipe) -> recipe.ingredient),
                        ItemStackTemplate.CODEC
                                .fieldOf("result")
                                .forGetter((recipe) -> recipe.result),
                        Codec.INT.fieldOf("fluidamount")
                                .forGetter((recipe) -> recipe.fluidAmount),
                        Codec.INT.fieldOf("energy")
                                .forGetter((recipe) -> recipe.energy),
                        Codec.INT.fieldOf("duration")
                                .forGetter((recipe) -> recipe.duration)
                ).apply(builder, factory::create));
    }

    public static <T extends EnergyFluidProducerRecipe> StreamCodec<RegistryFriendlyByteBuf, T> streamCodec(RecipeSerializerEnergyFluidProducer.IFactory<T> factory) {
        return StreamCodec.of(RecipeSerializerEnergyFluidProducer::toNetwork, (input) -> fromNetwork(factory, input));
    }

    private static <T extends EnergyFluidProducerRecipe> T fromNetwork(RecipeSerializerEnergyFluidProducer.IFactory<T> factory, RegistryFriendlyByteBuf buffer) {
        return factory.create(buffer.readUtf(32767), Ingredient.CONTENTS_STREAM_CODEC.decode(buffer), ItemStackTemplate.STREAM_CODEC.decode(buffer), buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
    }

    private static <T extends EnergyFluidProducerRecipe> void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.group);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
        ItemStackTemplate.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeVarInt(recipe.fluidAmount);
        buffer.writeVarInt(recipe.energy);
        buffer.writeVarInt(recipe.duration);
    }

    public interface IFactory<T extends EnergyFluidProducerRecipe> {
        T create(String group, Ingredient input, ItemStackTemplate output, int fluidAmount, int energy, int duration);
    }
}
