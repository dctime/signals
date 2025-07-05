package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class SignalResearchItemChamberBlockEntity extends BlockEntity {

    private ItemStackHandler items;
    private SimpleContainerData data;

    public static final int ITEMS_SIZE = 2;
    public static final int DATA_SIZE = 1;

    public static final int DATA_PROGRESS_INDEX = 0;

    public SimpleContainerData getData() {
        return data;
    }

    public ItemStackHandler getItems() {
        return items;
    }

    public void addProgress(int amount) {
        int currentProgress = data.get(DATA_PROGRESS_INDEX);
        int newProgress = Math.min(currentProgress + amount, 100);
        data.set(DATA_PROGRESS_INDEX, newProgress);
    }

    public void resetProgress() {
        data.set(DATA_PROGRESS_INDEX, 0);
    }

    public boolean checkProgressReady() {
        return data.get(DATA_PROGRESS_INDEX) >= 100;
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


    public static void tick(Level level, BlockPos blockPos, BlockState blockState, SignalResearchItemChamberBlockEntity signalResearchItemChamberBlockEntity) {
        signalResearchItemChamberBlockEntity.addProgress(1);
        if (signalResearchItemChamberBlockEntity.checkProgressReady()) {
            signalResearchItemChamberBlockEntity.resetProgress();
        }
    }
}
