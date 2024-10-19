package de.maxhenkel.car.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;

public abstract class RecipeSerializerEnergyFluidProducer<T extends EnergyFluidProducerRecipe> implements RecipeSerializer<T> {

    private final RecipeSerializerEnergyFluidProducer.IFactory<T> factory;
    private MapCodec<T> codec;
    private StreamCodec<RegistryFriendlyByteBuf, T> streamCodec;

    public RecipeSerializerEnergyFluidProducer(RecipeSerializerEnergyFluidProducer.IFactory<T> factory) {
        this.factory = factory;
        codec = RecordCodecBuilder.mapCodec((builder) -> builder
                .group(
                        Codec.STRING.optionalFieldOf("group", "")
                                .forGetter((recipe) -> recipe.group),
                        Ingredient.CODEC
                                .fieldOf("ingredient")
                                .forGetter((recipe) -> recipe.ingredient),
                        BuiltInRegistries.ITEM.byNameCodec().xmap(ItemStack::new, ItemStack::getItem)
                                .fieldOf("result")
                                .forGetter((recipe) -> recipe.result),
                        Codec.INT.fieldOf("fluidamount")
                                .forGetter((recipe) -> recipe.fluidAmount),
                        Codec.INT.fieldOf("energy")
                                .forGetter((recipe) -> recipe.energy),
                        Codec.INT.fieldOf("duration")
                                .forGetter((recipe) -> recipe.duration)
                ).apply(builder, factory::create));
        streamCodec = StreamCodec.of(this::toNetwork, this::fromNetwork);
    }

    @Override
    public MapCodec<T> codec() {
        return codec;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, T> streamCodec() {
        return streamCodec;
    }

    public T fromNetwork(RegistryFriendlyByteBuf buffer) {
        return factory.create(buffer.readUtf(32767), Ingredient.CONTENTS_STREAM_CODEC.decode(buffer), ItemStack.STREAM_CODEC.decode(buffer), buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
    }

    public void toNetwork(RegistryFriendlyByteBuf buffer, T recipe) {
        buffer.writeUtf(recipe.group);
        Ingredient.CONTENTS_STREAM_CODEC.encode(buffer, recipe.ingredient);
        ItemStack.STREAM_CODEC.encode(buffer, recipe.result);
        buffer.writeVarInt(recipe.fluidAmount);
        buffer.writeVarInt(recipe.energy);
        buffer.writeVarInt(recipe.duration);
    }

    public interface IFactory<T extends EnergyFluidProducerRecipe> {
        T create(String group, Ingredient input, ItemStack output, int fluidAmount, int energy, int duration);
    }
}
