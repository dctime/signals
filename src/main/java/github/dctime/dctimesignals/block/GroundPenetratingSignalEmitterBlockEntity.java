package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.menu.GroundPenetratingSignalEmitterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;

public class GroundPenetratingSignalEmitterBlockEntity extends BaseContainerBlockEntity {
    public GroundPenetratingSignalEmitterBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.GROUND_PENETRATING_SIGNAL_EMITTER_BLOCK_ENTITY.get(), pos, blockState);
    }

    public static final int ITEMS_SIZE = 3;
    public static final int ITEMS_PICKAXE_INPUT = 0;
    public static final int ITEMS_PICKAXE_OUTPUT = 1;
    public static final int ITEMS_FILTER = 2;

    public static final int DATA_SIZE = 1;

    private ItemStackHandler itemStackHandler = new ItemStackHandler(ITEMS_SIZE);
    private SimpleContainerData data = new SimpleContainerData(DATA_SIZE);

    @Override
    protected NonNullList<ItemStack> getItems() {
        NonNullList<ItemStack> items = NonNullList.withSize(ITEMS_SIZE, ItemStack.EMPTY);
        for (int i = 0; i < ITEMS_SIZE; i++) {
            items.set(i, itemStackHandler.getStackInSlot(i));
        }
        return items;
    }

    public ItemStackHandler getItemStackHandler() {
        return itemStackHandler;
    }

    public SimpleContainerData getData() {
        return data;
    }

    @Override
    protected void setItems(NonNullList<ItemStack> nonNullList) {
        if (nonNullList.size() != ITEMS_SIZE) {
            throw new IllegalArgumentException("Expected " + ITEMS_SIZE + " items, but got " + nonNullList.size());
        }

        for (int i = 0; i < ITEMS_SIZE; i++) {
            itemStackHandler.setStackInSlot(i, nonNullList.get(i));
        }
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
    }

    @Override
    protected Component getDefaultName() {
        return Component.translatable("container.ground_penetrating_signal_emitter");
    }



    @Override
    protected AbstractContainerMenu createMenu(int i, Inventory inventory) {
        return new GroundPenetratingSignalEmitterMenu(i, inventory);
    }

    // Create an update tag here. For block entities with only a few fields, this can just call #saveAdditional.
    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    // Return our packet here. This method returning a non-null result tells the game to use this packet for syncing.
    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        // The packet uses the CompoundTag returned by #getUpdateTag. An alternative overload of #create exists
        // that allows you to specify a custom update tag, including the ability to omit data the client might not need.
        return ClientboundBlockEntityDataPacket.create(this);
    }

    public static <T extends BlockEntity> void tick(Level level, BlockPos blockPos, BlockState blockState, GroundPenetratingSignalEmitterBlockEntity entity) {

    }

    @Override
    public int getContainerSize() {
        return 0;
    }
}
