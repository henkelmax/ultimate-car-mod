package de.maxhenkel.car.recipes;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;

import javax.annotation.Nullable;

public abstract class RecipeSerializerEnergyFluidProducer<T extends EnergyFluidProducerRecipe> implements RecipeSerializer<T> {

    private final RecipeSerializerEnergyFluidProducer.IFactory<T> factory;
    private Codec<T> codec;

    public RecipeSerializerEnergyFluidProducer(RecipeSerializerEnergyFluidProducer.IFactory<T> factory) {
        this.factory = factory;
        codec = RecordCodecBuilder.create((builder) -> builder
                .group(
                        Codec.STRING.optionalFieldOf("group", "")
                                .forGetter((recipe) -> recipe.group),
                        Ingredient.CODEC_NONEMPTY
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
    }

    @Override
    public Codec<T> codec() {
        return codec;
    }

    @Override
    public @Nullable T fromNetwork(FriendlyByteBuf buffer) {
        return factory.create(buffer.readUtf(32767), Ingredient.fromNetwork(buffer), buffer.readItem(), buffer.readVarInt(), buffer.readVarInt(), buffer.readVarInt());
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
        T create(String group, Ingredient input, ItemStack output, int fluidAmount, int energy, int duration);
    }
}
