package github.dctime.dctimesignals.recipe;

import com.mojang.datafixers.util.Function6;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.function.Function;

public class SignalResearchRecipeSerializer implements RecipeSerializer<SignalResearchRecipe> {

    public static final MapCodec<SignalResearchRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(SignalResearchRecipe::getInputState),
            ItemStack.CODEC.fieldOf("input1").forGetter(SignalResearchRecipe::getInput1ItemStack),
            ItemStack.CODEC.fieldOf("input2").forGetter(SignalResearchRecipe::getInput2ItemStack),
            ItemStack.CODEC.fieldOf("input3").forGetter(SignalResearchRecipe::getInput3ItemStack),
            ItemStack.CODEC.fieldOf("result").forGetter(SignalResearchRecipe::getResult),
            Codec.STRING.fieldOf("signal_required1").forGetter(SignalResearchRecipe::getSignalRequired1),
            Codec.STRING.fieldOf("signal_required2").forGetter(SignalResearchRecipe::getSignalRequired2),
            Codec.STRING.fieldOf("signal_required3").forGetter(SignalResearchRecipe::getSignalRequired3)
    ).apply(inst, ((blockState, itemStack, itemStack2, itemStack3, itemStack4, s, s2, s3) -> {
        return new SignalResearchRecipe(blockState, List.of(itemStack, itemStack2, itemStack3), itemStack4, s, s2, s3);
    })));

    public static final StreamCodec<RegistryFriendlyByteBuf, SignalResearchRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), SignalResearchRecipe::getInputState,
                    ItemStack.LIST_STREAM_CODEC, SignalResearchRecipe::getInputItemStacks,
                    ItemStack.STREAM_CODEC, SignalResearchRecipe::getResult,
                    ByteBufCodecs.STRING_UTF8, SignalResearchRecipe::getSignalRequired1,
                    ByteBufCodecs.STRING_UTF8, SignalResearchRecipe::getSignalRequired2,
                    ByteBufCodecs.STRING_UTF8, SignalResearchRecipe::getSignalRequired3,
                    SignalResearchRecipe::new
            );

    @Override
    public MapCodec<SignalResearchRecipe> codec() {
        return CODEC;
    }

    @Override
    public StreamCodec<RegistryFriendlyByteBuf, SignalResearchRecipe> streamCodec() {
        return STREAM_CODEC;
    }
}
