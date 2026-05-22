package github.dctime.dctimesignals.block;

import github.dctime.dctimesignals.RegisterBlockEntities;
import github.dctime.dctimesignals.item.SignalDataItem;
import github.dctime.dctimesignals.menu.NetlistEditorMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;

public class NetlistEditorBlockEntity extends BlockEntity implements MenuProvider {

    public NetlistEditorBlockEntity(BlockPos pos, BlockState blockState) {
        super(RegisterBlockEntities.NETLIST_EDITOR_BLOCK_ENTITY.get(), pos, blockState);
    }

    private String netlist = "";

    public String getNetlist()                      { return netlist; }
    public void setNetlist(String netlist)          { this.netlist = netlist; setChanged(); }

    public ItemStack getStoredItem()                { return inventory.getStackInSlot(0); }
    public ItemStack insertItem(ItemStack stack)    { return inventory.insertItem(0, stack, false); }
    public ItemStack extractItem()                  { return inventory.extractItem(0, 1, false); }
    public boolean hasDisc() {
        return !getStoredItem().isEmpty();
    }

    /** 覆寫碟片上的波形數據 */
    public void writeToDisc(String label, double[] times, double[] values) {
        ItemStack stack = getStoredItem();
        if (!stack.isEmpty() && stack.getItem() instanceof SignalDataItem) {
            SignalDataItem.writeData(stack, label, times, values);
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }
    }

    private final ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            if (level != null && !level.isClientSide) {
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            }
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            return stack.getItem() instanceof SignalDataItem;
        }
    };

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.loadAdditional(tag, registries);
        netlist = tag.getString("netlist");
        if (tag.contains("inventory")) inventory.deserializeNBT(registries, tag.getCompound("inventory"));
    }

    @Override
    public void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        super.saveAdditional(tag, registries);
        tag.putString("netlist", netlist);
        tag.put("inventory", inventory.serializeNBT(registries));
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        CompoundTag tag = new CompoundTag();
        saveAdditional(tag, registries);
        return tag;
    }

    @Override
    public Packet<ClientGamePacketListener> getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public void onDataPacket(Connection connection, ClientboundBlockEntityDataPacket packet,
                             HolderLookup.Provider registries) {
        super.onDataPacket(connection, packet, registries);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Netlist Editor");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int i, Inventory inventory, Player player) {
        return new NetlistEditorMenu(i, inventory, this);
    }
}
