package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.RegisterRecipeTypes;
import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import github.dctime.dctimesignals.recipe.SignalResearchRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

import java.util.Optional;

public class SignalResearchItemChamberBlockEntity extends BlockEntity {

    private ItemStackHandler items;
    private SimpleContainerData data;

    public static final int ITEMS_SIZE = 2;
    public static final int DATA_SIZE = 1;

    public static final int DATA_PROGRESS_INDEX = 0;
    public static final int ITEMS_INPUT_INDEX = 0;
    public static final int ITEMS_OUTPUT_INDEX = 1;

    public SimpleContainerData getData() {
        return data;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public final int MAX_PROGRESS = 100;
    public void addProgress(int amount) {
        int currentProgress = data.get(DATA_PROGRESS_INDEX);
        int newProgress = Math.min(currentProgress + amount, MAX_PROGRESS);
        data.set(DATA_PROGRESS_INDEX, newProgress);
    }

    public void resetProgress() {
        data.set(DATA_PROGRESS_INDEX, 0);
    }

    public boolean checkProgressReady() {
        return data.get(DATA_PROGRESS_INDEX) >= MAX_PROGRESS;
    }

    public boolean inProgress() {
        return data.get(DATA_PROGRESS_INDEX) != 0;
    }

    public SignalResearchItemChamberBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.SIGNAL_RESEARCH_ITEM_CHAMBER_BLOCK_ENTITY.get(), pos, blockState);
        items = new ItemStackHandler(ITEMS_SIZE);
        data = new SimpleContainerData(DATA_SIZE);
        data.set(DATA_PROGRESS_INDEX, 0);
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        // Will default to 0 if absent. See the NBT article for more information.
//        this.value = tag.getInt("value");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
//        tag.putInt("value", this.value);
        setChanged();
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    private ItemStack recipeOutputBuffer;
    private void setRecipeOutputBuffer(ItemStack itemStack) {
        if (inProgress()) return;
        this.recipeOutputBuffer = itemStack;
    }

    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SignalResearchItemChamberBlockEntity signalResearchItemChamberBlockEntity) {
        if (level.isClientSide()) return;

        // if in progress
        if (signalResearchItemChamberBlockEntity.inProgress()) {
            signalResearchItemChamberBlockEntity.addProgress(1);
            if (signalResearchItemChamberBlockEntity.checkProgressReady()) {
                // If progress is ready, output the result
                ItemStack output = signalResearchItemChamberBlockEntity.recipeOutputBuffer;
                if (!output.isEmpty()) {
                    signalResearchItemChamberBlockEntity.items.insertItem(SignalResearchItemChamberBlockEntity.ITEMS_OUTPUT_INDEX, output, false);
                    signalResearchItemChamberBlockEntity.resetProgress();
                }
            }
            return;
        }


        SignalResearchRecipeInput inputRecipe = new SignalResearchRecipeInput(
                blockState,
                signalResearchItemChamberBlockEntity.items.getStackInSlot(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_INDEX)
        );

        Optional<RecipeHolder<SignalResearchRecipe>> recipe = level.getRecipeManager().getRecipeFor(
                RegisterRecipeTypes.SIGNAL_RESEARCH_RECIPE_TYPE.get(),
                inputRecipe,
                level
        );

        if (recipe.isEmpty()) return;

        ItemStack resultItem = recipe.get().value().assemble(inputRecipe, level.registryAccess());
        if (resultItem.isEmpty()) return;

        // make sure if the slot can eat this item
        if (signalResearchItemChamberBlockEntity.items.insertItem(
                SignalResearchItemChamberBlockEntity.ITEMS_OUTPUT_INDEX,
                resultItem,
                true
        ) != ItemStack.EMPTY) return;

        // all checks passed, set the output buffer and clear the input
        signalResearchItemChamberBlockEntity.items.extractItem(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_INDEX, 1, false);
        signalResearchItemChamberBlockEntity.setRecipeOutputBuffer(resultItem);
        signalResearchItemChamberBlockEntity.addProgress(1);
    }
}
