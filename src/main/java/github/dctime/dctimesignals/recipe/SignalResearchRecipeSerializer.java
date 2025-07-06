package github.dctime.dctimesignals.recipe;

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

public class SignalResearchRecipeSerializer implements RecipeSerializer<SignalResearchRecipe> {

    public static final MapCodec<SignalResearchRecipe> CODEC = RecordCodecBuilder.mapCodec(inst -> inst.group(
            BlockState.CODEC.fieldOf("state").forGetter(SignalResearchRecipe::getInputState),
            ItemStack.CODEC.fieldOf("input1").forGetter(SignalResearchRecipe::getInput1ItemStack),
            ItemStack.CODEC.fieldOf("input2").forGetter(SignalResearchRecipe::getInput2ItemStack),
            ItemStack.CODEC.fieldOf("input3").forGetter(SignalResearchRecipe::getInput3ItemStack),
            ItemStack.CODEC.fieldOf("result").forGetter(SignalResearchRecipe::getResult)
    ).apply(inst, SignalResearchRecipe::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, SignalResearchRecipe> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.idMapper(Block.BLOCK_STATE_REGISTRY), SignalResearchRecipe::getInputState,
                    ItemStack.STREAM_CODEC, SignalResearchRecipe::getInput1ItemStack,
                    ItemStack.STREAM_CODEC, SignalResearchRecipe::getInput2ItemStack,
                    ItemStack.STREAM_CODEC, SignalResearchRecipe::getInput3ItemStack,
                    ItemStack.STREAM_CODEC, SignalResearchRecipe::getResult,
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
