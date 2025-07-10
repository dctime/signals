package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.RegisterRecipeTypes;
import github.dctime.dctimesignals.lib.SignalOperationString;
import github.dctime.dctimesignals.lib.StringToSignalOperation;
import github.dctime.dctimesignals.recipe.SignalResearchRecipe;
import github.dctime.dctimesignals.recipe.SignalResearchRecipeInput;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SignalResearchItemChamberBlockEntity extends BlockEntity {

    private SignalResearchStationBlockEntity signalResearchStationBlockEntity;
    private BlockPos stationBlockPos;

    public void setSignalResearchStationBlockEntity(SignalResearchStationBlockEntity signalResearchStationBlockEntity) {
        this.signalResearchStationBlockEntity = signalResearchStationBlockEntity;
        stationBlockPos = signalResearchStationBlockEntity.getBlockPos();
    }

    @Nullable
    public SignalResearchStationBlockEntity getSignalResearchStationBlockEntity() {
        return this.signalResearchStationBlockEntity;
    }

    private ItemStackHandler items;
    private SimpleContainerData data;
    private ItemStackHandler researchingItem;

    public static final int ITEMS_SIZE = 4;
    public static final int DATA_SIZE = 1;
    public static final int RESEARCHING_ITEM_SIZE = 4;

    public static final int DATA_PROGRESS_INDEX = 0;
    public static final int ITEMS_INPUT_1_INDEX = 0;
    public static final int ITEMS_INPUT_2_INDEX = 1;
    public static final int ITEMS_INPUT_3_INDEX = 2;
    public static final int ITEMS_OUTPUT_INDEX = 3;
    public static final int RESEARCHING_ITEM_INDEX = 0;
    public static final int RESEARCHING_ITEM_INPUT_1_INDEX = 1;
    public static final int RESEARCHING_ITEM_INPUT_2_INDEX = 2;
    public static final int RESEARCHING_ITEM_INPUT_3_INDEX = 3;


    public SimpleContainerData getData() {
        return data;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public ItemStackHandler getResearchingItem() {
        return researchingItem;
    }

    public final int MAX_PROGRESS = 100;
    public void addProgress(int amount) {
        int currentProgress = data.get(DATA_PROGRESS_INDEX);
        int newProgress = Math.min(currentProgress + amount, MAX_PROGRESS);
        data.set(DATA_PROGRESS_INDEX, newProgress);
    }

    public void removeProgress(int amount) {
        int currentProgress = data.get(DATA_PROGRESS_INDEX);
        int newProgress = Math.max(currentProgress - amount, 1);
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
        researchingItem = new ItemStackHandler(RESEARCHING_ITEM_SIZE);
        data.set(DATA_PROGRESS_INDEX, 0);
    }

    // Read values from the passed CompoundTag here.
    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        (items).deserializeNBT(registries, tag.getCompound("items"));
        (researchingItem).deserializeNBT(registries, tag.getCompound("researchingItem"));
        data.set(DATA_PROGRESS_INDEX, tag.getInt("dataProgress"));
        signalRequired1 = tag.getString("signalRequired1");
        signalRequired2 = tag.getString("signalRequired2");
        signalRequired3 = tag.getString("signalRequired3");
        if (tag.contains("stationBlockPos"))
            stationBlockPos = BlockPos.CODEC.parse(NbtOps.INSTANCE, tag.get("stationBlockPos")).getOrThrow();
        // Will default to 0 if absent. See the NBT article for more information.
//        this.value = tag.getInt("value");
    }

    // Save values into the passed CompoundTag here.
    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.put("items", (items).serializeNBT(registries));
        tag.put("researchingItem", (researchingItem).serializeNBT(registries));
        tag.putInt("dataProgress", data.get(DATA_PROGRESS_INDEX));
        tag.putString("signalRequired1", this.signalRequired1);
        tag.putString("signalRequired2", this.signalRequired2);
        tag.putString("signalRequired3", this.signalRequired3);
        if (stationBlockPos != null)
            tag.put("stationBlockPos", BlockPos.CODEC.encodeStart(NbtOps.INSTANCE, stationBlockPos).getOrThrow());
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

    private void setResearchingItem(ItemStack itemStack) {
        if (inProgress()) return;
        this.researchingItem.setStackInSlot(RESEARCHING_ITEM_INDEX, itemStack);
    }

    private void setEatenResearchingInputItem(ItemStack input1, ItemStack input2, ItemStack input3) {
        if (inProgress()) return;
        this.researchingItem.setStackInSlot(RESEARCHING_ITEM_INPUT_1_INDEX, input1);
        this.researchingItem.setStackInSlot(RESEARCHING_ITEM_INPUT_2_INDEX, input2);
        this.researchingItem.setStackInSlot(RESEARCHING_ITEM_INPUT_3_INDEX, input3);
    }

    private String signalRequired1 = "";
    private String signalRequired2 = "";
    private String signalRequired3 = "";
    public String getSignalRequired1() {
        return signalRequired1;
    }
    public String getSignalRequired2() {
        return signalRequired2;
    }
    public String getSignalRequired3() {
        return signalRequired3;
    }
    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SignalResearchItemChamberBlockEntity signalResearchItemChamberBlockEntity) {
        if (level.isClientSide()) return;
        // if there is no signal research station block entity, do nothing
        if (signalResearchItemChamberBlockEntity.getSignalResearchStationBlockEntity() == null) {
            if (signalResearchItemChamberBlockEntity.stationBlockPos == null) return;
            if (level.getBlockEntity(signalResearchItemChamberBlockEntity.stationBlockPos) instanceof SignalResearchStationBlockEntity stationEntity) {
                signalResearchItemChamberBlockEntity.setSignalResearchStationBlockEntity(stationEntity);
            }
            return;
        }

        SignalResearchStationBlockEntity stationEntity = signalResearchItemChamberBlockEntity.getSignalResearchStationBlockEntity();

        // if in progress
        if (signalResearchItemChamberBlockEntity.inProgress()) {
            boolean allCorrect = true;

            for (int i = 0; i < 3; i++) {
                if (stationEntity.getRequiredInputSignalData().get(i) != stationEntity.getInputSignalData().get(i)) {
                    allCorrect = false;
                    break;
                }
            }

            if (!allCorrect || stationEntity.areOutputPortsInvalid()) {
                signalResearchItemChamberBlockEntity.removeProgress(1);
                return;
            }

            signalResearchItemChamberBlockEntity.addProgress(1);

            if (signalResearchItemChamberBlockEntity.checkProgressReady()) {
                // If progress is ready, output the result
                ItemStack output = signalResearchItemChamberBlockEntity.researchingItem.getStackInSlot(RESEARCHING_ITEM_INDEX);
                if (!output.isEmpty()) {
                    signalResearchItemChamberBlockEntity.items.insertItem(SignalResearchItemChamberBlockEntity.ITEMS_OUTPUT_INDEX, output, false);
                    signalResearchItemChamberBlockEntity.signalRequired1 = "";
                    signalResearchItemChamberBlockEntity.signalRequired2 = "";
                    signalResearchItemChamberBlockEntity.signalRequired3 = "";
                    signalResearchItemChamberBlockEntity.resetProgress();
                    signalResearchItemChamberBlockEntity.setResearchingItem(ItemStack.EMPTY);
                    signalResearchItemChamberBlockEntity.setEatenResearchingInputItem(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
                }
            }
            return;
        }


        SignalResearchRecipeInput inputRecipe = new SignalResearchRecipeInput(
                blockState,
                Ingredient.of(
                        signalResearchItemChamberBlockEntity.items.getStackInSlot(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_1_INDEX),
                        signalResearchItemChamberBlockEntity.items.getStackInSlot(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_2_INDEX),
                        signalResearchItemChamberBlockEntity.items.getStackInSlot(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_3_INDEX)
                )
        );

        Optional<RecipeHolder<SignalResearchRecipe>> recipe = level.getRecipeManager().getRecipeFor(
                RegisterRecipeTypes.SIGNAL_RESEARCH_RECIPE_TYPE.get(),
                inputRecipe,
                level
        );

        if (recipe.isEmpty()) return;

        ItemStack resultItem = recipe.get().value().assemble(inputRecipe, level.registryAccess());
        if (resultItem.isEmpty()) {
            signalResearchItemChamberBlockEntity.setResearchingItem(ItemStack.EMPTY);
            signalResearchItemChamberBlockEntity.setEatenResearchingInputItem(ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY);
            return;
        }

        // make sure if the slot can eat this item
        if (signalResearchItemChamberBlockEntity.items.insertItem(
                SignalResearchItemChamberBlockEntity.ITEMS_OUTPUT_INDEX,
                resultItem,
                true
        ) != ItemStack.EMPTY) return;

        // all checks passed, set the output buffer and clear the input
        for (int inputIngredientIndex = 0; inputIngredientIndex < recipe.get().value().getIngredients().getFirst().getItems().length; inputIngredientIndex++) {
            ItemStack inputIngredientStack = recipe.get().value().getIngredients().getFirst().getItems()[inputIngredientIndex];
            if (inputIngredientStack.is(Items.BARRIER)) continue;
            for (int inputSlotIndex = 0; inputSlotIndex < SignalResearchItemChamberBlockEntity.ITEMS_SIZE-1; inputSlotIndex++) {
                if (inputIngredientStack.is(signalResearchItemChamberBlockEntity.items.getStackInSlot(inputSlotIndex).getItem())) {
                    signalResearchItemChamberBlockEntity.items.extractItem(SignalResearchItemChamberBlockEntity.ITEMS_INPUT_1_INDEX+inputSlotIndex, inputIngredientStack.getCount(), false);
                    signalResearchItemChamberBlockEntity.researchingItem.setStackInSlot(SignalResearchItemChamberBlockEntity.RESEARCHING_ITEM_INPUT_1_INDEX+inputSlotIndex, inputIngredientStack);
                    break;
                }
            }
        }

        signalResearchItemChamberBlockEntity.setResearchingItem(resultItem);
        signalResearchItemChamberBlockEntity.signalRequired1 = recipe.get().value().getSignalRequired1();
        signalResearchItemChamberBlockEntity.signalRequired2 = recipe.get().value().getSignalRequired2();
        signalResearchItemChamberBlockEntity.signalRequired3 = recipe.get().value().getSignalRequired3();
        signalResearchItemChamberBlockEntity.addProgress(1);
    }
}
